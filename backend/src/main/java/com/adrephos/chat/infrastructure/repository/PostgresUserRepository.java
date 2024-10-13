package com.adrephos.chat.infrastructure.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.adrephos.chat.domain.User;
import com.adrephos.chat.domain.repository.UserRepository;
import com.adrephos.chat.infrastructure.repository.crud.UserCrudRepository;
import com.adrephos.chat.infrastructure.repository.entity.UserEntity;
import com.adrephos.chat.infrastructure.repository.mapper.UserMapper;

@Repository
public class PostgresUserRepository implements UserRepository {
  @Autowired
  private UserCrudRepository userCrudRepository;

  @Autowired
  private UserMapper mapper;

  @Override
  public User create(User user) {
    UserEntity userEntity = userCrudRepository.save(mapper.toEntity(user));
    return mapper.toDomain(userEntity);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userCrudRepository.findById(username).map(mapper::toDomain);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userCrudRepository.findByEmail(email).map(mapper::toDomain);
  }

  @Override
  public void delete(String username) {
    userCrudRepository.deleteById(username);
  }
}
