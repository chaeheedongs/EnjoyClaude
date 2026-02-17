package com.enjoy.EnjoyClaude.config;

import com.enjoy.EnjoyClaude.domains.role.Permission;
import com.enjoy.EnjoyClaude.domains.role.PermissionRepository;
import com.enjoy.EnjoyClaude.domains.role.Role;
import com.enjoy.EnjoyClaude.domains.role.RoleRepository;
import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.domains.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== 초기 데이터 생성 시작 ===");

        // 1. 권한 생성
        final Permission readUsers = createPermission("READ:users", "사용자 읽기");
        final Permission writeUsers = createPermission("WRITE:users", "사용자 쓰기");
        final Permission readRoles = createPermission("READ:roles", "역할 읽기");
        final Permission writeRoles = createPermission("WRITE:roles", "역할 쓰기");
        final Permission readPosts = createPermission("READ:posts", "게시물 읽기");
        final Permission writePosts = createPermission("WRITE:posts", "게시물 쓰기");

        // 2. 역할 생성
        final Role userRole = createRole("ROLE_USER", "일반 사용자", Set.of(readPosts));
        final Role adminRole = createRole("ROLE_ADMIN", "관리자",
                Set.of(readUsers, writeUsers, readRoles, writeRoles, readPosts, writePosts));

        // 3. 관리자 계정 생성
        if (!userRepository.existsByEmail("admin@example.com")) {
            final User admin = new User(
                    null,
                    "admin@example.com",
                    "관리자",
                    passwordEncoder.encode("password123!"),
                    true,
                    new HashSet<>(Set.of(adminRole)),
                    null,
                    null
            );
            userRepository.save(admin);
            log.info("관리자 계정 생성: admin@example.com / password123!");
        }

        log.info("=== 초기 데이터 생성 완료 ===");
    }

    private Permission createPermission(final String name, final String description) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> {
                    final Permission permission = new Permission(null, name, description, null, null);
                    final Permission saved = permissionRepository.save(permission);
                    log.info("권한 생성: {}", name);
                    return saved;
                });
    }

    private Role createRole(final String name, final String description, final Set<Permission> permissions) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    final Role role = new Role(null, name, description, permissions, null, null);
                    final Role saved = roleRepository.save(role);
                    log.info("역할 생성: {}", name);
                    return saved;
                });
    }
}
