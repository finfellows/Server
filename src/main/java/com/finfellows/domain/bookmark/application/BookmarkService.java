package com.finfellows.domain.bookmark.application;

import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import org.springframework.http.ResponseEntity;

public interface BookmarkService {
    Message insert(UserPrincipal userPrincipal, Long id);

    Message delete(UserPrincipal userPrincipal, Long id);

}
