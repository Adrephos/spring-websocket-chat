package com.adrephos.chat.infrastructure.repository.mapper;

import com.adrephos.chat.application.dto.RegisterDTO;
import com.adrephos.chat.domain.User;
import com.adrephos.chat.infrastructure.repository.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-15T11:05:37-0500",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.39.0.v20240725-1906, environment: Java 21.0.3 (N/A)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setUsername( user.getUsername() );

        return userEntity;
    }

    @Override
    public UserEntity toEntity(RegisterDTO user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setUsername( user.getUsername() );

        return userEntity;
    }

    @Override
    public User toDomain(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        String email = null;
        String password = null;
        String username = null;

        email = userEntity.getEmail();
        password = userEntity.getPassword();
        username = userEntity.getUsername();

        User user = new User( username, password, email );

        return user;
    }
}
