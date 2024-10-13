package com.adrephos.chat.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.domain.repository.ChatRepository;

@Service
public class GetUserChats {
  @Autowired
  ChatRepository chatRepository;

  public Optional<List<Chat>> run(String username) {
    Optional<List<Chat>> chatList = chatRepository.findByUsername(username);
    if (chatList.get().size() == 0) {
      return Optional.empty();
    }
    return chatList;
  }
}
