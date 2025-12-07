package com.api.interest_calulator.service;

import com.api.interest_calulator.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterestService {

    // 1️⃣ Simple Interest
    public SimpleInterestResponse calculateSimpleInterest(double principal, double rate, double time, String timeUnit) {

        if (timeUnit.equalsIgnoreCase("months")) {
            time = time / 12.0;
        }

        double si = (principal * rate * time) / 100;
        double total = principal + si;

        return SimpleInterestResponse.builder()
                .principal(principal)
                .rate(rate)
                .time(time)
                .timeUnit(timeUnit)
                .simpleInterest(si)
                .totalAmount(total)
                .build();
    }

    // 2️⃣ Compound Interest
    public CompoundInterestResponse calculateCompoundInterest(double principal, double rate, double years, int n) {
        double r = rate / 100.0;
        double amount = principal * Math.pow(1 + r / n, n * years);
        double ci = amount - principal;

        return CompoundInterestResponse.builder()
                .principal(principal)
                .rate(rate)
                .years(years)
                .compoundsPerYear(n)
                .finalAmount(round(amount))
                .compoundInterest(round(ci))
                .build();
    }

    // 3️⃣ EMI Calculator
    public EmiResponse calculateEmi(double principal, double annualRate, int tenureMonths) {

        double r = (annualRate / 12) / 100; // monthly interest
        double emi = (principal * r * Math.pow(1 + r, tenureMonths)) /
                (Math.pow(1 + r, tenureMonths) - 1);

        double totalPayable = emi * tenureMonths;
        double totalInterest = totalPayable - principal;

        return EmiResponse.builder()
                .principal(principal)
                .annualRate(annualRate)
                .tenureMonths(tenureMonths)
                .monthlyEmi(round(emi))
                .totalPayable(round(totalPayable))
                .totalInterest(round(totalInterest))
                .build();
    }

    // 4️⃣ SIP Future Value
    public SipResponse calculateSipFutureValue(double monthlySip, double annualRate, double years) {

        double r = (annualRate / 12) / 100;
        double n = years * 12;

        double futureValue = monthlySip * (Math.pow(1 + r, n) - 1) / r * (1 + r);
        double totalInvested = monthlySip * n;

        return SipResponse.builder()
                .monthlySip(monthlySip)
                .annualRate(annualRate)
                .years(years)
                .totalInvested(totalInvested)
                .futureValue(round(futureValue))
                .totalGain(round(futureValue - totalInvested))
                .build();
    }

    // 5️⃣ FD Maturity
    public FdResponse calculateFd(double principal, double rate, double years, int n) {

        double r = rate / 100.0;
        double maturity = principal * Math.pow(1 + r / n, n * years);
        double interest = maturity - principal;

        return FdResponse.builder()
                .principal(principal)
                .rate(rate)
                .years(years)
                .compoundsPerYear(n)
                .maturityAmount(round(maturity))
                .interestEarned(round(interest))
                .build();
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public AmortizationScheduleResponse generateAmortizationSchedule(double principal, double annualRate, int tenureMonths) {

        double r = (annualRate / 12) / 100;
        double emi = (principal * r * Math.pow(1 + r, tenureMonths)) /
                (Math.pow(1 + r, tenureMonths) - 1);

        List<LoanAmortizationEntry> schedule = new ArrayList<>();

        double remaining = principal;

        for (int month = 1; month <= tenureMonths; month++) {

            double interest = remaining * r;
            double principalPaid = emi - interest;
            remaining -= principalPaid;

            if (remaining < 0) remaining = 0; // Cleanup last entry

            schedule.add(
                    LoanAmortizationEntry.builder()
                            .month(month)
                            .emi(round(emi))
                            .interest(round(interest))
                            .principal(round(principalPaid))
                            .remainingBalance(round(remaining))
                            .build()
            );
        }

        return AmortizationScheduleResponse.builder()
                .principal(principal)
                .annualRate(annualRate)
                .tenureMonths(tenureMonths)
                .monthlyEmi(round(emi))
                .schedule(schedule)
                .build();
    }

    public RdResponse calculateRd(double monthlyDeposit, double rate, double years) {

        int months = (int)(years * 12);
        double r = rate / 100 / 12;

        double maturity = monthlyDeposit * (Math.pow(1 + r, months) - 1) / r;

        double totalInvested = monthlyDeposit * months;
        double interest = maturity - totalInvested;

        return RdResponse.builder()
                .monthlyDeposit(monthlyDeposit)
                .rate(rate)
                .years(years)
                .maturityAmount(round(maturity))
                .interestEarned(round(interest))
                .build();
    }

    public com.api.interest.dto.SavingsGoalResponse calculateSavingsGoal(double targetAmount, double annualRate, double years) {

        double r = (annualRate / 12) / 100;
        int n = (int)(years * 12);

        // Required SIP
        double sip = (targetAmount * r) / ((Math.pow(1 + r, n) - 1) * (1 + r));

        // Required Lumpsum
        double lumpsum = targetAmount / Math.pow(1 + annualRate / 100, years);

        return com.api.interest.dto.SavingsGoalResponse.builder()
                .targetAmount(targetAmount)
                .years(years)
                .annualRate(annualRate)
                .requiredMonthlySip(round(sip))
                .requiredLumpsum(round(lumpsum))
                .build();
    }
}