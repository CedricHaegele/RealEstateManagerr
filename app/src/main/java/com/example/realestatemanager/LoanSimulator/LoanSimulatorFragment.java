package com.example.realestatemanager.LoanSimulator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.realestatemanager.R;

public class LoanSimulatorFragment extends Fragment {

    private EditText editTextLoanAmount;
    private EditText editTextInterestRate;
    private EditText editTextLoanTerm;
    private TextView textViewMonthlyPayment;
    private LoanSimulatorViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan_simulator, container, false);

        editTextLoanAmount = view.findViewById(R.id.editTextLoanAmount);
        editTextInterestRate = view.findViewById(R.id.editTextInterestRate);
        editTextLoanTerm = view.findViewById(R.id.editTextLoanTerm);
        Button buttonCalculate = view.findViewById(R.id.buttonCalculate);
        textViewMonthlyPayment = view.findViewById(R.id.textViewMonthlyPayment);
        viewModel = new ViewModelProvider(this).get(LoanSimulatorViewModel.class);
        buttonCalculate.setOnClickListener(v -> calculateMonthlyPayment());

        return view;
    }

    private void calculateMonthlyPayment() {
        String loanAmountStr = editTextLoanAmount.getText().toString();
        String interestRateStr = editTextInterestRate.getText().toString();
        String loanTermStr = editTextLoanTerm.getText().toString();

        if (!loanAmountStr.isEmpty() && !interestRateStr.isEmpty() && !loanTermStr.isEmpty()) {
            double loanAmount = Double.parseDouble(loanAmountStr);
            double interestRate = Double.parseDouble(interestRateStr);
            int loanTerm = Integer.parseInt(loanTermStr);

            double monthlyPayment = viewModel.calculateMonthlyPayment(loanAmount, interestRate, loanTerm);
            textViewMonthlyPayment.setText(getString(R.string.monthly_payment_result, monthlyPayment));
        } else {
            textViewMonthlyPayment.setText("Veuillez remplir tous les champs.");
        }
    }
}
