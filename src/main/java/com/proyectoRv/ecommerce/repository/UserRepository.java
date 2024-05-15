package com.proyectoRv.ecommerce.repository;

import com.proyectoRv.ecommerce.entity.UserEntity;
import com.proyectoRv.ecommerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmail(String email);

    UserEntity findByUserRole(UserRole userRole);
}
