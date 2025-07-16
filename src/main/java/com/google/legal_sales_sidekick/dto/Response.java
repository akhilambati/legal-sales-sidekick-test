package com.google.legal_sales_sidekick.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class Response {
    private Object data;
    private String errorMessage;
    private String responseMessage;
    private Integer responseCode;
}
