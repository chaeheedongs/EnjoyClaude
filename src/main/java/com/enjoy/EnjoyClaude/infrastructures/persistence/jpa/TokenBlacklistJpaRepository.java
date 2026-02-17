package com.enjoy.EnjoyClaude.infrastructures.persistence.jpa;

import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.TokenBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenBlacklistJpaRepository extends JpaRepository<TokenBlacklistEntity, Long> {
    Optional<TokenBlacklistEntity> findByToken(String token);
    boolean existsByToken(String token);

    @Modifying
    @Query("DELETE FROM TokenBlacklistEntity t WHERE t.expiresAt < :now")
    void deleteExpiredTokens(LocalDateTime now);
}
