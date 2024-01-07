package com.finfellows.domain.policyinfo.exception;

public class InvalidPolicyInfoException extends RuntimeException {

    public InvalidPolicyInfoException() {
        super("정책 정보가 올바르지 않습니다.");
    }

}
