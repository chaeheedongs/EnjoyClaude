package com.enjoy.EnjoyClaude.infrastructures.persistence.assembler;

import com.enjoy.EnjoyClaude.domains.auth.RefreshToken;
import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenPersistenceAssembler {
    public RefreshToken toDomain(final RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        return new RefreshToken(
                entity.getId(),
                entity.getToken(),
                entity.getUserId(),
                entity.getExpiresAt(),
                entity.getCreatedAt(),
                entity.getIpAddress(),
                entity.getUserAgent()
        );
    }

    public RefreshTokenEntity toEntity(final RefreshToken domain) {
        if (domain == null) {
            return null;
        }
        final RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(domain.getId());
        entity.setToken(domain.getToken());
        entity.setUserId(domain.getUserId());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setIpAddress(domain.getIpAddress());
        entity.setUserAgent(domain.getUserAgent());
        return entity;
    }
}
