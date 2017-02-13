package com.example.michaelmonty.mortgage_calculator_monty;

/**
 * Created by Michael Monty on 2/12/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class payment_sum_Activity {

    private TextView monthlyMortTextView;
    private TextView monthlyHOATextView;
    private TextView monthlyTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_sum);

        Intent payIntent = getIntent();

        String message = payIntent.getStringExtra("monthlyMort");
        monthlyMortTextView = (TextView)findViewById(R.id.monthlyMortgageText);
        monthlyMortTextView.setText(message);

        message = payIntent.getStringExtra("monthlyHOA");
        monthlyHOATextView = (TextView)findViewById(R.id.monthlyHOAText);
        monthlyHOATextView.setText(message);

        message = payIntent.getStringExtra("totalMonthlyPayment");
        monthlyTotalTextView = (TextView)findViewById(R.id.monthlyTotalText);
        monthlyTotalTextView.setText(message);
    }
}
