package com.finfellows.domain.product.exception;

public class InvalidFinancialProductException extends RuntimeException {

    public InvalidFinancialProductException() {
        super("유효하지 않은 금융상품입니다.");
    }
}
