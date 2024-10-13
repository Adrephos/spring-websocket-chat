package com.adrephos.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.adrephos.chat.domain.Chat;

public interface ChatRepository {
  Chat create(Chat chat);

  Optional<Chat> findById(UUID id);

  Optional<Chat> findByUsernames(String firstUsername, String secondUsername);

  Optional<List<Chat>> findByUsername(String username);

  Chat update(Chat chat);

  void delete(UUID id);
}
