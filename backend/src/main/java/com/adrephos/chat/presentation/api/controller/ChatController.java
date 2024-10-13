package com.adrephos.chat.presentation.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrephos.chat.application.CreateChat;
import com.adrephos.chat.application.GetChatMessages;
import com.adrephos.chat.application.GetUserChats;
import com.adrephos.chat.application.dto.CreateChatDTO;
import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.domain.Message;

@RestController
@RequestMapping("/chats")
@CrossOrigin(origins = "*")
public class ChatController {
  @Autowired
  private CreateChat createChat;

  @Autowired
  private GetUserChats getUserChats;

  @Autowired
  private GetChatMessages getChatMessages;

  @PostMapping("")
  public ResponseEntity<Chat> create(@RequestBody CreateChatDTO dto) {
    try {
      return new ResponseEntity<>(createChat.run(dto), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/{username}")
  public ResponseEntity<List<Chat>> getUserChats(@PathVariable String username) {
    return getUserChats.run(username)
        .map(chats -> new ResponseEntity<>(chats, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/messages/{id}")
  public ResponseEntity<List<Message>> getChatMessages(@PathVariable UUID id) {
    return getChatMessages.run(id)
        .map(messages -> new ResponseEntity<>(messages, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
