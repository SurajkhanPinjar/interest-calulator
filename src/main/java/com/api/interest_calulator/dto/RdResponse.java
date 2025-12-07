package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RdResponse {
    private double monthlyDeposit;
    private double rate;
    private double years;
    private double maturityAmount;
    private double interestEarned;
}