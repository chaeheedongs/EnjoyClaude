package com.enjoy.EnjoyClaude.domains.auth;

import java.util.Optional;

public interface TokenBlacklistRepository {
    TokenBlacklist save(TokenBlacklist tokenBlacklist);
    Optional<TokenBlacklist> findByToken(String token);
    boolean existsByToken(String token);
    void deleteExpiredTokens();
}
