package com.adrephos.chat.infrastructure.repository.crud;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.adrephos.chat.infrastructure.repository.entity.MessageEntity;

public interface MessageCrudRepository extends CrudRepository<MessageEntity, UUID> {
  Optional<List<MessageEntity>> findByChatIdOrderBySentAtAsc(UUID chatId);
}
