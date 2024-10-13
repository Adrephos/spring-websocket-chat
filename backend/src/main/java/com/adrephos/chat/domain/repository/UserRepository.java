package com.adrephos.chat.domain.repository;

import java.util.Optional;

import com.adrephos.chat.domain.User;

public interface UserRepository {
  User create(User user);

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  void delete(String username);
}
