package com.finfellows.domain.product.dto.request;

import lombok.Data;

@Data
public class BankUploadReq {

    private String dcls_month;
    private String fin_co_no;
    private String kor_co_nm;
    private String dcls_chrg_man;
    private String homp_url;
    private String cal_tel;

}
