package com.mrhenry.repository;

import com.mrhenry.entity.UserEntity;
import com.mrhenry.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {
}
