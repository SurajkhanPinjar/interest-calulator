package com.api.interest.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsGoalResponse {
    private double targetAmount;
    private double years;
    private double annualRate;
    private double requiredMonthlySip;
    private double requiredLumpsum;
}