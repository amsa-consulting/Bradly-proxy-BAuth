package com.bradlyproxy.bradlyproxybauth.model;

import lombok.Data;

@Data
public class TransactTransactionalImport {
    private String cust_code;
    private String description;
    private String term_code;
    private String billno;
    private String busdate;
    private Double amount;
    private String batchno;
    private String cctype;
    private String paymenttype;
    private String paymentcode;
    private String ippsourceamountcode;
}
