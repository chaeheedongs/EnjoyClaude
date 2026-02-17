package com.enjoy.EnjoyClaude.infrastructures.persistence.jpa;

import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
