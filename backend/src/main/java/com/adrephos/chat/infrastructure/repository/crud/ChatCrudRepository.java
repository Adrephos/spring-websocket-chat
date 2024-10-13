package com.adrephos.chat.infrastructure.repository.crud;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.adrephos.chat.infrastructure.repository.entity.ChatEntity;

public interface ChatCrudRepository extends CrudRepository<ChatEntity, UUID> {
  @Query("SELECT c FROM ChatEntity c WHERE " +
      "(c.firstUsername = :username1 AND c.secondUsername = :username2) " +
      "OR (c.firstUsername = :username2 AND c.secondUsername = :username1)")
  Optional<ChatEntity> findByUsernames(@Param("username1") String username1, @Param("username2") String username2);

  @Query("SELECT c FROM ChatEntity c WHERE c.firstUsername = :username OR c.secondUsername = :username")
  Optional<List<ChatEntity>> findByUsername(@Param("username") String username);
}
