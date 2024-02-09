package com.example.realestatemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realestatemanager.R;
import com.example.realestatemanager.viewmodel.SimulatorViewModel;

public class SimulatorActivity extends AppCompatActivity {

    private SimulatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        intToolBar();

        viewModel = new ViewModelProvider(this).get(SimulatorViewModel.class);

        EditText editTextLoanAmount = findViewById(R.id.editTextLoanAmount);
        EditText editTextInterestRate = findViewById(R.id.editTextInterestRate);
        EditText editTextLoanTerm = findViewById(R.id.editTextLoanTerm);
        TextView textViewMonthlyPayment = findViewById(R.id.textViewMonthlyPayment);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);

        buttonCalculate.setOnClickListener(v -> {
            double loanAmount = Double.parseDouble(editTextLoanAmount.getText().toString());
            double interestRate = Double.parseDouble(editTextInterestRate.getText().toString());
            int loanTerm = Integer.parseInt(editTextLoanTerm.getText().toString());

            viewModel.calculateMonthlyPayment(loanAmount, interestRate, loanTerm);
        });

        viewModel.getMonthlyPayment().observe(this, payment -> textViewMonthlyPayment.setText(getString(R.string.monthly_payment, payment)));
    }

    private void intToolBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setTitle("Loan Simulator");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
