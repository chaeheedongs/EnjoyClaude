package com.enjoy.EnjoyClaude.infrastructures.persistence.jpa;

import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByName(String name);
}
