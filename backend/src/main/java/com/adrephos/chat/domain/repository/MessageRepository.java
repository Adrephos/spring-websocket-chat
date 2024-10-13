package com.adrephos.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.adrephos.chat.domain.Message;

public interface MessageRepository {
  Message create(Message message);

  Optional<Message> find(UUID id);

  Optional<List<Message>> findByChatId(UUID chatId);

  Message update(Message message);

  void delete(UUID id);
}
