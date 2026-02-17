package com.enjoy.EnjoyClaude.infrastructures.persistence.assembler;

import com.enjoy.EnjoyClaude.domains.auth.TokenBlacklist;
import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.TokenBlacklistEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistPersistenceAssembler {
    public TokenBlacklist toDomain(final TokenBlacklistEntity entity) {
        if (entity == null) {
            return null;
        }
        return new TokenBlacklist(
                entity.getId(),
                entity.getToken(),
                entity.getExpiresAt(),
                entity.getCreatedAt()
        );
    }

    public TokenBlacklistEntity toEntity(final TokenBlacklist domain) {
        if (domain == null) {
            return null;
        }
        final TokenBlacklistEntity entity = new TokenBlacklistEntity();
        entity.setId(domain.getId());
        entity.setToken(domain.getToken());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
