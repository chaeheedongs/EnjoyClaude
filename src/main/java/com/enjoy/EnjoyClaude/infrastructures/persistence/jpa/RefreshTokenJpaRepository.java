package com.enjoy.EnjoyClaude.infrastructures.persistence.jpa;

import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByToken(String token);
    void deleteByUserId(Long userId);
}
