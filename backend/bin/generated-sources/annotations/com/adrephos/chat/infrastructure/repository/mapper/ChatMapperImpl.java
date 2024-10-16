package com.adrephos.chat.infrastructure.repository.mapper;

import com.adrephos.chat.domain.Chat;
import com.adrephos.chat.infrastructure.repository.entity.ChatEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-15T11:05:37-0500",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.39.0.v20240725-1906, environment: Java 21.0.3 (N/A)"
)
@Component
public class ChatMapperImpl implements ChatMapper {

    @Override
    public ChatEntity toEntity(Chat chat) {
        if ( chat == null ) {
            return null;
        }

        ChatEntity chatEntity = new ChatEntity();

        chatEntity.setFirstUsername( chat.getFirstUsername() );
        chatEntity.setId( chat.getId() );
        chatEntity.setSecondUsername( chat.getSecondUsername() );

        return chatEntity;
    }

    @Override
    public List<ChatEntity> toEntity(List<Chat> chats) {
        if ( chats == null ) {
            return null;
        }

        List<ChatEntity> list = new ArrayList<ChatEntity>( chats.size() );
        for ( Chat chat : chats ) {
            list.add( toEntity( chat ) );
        }

        return list;
    }

    @Override
    public Chat toDomain(ChatEntity chatEntity) {
        if ( chatEntity == null ) {
            return null;
        }

        String firstUsername = null;
        UUID id = null;
        String secondUsername = null;

        firstUsername = chatEntity.getFirstUsername();
        id = chatEntity.getId();
        secondUsername = chatEntity.getSecondUsername();

        Chat chat = new Chat( id, firstUsername, secondUsername );

        return chat;
    }

    @Override
    public List<Chat> toDomain(List<ChatEntity> chatEntities) {
        if ( chatEntities == null ) {
            return null;
        }

        List<Chat> list = new ArrayList<Chat>( chatEntities.size() );
        for ( ChatEntity chatEntity : chatEntities ) {
            list.add( toDomain( chatEntity ) );
        }

        return list;
    }
}
