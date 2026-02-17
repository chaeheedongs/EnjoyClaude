package com.enjoy.EnjoyClaude.domains.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);
    void deleteById(Long id);
}
