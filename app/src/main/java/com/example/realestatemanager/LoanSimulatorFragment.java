package com.example.realestatemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoanSimulatorFragment extends AppCompatActivity {

    private EditText editTextLoanAmount;
    private EditText editTextInterestRate;
    private EditText editTextLoanTerm;
    private Button buttonCalculate;
    private TextView textViewMonthlyPayment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_simulator);

        // Initialisez les vues
        editTextLoanAmount = findViewById(R.id.editTextLoanAmount);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);
        editTextLoanTerm = findViewById(R.id.editTextLoanTerm);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewMonthlyPayment = findViewById(R.id.textViewMonthlyPayment);

        // Gérez le clic sur le bouton de calcul
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateMonthlyPayment();
            }
        });
    }

    private void calculateMonthlyPayment() {
        // Obtenez les valeurs entrées par l'utilisateur
        double loanAmount = Double.parseDouble(editTextLoanAmount.getText().toString());
        double interestRate = Double.parseDouble(editTextInterestRate.getText().toString());
        int loanTerm = Integer.parseInt(editTextLoanTerm.getText().toString());

        // Formule de calcul des paiements mensuels
        double monthlyInterestRate = interestRate / 100 / 12;
        int numberOfPayments = loanTerm * 12;
        double monthlyPayment = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));

        // Affichez le résultat dans la TextView
        //textViewMonthlyPayment.setText(getString(R.string.monthly_payment_result, monthlyPayment));
    }
}
