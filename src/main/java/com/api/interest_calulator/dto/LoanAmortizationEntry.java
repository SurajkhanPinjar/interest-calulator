package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanAmortizationEntry {
    private int month;
    private double emi;
    private double interest;
    private double principal;
    private double remainingBalance;
}