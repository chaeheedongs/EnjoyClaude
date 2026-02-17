package com.enjoy.EnjoyClaude.domains.user;

import com.enjoy.EnjoyClaude.domains.code.RoleType;
import com.enjoy.EnjoyClaude.domains.common.exception.RoleNotFoundException;
import com.enjoy.EnjoyClaude.domains.role.Role;
import com.enjoy.EnjoyClaude.domains.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RoleRepository roleRepository;

    public User createUserWithDefaultRole(final User user) {
        final Role defaultRole = roleRepository.findByName(RoleType.USER.getCode())
                .orElseThrow(() -> new RoleNotFoundException("기본 역할(" + RoleType.USER.getCode() + ")이 존재하지 않습니다."));
        user.addRole(defaultRole);
        return user;
    }
}
