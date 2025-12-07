package com.api.interest_calulator.controller;

import com.api.interest_calulator.dto.*;
import com.api.interest_calulator.service.InterestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interest")
@RequiredArgsConstructor
@Tag(name = "Interest Calculator API")
@SecurityRequirement(name = "ApiKeyAuth")
public class InterestController {

    private final InterestService interestService;

    @Operation(summary = "Calculate Simple Interest")
    @GetMapping("/simple")
    public SimpleInterestResponse simple(
            @Parameter(description = "Principal amount") @RequestParam double principal,
            @Parameter(description = "Rate of interest (annual %)") @RequestParam double rate,
            @Parameter(description = "Time duration") @RequestParam double time,
            @Parameter(description = "years/months") @RequestParam(defaultValue = "years") String timeUnit
    ) {
        return interestService.calculateSimpleInterest(principal, rate, time, timeUnit);
    }

    @Operation(summary = "Calculate Compound Interest")
    @GetMapping("/compound")
    public CompoundInterestResponse compound(
            @Parameter(description = "Principal amount") @RequestParam double principal,
            @Parameter(description = "Interest rate in %") @RequestParam double rate,
            @Parameter(description = "Number of years") @RequestParam double years,
            @Parameter(description = "Compounding frequency per year") @RequestParam(defaultValue = "1") int n
    ) {
        return interestService.calculateCompoundInterest(principal, rate, years, n);
    }

    @Operation(summary = "Calculate Loan EMI")
    @GetMapping("/emi")
    public EmiResponse emi(
            @Parameter(description = "Loan amount") @RequestParam double principal,
            @Parameter(description = "Annual interest rate in %") @RequestParam double annualRate,
            @Parameter(description = "Loan tenure in months") @RequestParam int tenureMonths
    ) {
        return interestService.calculateEmi(principal, annualRate, tenureMonths);
    }

    @Operation(summary = "Calculate SIP Future Value")
    @GetMapping("/sip/future-value")
    public SipResponse sip(
            @Parameter(description = "Monthly SIP amount") @RequestParam double monthlySip,
            @Parameter(description = "Annual return %") @RequestParam double annualRate,
            @Parameter(description = "Investment duration in years") @RequestParam double years
    ) {
        return interestService.calculateSipFutureValue(monthlySip, annualRate, years);
    }

    @Operation(summary = "Calculate FD Maturity Amount")
    @GetMapping("/fd")
    public FdResponse fd(
            @Parameter(description = "Fixed Deposit amount") @RequestParam double principal,
            @Parameter(description = "Annual interest rate %") @RequestParam double rate,
            @Parameter(description = "Tenure in years") @RequestParam double years,
            @Parameter(description = "Compounding frequency") @RequestParam(defaultValue = "4") int n
    ) {
        return interestService.calculateFd(principal, rate, years, n);
    }

    @Operation(summary = "Loan Amortization Schedule (Month-by-month breakdown)")
    @GetMapping("/loan/amortization")
    public AmortizationScheduleResponse amortization(
            @RequestParam double principal,
            @RequestParam double annualRate,
            @RequestParam int tenureMonths
    ) {
        return interestService.generateAmortizationSchedule(principal, annualRate, tenureMonths);
    }

    @Operation(summary = "Recurring Deposit (RD) maturity calculator")
    @GetMapping("/rd")
    public RdResponse rd(
            @RequestParam double monthlyDeposit,
            @RequestParam double rate,
            @RequestParam double years
    ) {
        return interestService.calculateRd(monthlyDeposit, rate, years);
    }

    @Operation(summary = "Savings Goal Calculator (required SIP or lumpsum)")
    @GetMapping("/savings-goal")
    public com.api.interest.dto.SavingsGoalResponse savingsGoal(
            @RequestParam double targetAmount,
            @RequestParam double annualRate,
            @RequestParam double years
    ) {
        return interestService.calculateSavingsGoal(targetAmount, annualRate, years);
    }
}