package com.finfellows.domain.product.exception;

public class ProductTypeMismatchException extends RuntimeException {

    public ProductTypeMismatchException() {
        super("조회하려는 상품의 Type이 일치하지 않습니다.");
    }

}
