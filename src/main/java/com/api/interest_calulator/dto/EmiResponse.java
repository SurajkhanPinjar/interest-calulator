package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmiResponse {
    private double principal;
    private double annualRate;
    private int tenureMonths;
    private double monthlyEmi;
    private double totalPayable;
    private double totalInterest;
}