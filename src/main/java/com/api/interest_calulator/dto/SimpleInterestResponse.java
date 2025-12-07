package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleInterestResponse {
    private double principal;
    private double rate;
    private double time;
    private String timeUnit;
    private double simpleInterest;
    private double totalAmount;
}