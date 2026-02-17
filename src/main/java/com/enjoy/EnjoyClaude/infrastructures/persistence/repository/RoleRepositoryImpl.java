package com.enjoy.EnjoyClaude.infrastructures.persistence.repository;

import com.enjoy.EnjoyClaude.domains.role.Role;
import com.enjoy.EnjoyClaude.domains.role.RoleRepository;
import com.enjoy.EnjoyClaude.infrastructures.persistence.assembler.RolePersistenceAssembler;
import com.enjoy.EnjoyClaude.infrastructures.persistence.jpa.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpaRepository jpaRepository;
    private final RolePersistenceAssembler assembler;

    @Override
    public Role save(Role role) {
        var entity = assembler.toEntity(role);
        var savedEntity = jpaRepository.save(entity);
        return assembler.toDomain(savedEntity);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return jpaRepository.findById(id)
                .map(assembler::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(assembler::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return jpaRepository.findAll().stream()
                .map(assembler::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
