package com.adrephos.chat.infrastructure.repository.mapper;

import com.adrephos.chat.domain.Message;
import com.adrephos.chat.infrastructure.repository.entity.MessageEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-13T10:03:56-0500",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.39.0.v20240725-1906, environment: Java 21.0.3 (N/A)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageEntity toEntity(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setChatId( message.getChatId() );
        messageEntity.setContent( message.getContent() );
        messageEntity.setEdited( message.getEdited() );
        messageEntity.setEditedAt( message.getEditedAt() );
        messageEntity.setId( message.getId() );
        messageEntity.setReceiverUsername( message.getReceiverUsername() );
        messageEntity.setSenderUsername( message.getSenderUsername() );
        messageEntity.setSentAt( message.getSentAt() );

        return messageEntity;
    }

    @Override
    public List<MessageEntity> toEntity(List<Message> messages) {
        if ( messages == null ) {
            return null;
        }

        List<MessageEntity> list = new ArrayList<MessageEntity>( messages.size() );
        for ( Message message : messages ) {
            list.add( toEntity( message ) );
        }

        return list;
    }

    @Override
    public Message toDomain(MessageEntity messageEntity) {
        if ( messageEntity == null ) {
            return null;
        }

        UUID chatId = null;
        String content = null;
        Boolean edited = null;
        LocalDateTime editedAt = null;
        UUID id = null;
        String receiverUsername = null;
        String senderUsername = null;
        LocalDateTime sentAt = null;

        chatId = messageEntity.getChatId();
        content = messageEntity.getContent();
        edited = messageEntity.getEdited();
        editedAt = messageEntity.getEditedAt();
        id = messageEntity.getId();
        receiverUsername = messageEntity.getReceiverUsername();
        senderUsername = messageEntity.getSenderUsername();
        sentAt = messageEntity.getSentAt();

        Message message = new Message( id, content, sentAt, edited, editedAt, chatId, senderUsername, receiverUsername );

        return message;
    }

    @Override
    public List<Message> toDomain(List<MessageEntity> messageEntities) {
        if ( messageEntities == null ) {
            return null;
        }

        List<Message> list = new ArrayList<Message>( messageEntities.size() );
        for ( MessageEntity messageEntity : messageEntities ) {
            list.add( toDomain( messageEntity ) );
        }

        return list;
    }
}
