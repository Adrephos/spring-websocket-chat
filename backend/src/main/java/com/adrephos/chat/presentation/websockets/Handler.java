package com.adrephos.chat.presentation.websockets;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.adrephos.chat.application.CreateMessage;
import com.adrephos.chat.application.DeleteMessage;
import com.adrephos.chat.application.UpdateMessage;
import com.adrephos.chat.application.dto.CreateMessageDTO;
import com.adrephos.chat.application.dto.UpdateMessageDTO;
import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.domain.Message;
import com.adrephos.chat.domain.repository.ChatRepository;
import com.adrephos.chat.domain.repository.MessageRepository;
import com.adrephos.chat.presentation.config.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Handler extends TextWebSocketHandler {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  CreateMessage createMessage;

  @Autowired
  UpdateMessage updateMessage;

  @Autowired
  DeleteMessage deleteMessage;

  @Autowired
  MessageRepository messageRepository;

  @Autowired
  ChatRepository chatRepository;

  private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

  private enum MessageType {
    CONNECT, SEND_MESSAGE, RECEIVE_MESSAGE, DELETE_MESSAGE, UPDATE_MESSAGE
  }

  private void sendToSession(WebSocketSession session, MessageType type, String content) {
    try {
      session.sendMessage(new TextMessage(type + ":" + content));
    } catch (Exception e) {
      throw new RuntimeException("Failed to send message", e);
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    String token = session.getAttributes().get("token").toString();
    String username = jwtUtil.getUsername(token);

    if (username != null) {
      sessions.remove(username);
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String token = session.getAttributes().get("token").toString();
    String username = jwtUtil.getUsername(token);

    if (username != null) {
      sessions.put(username, session);
      sendToSession(session, MessageType.CONNECT, "Success");
    } else {
      session.close(CloseStatus.NOT_ACCEPTABLE);
    }
  }

  private void sendToUsers(String firstUsername, String secondUsername,
      MessageType type, String content) {
    WebSocketSession firstSession = sessions.get(firstUsername);
    WebSocketSession secondSession = sessions.get(secondUsername);

    if (firstSession != null) {
      sendToSession(firstSession, MessageType.DELETE_MESSAGE, content);
    }
    if (secondSession != null) {
      sendToSession(secondSession, MessageType.DELETE_MESSAGE, content);
    }
  }

  private void sendMessage(WebSocketSession session, String content) {
    try {
      CreateMessageDTO dto = objectMapper.readValue(content, CreateMessageDTO.class);
      sendToUsers(dto.getSenderUsername(), dto.getReceiverUsername(),
          MessageType.RECEIVE_MESSAGE, content);

      createMessage.run(dto);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message", e);
    }
  }

  private void deleteMessage(WebSocketSession session, String content) {
    try {
      UUID id = UUID.fromString(content);
      Message message = messageRepository.find(id)
          .orElseThrow(() -> new RuntimeException("Message not found"));
      deleteMessage.run(id);

      Chat chat = chatRepository.findById(message.getChatId())
          .orElseThrow(() -> new RuntimeException("Chat not found"));

      sendToUsers(chat.getFirstUsername(), chat.getSecondUsername(),
          MessageType.DELETE_MESSAGE, content);

    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message", e);
    }
  }

  private void updateMessage(WebSocketSession session, String content) {
    try {
      UpdateMessageDTO dto = objectMapper.readValue(content, UpdateMessageDTO.class);
      Message message = updateMessage.run(dto);

      Chat chat = chatRepository.findById(message.getChatId())
          .orElseThrow(() -> new RuntimeException("Chat not found"));

      String editedMessage = objectMapper.writeValueAsString(message);

      sendToUsers(chat.getFirstUsername(), chat.getSecondUsername(),
          MessageType.UPDATE_MESSAGE, editedMessage);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse message", e);
    }
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    String payload = message.getPayload();
    MessageType type = MessageType.valueOf(payload.substring(0, payload.indexOf(":")));
    String content = payload.substring(payload.indexOf(":") + 1);

    switch (type) {
      case SEND_MESSAGE:
        sendMessage(session, content);
        break;
      case DELETE_MESSAGE:
        deleteMessage(session, content);
        break;
      case UPDATE_MESSAGE:
        updateMessage(session, content);
        break;
      default:
        throw new RuntimeException("Invalid message type");
    }
  }
}
