package com.example.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimulatorViewModel extends ViewModel {
    private MutableLiveData<String> monthlyPayment = new MutableLiveData<>();

    // Calculer le paiement mensuel et mettre à jour monthlyPayment
    public void calculateMonthlyPayment(double loanAmount, double interestRate, int loanTerm) {
        double monthlyInterestRate = interestRate / 1200;
        int totalMonths = loanTerm * 12;

        double monthlyPaymentValue = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -totalMonths));

        monthlyPayment.setValue(String.format("%.2f", monthlyPaymentValue));
    }

    public LiveData<String> getMonthlyPayment() {
        return monthlyPayment;
    }
}
