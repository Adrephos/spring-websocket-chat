package com.adrephos.chat.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrephos.chat.domain.Message;
import com.adrephos.chat.domain.repository.MessageRepository;
import com.adrephos.chat.infrastructure.repository.crud.MessageCrudRepository;
import com.adrephos.chat.infrastructure.repository.entity.MessageEntity;
import com.adrephos.chat.infrastructure.repository.mapper.MessageMapper;

@Repository
public class PostgresMessageRepository implements MessageRepository {
  @Autowired
  private MessageCrudRepository messageCrudRepository;

  @Autowired
  private MessageMapper mapper;

  @Override
  public Message create(Message message) {
    MessageEntity messageEntity = mapper.toEntity(message);
    return mapper.toDomain(messageCrudRepository.save(messageEntity));
  }

  @Override
  public Optional<Message> find(UUID id) {
    return messageCrudRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<List<Message>> findByChatId(UUID chatId) {
    return messageCrudRepository.findByChatIdOrderBySentAtAsc(chatId).map(mapper::toDomain);
  }

  @Override
  public Message update(Message message) {
    return create(message);
  }

  @Override
  public void delete(UUID id) {
    messageCrudRepository.deleteById(id);
  }
}
