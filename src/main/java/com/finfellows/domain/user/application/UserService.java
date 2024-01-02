package com.finfellows.domain.user.application;

import com.finfellows.domain.auth.domain.Token;
import com.finfellows.domain.auth.domain.repository.TokenRepository;
import com.finfellows.domain.common.Status;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.error.DefaultException;
import com.finfellows.global.payload.ApiResponse;
import com.finfellows.global.payload.ErrorCode;
import com.finfellows.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {



}
