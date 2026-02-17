package com.enjoy.EnjoyClaude.infrastructures.security;

import com.enjoy.EnjoyClaude.domains.common.exception.UserNotFoundException;
import com.enjoy.EnjoyClaude.domains.user.User;
import com.enjoy.EnjoyClaude.domains.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .disabled(!user.isEnabled())
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final User user) {
        return user.getRoles().stream()
                .flatMap(role -> {
                    final SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority(role.getName());
                    final java.util.List<SimpleGrantedAuthority> permissionAuthorities = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .collect(Collectors.toList());
                    permissionAuthorities.add(roleAuthority);
                    return permissionAuthorities.stream();
                })
                .collect(Collectors.toList());
    }
}
