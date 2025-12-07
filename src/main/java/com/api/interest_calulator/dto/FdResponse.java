package com.api.interest_calulator.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FdResponse {
    private double principal;
    private double rate;
    private double years;
    private int compoundsPerYear;
    private double maturityAmount;
    private double interestEarned;
}