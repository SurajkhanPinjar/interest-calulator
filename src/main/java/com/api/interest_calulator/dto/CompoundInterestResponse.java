package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompoundInterestResponse {
    private double principal;
    private double rate;
    private double years;
    private int compoundsPerYear;
    private double finalAmount;
    private double compoundInterest;
}