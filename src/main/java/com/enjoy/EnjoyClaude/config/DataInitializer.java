package com.enjoy.EnjoyClaude.config;

import com.enjoy.EnjoyClaude.domains.code.PermissionType;
import com.enjoy.EnjoyClaude.domains.code.RoleType;
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
        final Permission readUsers = createPermission(PermissionType.READ_USERS);
        final Permission writeUsers = createPermission(PermissionType.WRITE_USERS);
        final Permission readRoles = createPermission(PermissionType.READ_ROLES);
        final Permission writeRoles = createPermission(PermissionType.WRITE_ROLES);
        final Permission readPosts = createPermission(PermissionType.READ_POSTS);
        final Permission writePosts = createPermission(PermissionType.WRITE_POSTS);

        // 2. 역할 생성
        final Role userRole = createRole(RoleType.USER, Set.of(readPosts));
        final Role adminRole = createRole(RoleType.ADMIN,
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

    private Permission createPermission(final PermissionType type) {
        return permissionRepository.findByName(type.getCode())
                .orElseGet(() -> {
                    final Permission permission = new Permission(null, type.getCode(), type.getDescription(), null, null);
                    final Permission saved = permissionRepository.save(permission);
                    log.info("권한 생성: {}", type.getCode());
                    return saved;
                });
    }

    private Role createRole(final RoleType type, final Set<Permission> permissions) {
        return roleRepository.findByName(type.getCode())
                .orElseGet(() -> {
                    final Role role = new Role(null, type.getCode(), type.getDescription(), permissions, null, null);
                    final Role saved = roleRepository.save(role);
                    log.info("역할 생성: {}", type.getCode());
                    return saved;
                });
    }
}
