package com.example.michaelmonty.mortgage_calculator_monty;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Button mort_SumButton;
    private Button pay_SumButton;
    private TextView result_Feild;
    private TextView errorTextView;
    private boolean errorVisible = false;

    private Float interest_Rate = new Float(0);
    private Float property_Tax = new Float(0);
    private Float start_Date = new Float(0);
    private Float term_Date = new Float(0);
    private Float home_Value = new Float(0);
    private Float loan_Amount = new Float(0);
    private Float in_Per_Year = new Float(0);
    private Float h_O_A = new Float(0);
    private EditText interest_Rate_Edit;
    private EditText property_Tax_Edit;
    private EditText start_Date_Edit;
    private EditText term_Date_Edit;
    private EditText home_Value_Edit;
    private EditText loan_Amount_Edit;
    private EditText in_Per_Year_Edit;
    private EditText h_O_A_Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mort_SumButton = (Button)findViewById(R.id.mortSumButton);
        pay_SumButton = (Button)findViewById(R.id.paySumButton);
        result_Feild = (TextView)findViewById(R.id.resultField);

        home_Value_Edit = (EditText)findViewById(R.id.Home_Value);
        loan_Amount_Edit = (EditText)findViewById(R.id.Loan_Value);
        interest_Rate_Edit = (EditText)findViewById(R.id.interestRate);
        start_Date_Edit = (EditText)findViewById(R.id.startDate);
        term_Date_Edit = (EditText)findViewById(R.id.termDate);
        property_Tax_Edit = (EditText)findViewById(R.id.propertyTax);
        in_Per_Year_Edit = (EditText)findViewById(R.id.inPerYear);
        h_O_A_Edit = (EditText)findViewById(R.id.hOA);

        result_Feild.setText("Summary");

        home_Value_Edit.setText(home_Value.toString());
        loan_Amount_Edit.setText(loan_Amount.toString());
        interest_Rate_Edit.setText(interest_Rate.toString());
        start_Date_Edit.setText(start_Date.toString());
        term_Date_Edit.setText(term_Date.toString());
        property_Tax_Edit.setText(property_Tax.toString());
        in_Per_Year_Edit.setText(in_Per_Year.toString());
        h_O_A_Edit.setText(h_O_A.toString());


        mort_SumButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(allValsFilled() == true){
                    result_Feild.setText(home_Value-loan_Amount-interest_Rate-in_Per_Year-property_Tax-h_O_A-term_Date+start_Date.toString());
                }
                else{
                    errorTextView.setVisibility(View.VISIBLE);
                    errorVisible = true;
                }
            }
        });

        pay_SumButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(allValsFilled() == true){
                    result_Feild.setText(home_Value+loan_Amount+interest_Rate+in_Per_Year+property_Tax+h_O_A+term_Date+start_Date.toString());
                }
                else{
                    errorTextView.setVisibility(View.VISIBLE);
                    errorVisible =true;
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        getVals();

        outState.putFloat("saveHomeVal", home_Value);
        outState.putFloat("saveLoanVal", loan_Amount);
        outState.putFloat("saveInterestRate", interest_Rate);
        outState.putFloat("savePropertyTax", property_Tax);
        outState.putFloat("saveLoanTerm", term_Date);
        outState.putFloat("saveLoanStart", start_Date);
        outState.putFloat("saveIntPerYear", in_Per_Year);
        outState.putFloat("saveHOA", h_O_A);
        outState.putBoolean("errorVisible", errorVisible);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        home_Value = savedInstanceState.getFloat("saveHomeVal");
        loan_Amount = savedInstanceState.getFloat("saveLoanVal");
        interest_Rate = savedInstanceState.getFloat("saveInterestRate");
        property_Tax = savedInstanceState.getFloat("savePropertyTax");
        term_Date = savedInstanceState.getFloat("saveLoanTerm");
        start_Date = savedInstanceState.getFloat("saveLoanStart");
        in_Per_Year = savedInstanceState.getFloat("saveIntPerYear");
        h_O_A = savedInstanceState.getFloat("saveHOA");
        errorVisible = savedInstanceState.getBoolean("errorVisible");

        getVals();

        home_Value_Edit.setText(home_Value.toString());
        loan_Amount_Edit.setText(loan_Amount.toString());
        interest_Rate_Edit.setText(interest_Rate.toString());
        property_Tax_Edit.setText(property_Tax.toString());
        term_Date_Edit.setText(term_Date.toString());
        start_Date_Edit.setText(start_Date.toString());
        in_Per_Year_Edit.setText(in_Per_Year.toString());
        h_O_A_Edit.setText(h_O_A.toString());

        if (errorVisible)
            errorTextView.setVisibility(View.VISIBLE);
    }

    private boolean allValsFilled(){
        boolean returnVal = true;
        if (home_Value_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (loan_Amount_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (interest_Rate_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (property_Tax_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (term_Date_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (start_Date_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (in_Per_Year_Edit.getText().toString().equals(0.0))
            returnVal = false;
        if (h_O_A_Edit.getText().toString().equals(0.0))
            returnVal = false;
        return returnVal;
    }

    private String[] performCal(){
        String[] returnVal = new String[8];
        getVals();

        Double total_loan_cost = loan_Amount.doubleValue();
        Double total_interest;
        Double current_paid = 0.0;
        Double month_mort_pay;
        Double total_month_pay;
        Double yearlyTax;
        Double taxes_paid;
        Double HOAPaid;
        Calendar cal = Calendar.getInstance();
        int numYears = cal.get(Calendar.YEAR) - start_Date.intValue();
        int month = cal.get(Calendar.MONTH);

        for (int i = 0; i < term_Date.intValue(); i++){
            total_loan_cost = total_loan_cost + total_loan_cost*(interest_Rate.doubleValue()/100);
            total_loan_cost = total_loan_cost.doubleValue();
        }

        returnVal[0] = total_loan_cost.toString();

        total_interest = total_loan_cost - loan_Amount.doubleValue();
        returnVal[1] = total_interest.toString();

        yearlyTax = home_Value*property_Tax.doubleValue()/100;
        returnVal[2] = yearlyTax.toString();

        month_mort_pay = total_loan_cost/term_Date.doubleValue()/100;
        returnVal[3] = month_mort_pay.toString();

        total_month_pay = month_mort_pay + h_O_A.doubleValue() + (yearlyTax/12) + (in_Per_Year.doubleValue()/12);
        returnVal[4] = total_month_pay.toString();

        current_paid = total_month_pay*12*numYears + (total_month_pay*month);
        returnVal[5] = current_paid.toString();

        taxes_paid = yearlyTax*numYears;
        returnVal[6] = taxes_paid.toString();

        HOAPaid = h_O_A.doubleValue()*12*numYears + (h_O_A.doubleValue()*month);
        returnVal[7] = HOAPaid.toString();

        return returnVal;
    }

    private void getVals(){
        home_Value = Float.parseFloat(home_Value_Edit.getText().toString());
        loan_Amount = Float.parseFloat(loan_Amount_Edit.getText().toString());
        interest_Rate = Float.parseFloat(interest_Rate_Edit.getText().toString());
        property_Tax = Float.parseFloat(property_Tax_Edit.getText().toString());
        start_Date = Float.parseFloat(start_Date_Edit.getText().toString());
        term_Date = Float.parseFloat(term_Date_Edit.getText().toString());
        in_Per_Year = Float.parseFloat(in_Per_Year_Edit.getText().toString());
        h_O_A = Float.parseFloat(h_O_A_Edit.getText().toString());
    }


}