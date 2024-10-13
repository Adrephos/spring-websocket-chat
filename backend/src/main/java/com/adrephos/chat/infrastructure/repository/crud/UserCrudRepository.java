package com.adrephos.chat.infrastructure.repository.crud;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.adrephos.chat.infrastructure.repository.entity.UserEntity;

public interface UserCrudRepository extends CrudRepository<UserEntity, String> {
  Optional<UserEntity> findByEmail(String email);
}
