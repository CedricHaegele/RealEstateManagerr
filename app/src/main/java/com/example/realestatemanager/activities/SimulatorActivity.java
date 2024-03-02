package com.example.realestatemanager.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.realestatemanager.R;
import com.example.realestatemanager.viewmodel.SimulatorViewModel;

public class SimulatorActivity extends AppCompatActivity {

    // ViewModel for the simulator logic
    private SimulatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator); // Setting the layout for this activity

        initToolBar(); // Initialize the toolbar settings

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(SimulatorViewModel.class);

        // Getting references to the UI elements
        EditText editTextDownPayment = findViewById(R.id.editTextDownPayment);
        EditText editTextLoanAmount = findViewById(R.id.editTextLoanAmount);
        EditText editTextInterestRate = findViewById(R.id.editTextInterestRate);
        EditText editTextLoanTerm = findViewById(R.id.editTextLoanTerm);
        TextView textViewMonthlyPayment = findViewById(R.id.textViewMonthlyPayment);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);

        // Set the onClickListener for the calculate button
        buttonCalculate.setOnClickListener(v -> {
            // Retrieve input values from EditTexts
            String loanAmountStr = editTextLoanAmount.getText().toString();
            String interestRateStr = editTextInterestRate.getText().toString();
            String loanTermStr = editTextLoanTerm.getText().toString();
            String downPaymentStr = editTextDownPayment.getText().toString();

            // Validate and parse input values
            if (!loanAmountStr.isEmpty() && !interestRateStr.isEmpty() && !loanTermStr.isEmpty()) {
                try {
                    double loanAmount = Double.parseDouble(loanAmountStr);
                    double interestRate = Double.parseDouble(interestRateStr);
                    int loanTerm = Integer.parseInt(loanTermStr);
                    double downPayment = Double.parseDouble(downPaymentStr);

                    // Calculate the monthly payment
                    viewModel.calculateMonthlyPayment(loanAmount, interestRate, loanTerm, downPayment);
                } catch (NumberFormatException e) {
                    // Set error message for invalid input
                    textViewMonthlyPayment.setText(getString(R.string.input_error));
                }
            } else {
                // Set error message for empty input fields
                textViewMonthlyPayment.setText(getString(R.string.input_error));
            }
        });

        // Observe the LiveData for monthly payment and update UI
        viewModel.getMonthlyPayment().observe(this, payment -> {
            // Format and display the calculated monthly payment
            String formattedPayment = getString(R.string.monthly_payment, payment);
            textViewMonthlyPayment.setText(formattedPayment);
        });

    }

    private void initToolBar() {
        // Initialize toolbar settings
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable "Up" button
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Loan Simulator"); // Set toolbar title
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle "Up" button click in toolbar
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
