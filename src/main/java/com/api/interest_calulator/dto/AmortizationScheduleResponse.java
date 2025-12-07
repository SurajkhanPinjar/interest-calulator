package com.api.interest_calulator.dto;

import com.api.interest_calulator.dto.LoanAmortizationEntry;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmortizationScheduleResponse {
    private double principal;
    private double annualRate;
    private int tenureMonths;
    private double monthlyEmi;
    private List<LoanAmortizationEntry> schedule;
}