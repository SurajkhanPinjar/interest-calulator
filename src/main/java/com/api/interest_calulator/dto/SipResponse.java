package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SipResponse {
    private double monthlySip;
    private double annualRate;
    private double years;
    private double totalInvested;
    private double futureValue;
    private double totalGain;
}