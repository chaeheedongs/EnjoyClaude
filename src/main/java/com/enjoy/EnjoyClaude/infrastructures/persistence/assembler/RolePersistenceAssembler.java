package com.enjoy.EnjoyClaude.infrastructures.persistence.assembler;

import com.enjoy.EnjoyClaude.domains.role.Role;
import com.enjoy.EnjoyClaude.infrastructures.persistence.entity.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RolePersistenceAssembler {
    private final PermissionPersistenceAssembler permissionAssembler;

    public Role toDomain(final RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Role(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPermissions().stream()
                        .map(permissionAssembler::toDomain)
                        .collect(Collectors.toSet()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RoleEntity toEntity(final Role domain) {
        if (domain == null) {
            return null;
        }
        final RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPermissions(domain.getPermissions().stream()
                .map(permissionAssembler::toEntity)
                .collect(Collectors.toSet()));
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    public void updateEntity(final Role domain, final RoleEntity entity) {
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPermissions(domain.getPermissions().stream()
                .map(permissionAssembler::toEntity)
                .collect(Collectors.toSet()));
    }
}
