package com.example.realestatemanager.loansimulator;

import androidx.lifecycle.ViewModel;

public class LoanSimulatorViewModel extends ViewModel {
    public double calculateMonthlyPayment(double loanAmount, double interestRate, int loanTerm) {
        double monthlyInterestRate = interestRate / 100 / 12;
        int numberOfPayments = loanTerm * 12;
        return (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
    }
}
