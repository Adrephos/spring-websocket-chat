package com.adrephos.chat.infrastructure.repository.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.infrastructure.repository.entity.ChatEntity;

@Mapper(componentModel = "spring")
public interface ChatMapper {
  ChatEntity toEntity(Chat chat);
  List<ChatEntity> toEntity(List<Chat> chats);

  Chat toDomain(ChatEntity chatEntity);
  List<Chat> toDomain(List<ChatEntity> chatEntities);
}
