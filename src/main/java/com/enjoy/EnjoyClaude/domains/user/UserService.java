package com.enjoy.EnjoyClaude.domains.user;

import com.enjoy.EnjoyClaude.domains.role.Role;
import com.enjoy.EnjoyClaude.domains.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;

    public User createUserWithDefaultRole(User user) {
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("기본 역할(ROLE_USER)이 존재하지 않습니다."));
        user.addRole(defaultRole);
        return user;
    }
}
