package com.enjoy.EnjoyClaude.infrastructures.persistence.repository;

import com.enjoy.EnjoyClaude.domains.auth.TokenBlacklist;
import com.enjoy.EnjoyClaude.domains.auth.TokenBlacklistRepository;
import com.enjoy.EnjoyClaude.infrastructures.persistence.assembler.TokenBlacklistPersistenceAssembler;
import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.TokenBlacklistEntity;
import com.enjoy.EnjoyClaude.infrastructures.persistence.jpa.TokenBlacklistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenBlacklistRepositoryImpl implements TokenBlacklistRepository {
    private final TokenBlacklistJpaRepository jpaRepository;
    private final TokenBlacklistPersistenceAssembler assembler;

    @Override
    public TokenBlacklist save(TokenBlacklist tokenBlacklist) {
        TokenBlacklistEntity entity = assembler.toEntity(tokenBlacklist);
        TokenBlacklistEntity savedEntity = jpaRepository.save(entity);
        return assembler.toDomain(savedEntity);
    }

    @Override
    public Optional<TokenBlacklist> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(assembler::toDomain);
    }

    @Override
    public boolean existsByToken(String token) {
        return jpaRepository.existsByToken(token);
    }

    @Override
    public void deleteExpiredTokens() {
        jpaRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
