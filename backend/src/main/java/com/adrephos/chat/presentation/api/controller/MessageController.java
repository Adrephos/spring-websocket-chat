package com.adrephos.chat.presentation.api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrephos.chat.application.CreateMessage;
import com.adrephos.chat.application.DeleteMessage;
import com.adrephos.chat.application.UpdateMessage;
import com.adrephos.chat.application.dto.CreateMessageDTO;
import com.adrephos.chat.application.dto.UpdateMessageDTO;
import com.adrephos.chat.domain.Message;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessageController {
  @Autowired
  private CreateMessage createMessage;

  @Autowired
  private UpdateMessage updateMessage;

  @Autowired
  private DeleteMessage deleteMessage;

  // TODO: Expose this endpoint via WebSocket
  @PostMapping("/send")
  public Message create(@RequestBody CreateMessageDTO dto) {
    return createMessage.run(dto);
  }

  // TODO: Expose this endpoint via WebSocket
  @PostMapping("/edit")
  public Message update(@RequestBody UpdateMessageDTO message) {
    return updateMessage.run(message);
  }

  // TODO: Expose this endpoint via WebSocket
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") UUID id) {
    deleteMessage.run(id);
  }
}
