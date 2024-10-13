package com.adrephos.chat.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.domain.repository.ChatRepository;
import com.adrephos.chat.infrastructure.repository.crud.ChatCrudRepository;
import com.adrephos.chat.infrastructure.repository.entity.ChatEntity;
import com.adrephos.chat.infrastructure.repository.mapper.ChatMapper;

@Repository
public class PostgresChatRepository implements ChatRepository {
  @Autowired
  private ChatCrudRepository chatCrudRepository;

  @Autowired
  private ChatMapper mapper;

  @Override
  public Chat create(Chat chat) {
    ChatEntity chatEntity = mapper.toEntity(chat);
    return mapper.toDomain(chatCrudRepository.save(chatEntity));
  }

  @Override
  public Optional<Chat> findById(UUID id) {
    return chatCrudRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public Optional<Chat> findByUsernames(String firstUsername, String secondUsername) {
    return chatCrudRepository.findByUsernames(firstUsername, secondUsername).map(mapper::toDomain);
  }

  @Override
  public Optional<List<Chat>> findByUsername(String username) {
    return chatCrudRepository.findByUsername(username).map(mapper::toDomain);
  }

  @Override
  public Chat update(Chat chat) {
    return create(chat);
  }

  @Override
  public void delete(UUID id) {
    chatCrudRepository.deleteById(id);
  }
}
