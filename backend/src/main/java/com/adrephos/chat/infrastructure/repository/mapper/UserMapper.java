package com.adrephos.chat.infrastructure.repository.mapper;

import org.mapstruct.Mapper;

import com.adrephos.chat.application.dto.RegisterDTO;
import com.adrephos.chat.domain.User;
import com.adrephos.chat.infrastructure.repository.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserEntity toEntity(User user);
  UserEntity toEntity(RegisterDTO user);
  User toDomain(UserEntity userEntity);
}
