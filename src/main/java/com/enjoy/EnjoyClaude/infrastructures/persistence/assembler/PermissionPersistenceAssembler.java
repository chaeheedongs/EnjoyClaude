package com.enjoy.EnjoyClaude.infrastructures.persistence.assembler;

import com.enjoy.EnjoyClaude.domains.role.Permission;
import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionPersistenceAssembler {
    public Permission toDomain(PermissionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Permission(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PermissionEntity toEntity(Permission domain) {
        if (domain == null) {
            return null;
        }
        PermissionEntity entity = new PermissionEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    public void updateEntity(Permission domain, PermissionEntity entity) {
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
    }
}
