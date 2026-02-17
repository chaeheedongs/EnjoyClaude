package com.enjoy.EnjoyClaude.infrastructures.persistence.repository;

import com.enjoy.EnjoyClaude.domains.role.Permission;
import com.enjoy.EnjoyClaude.domains.role.PermissionRepository;
import com.enjoy.EnjoyClaude.infrastructures.persistence.assembler.PermissionPersistenceAssembler;
import com.enjoy.EnjoyClaude.infrastructures.persistence.jpa.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionJpaRepository jpaRepository;
    private final PermissionPersistenceAssembler assembler;

    @Override
    public Permission save(Permission permission) {
        var entity = assembler.toEntity(permission);
        var savedEntity = jpaRepository.save(entity);
        return assembler.toDomain(savedEntity);
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return jpaRepository.findById(id)
                .map(assembler::toDomain);
    }

    @Override
    public Optional<Permission> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(assembler::toDomain);
    }

    @Override
    public List<Permission> findAll() {
        return jpaRepository.findAll().stream()
                .map(assembler::toDomain)
                .collect(Collectors.toList());
    }
}
