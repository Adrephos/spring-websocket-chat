package com.adrephos.chat.infrastructure.repository.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.adrephos.chat.domain.Message;
import com.adrephos.chat.infrastructure.repository.entity.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {
  MessageEntity toEntity(Message message);
  List<MessageEntity> toEntity(List<Message> messages);

  Message toDomain(MessageEntity messageEntity);
  List<Message> toDomain(List<MessageEntity> messageEntities);
}
