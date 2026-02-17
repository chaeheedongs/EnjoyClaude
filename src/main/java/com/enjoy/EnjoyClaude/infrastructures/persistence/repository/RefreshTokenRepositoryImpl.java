package com.enjoy.EnjoyClaude.infrastructures.persistence.repository;

import com.enjoy.EnjoyClaude.domains.auth.RefreshToken;
import com.enjoy.EnjoyClaude.domains.auth.RefreshTokenRepository;
import com.enjoy.EnjoyClaude.infrastructures.persistence.assembler.RefreshTokenPersistenceAssembler;
import com.enjoy.EnjoyClaude.infrastructures.persistence.jpa.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository jpaRepository;
    private final RefreshTokenPersistenceAssembler assembler;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        var entity = assembler.toEntity(refreshToken);
        var savedEntity = jpaRepository.save(entity);
        return assembler.toDomain(savedEntity);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(assembler::toDomain);
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        jpaRepository.deleteByToken(token);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        jpaRepository.deleteByUserId(userId);
    }
}
