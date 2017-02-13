package com.example.michaelmonty.mortgage_calculator_monty;

/**
 * Created by Michael Monty on 2/12/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Mortgage_Sum_Activity {

    private TextView totalLoanTextView;
    private TextView totalIntTextView;
    private TextView yearlyTaxTextView;
    private TextView totalPaidTextView;
    private TextView taxesPaidTextView;
    private TextView HOAPaidTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mort_sum);

        Intent mortIntent = getIntent();

        String message = mortIntent.getStringExtra("totalLoan");
        totalLoanTextView = (TextView)findViewById(R.id.totalLoanText);
        totalLoanTextView.setText(message);

        message = mortIntent.getStringExtra("totalInterest");
        totalIntTextView = (TextView)findViewById(R.id.totalInterestText);
        totalIntTextView.setText(message);

        message = mortIntent.getStringExtra("yearlyTax");
        yearlyTaxTextView = (TextView)findViewById(R.id.yearlyTaxText);
        yearlyTaxTextView.setText(message);

        message = mortIntent.getStringExtra("totalPaid");
        totalPaidTextView = (TextView)findViewById(R.id.totalPaidText);
        totalPaidTextView.setText(message);

        message = mortIntent.getStringExtra("taxesPaid");
        taxesPaidTextView = (TextView)findViewById(R.id.totalTaxText);
        taxesPaidTextView.setText(message);

        message = mortIntent.getStringExtra("HOAPaid");
        HOAPaidTextView = (TextView)findViewById(R.id.totalHOAText);
        HOAPaidTextView.setText(message);

    }


}
