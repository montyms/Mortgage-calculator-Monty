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

    private Float homeVal= new Float(0);
    private Float loanAmtVal= new Float(0);
    private Float loanTermVal= new Float(0);
    private Float insPerVal= new Float(0);
    private Float intRatePerc= new Float(0);
    private Float startYearVal = new Float(0);
    private Float propTaxPerc= new Float(0);
    private Float monthlyHOAVal= new Float(0);

    private boolean errorVisible = false;

    private EditText homeValEdit;
    private EditText loanAmtEdit;
    private EditText loanTermEdit;
    private EditText insPerEdit;
    private EditText intRateEdit;
    private EditText startYearEdit;
    private EditText propTaxEdit;
    private EditText monthlyHOAEdit;

    private Button mortSummButton;
    private Button paySummButton;

    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeValEdit = (EditText)findViewById(R.id.textEditHVal);
        loanAmtEdit = (EditText)findViewById(R.id.textEditLVal);
        loanTermEdit = (EditText)findViewById(R.id.textEditTerm);
        insPerEdit = (EditText)findViewById(R.id.textEditInsurance);
        intRateEdit = (EditText)findViewById(R.id.textEditInterest);
        startYearEdit = (EditText)findViewById(R.id.textEditStartYear);
        propTaxEdit = (EditText)findViewById(R.id.textEditPropTax);
        monthlyHOAEdit = (EditText)findViewById(R.id.textEditHOA);

        mortSummButton = (Button)findViewById(R.id.mortSumm);
        paySummButton = (Button)findViewById(R.id.paySumm);

        errorTextView = (TextView)findViewById(R.id.errorText);

        homeValEdit.setText(homeVal.toString());
        loanAmtEdit.setText(loanAmtVal.toString());
        loanTermEdit.setText(loanTermVal.toString());
        insPerEdit.setText(insPerVal.toString());
        intRateEdit.setText(intRatePerc.toString());
        startYearEdit.setText(startYearVal.toString());
        propTaxEdit.setText(propTaxPerc.toString());
        monthlyHOAEdit.setText(monthlyHOAVal.toString());



        mortSummButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(allValsFilled() == true){
                    Intent mortIntent = new Intent(MainActivity.this,mortSumActivity.class);
                    String[] calcValues = performCalcs();
                    mortIntent.putExtra("totalLoan", calcValues[0]);
                    mortIntent.putExtra("totalInterest", calcValues[1]);
                    mortIntent.putExtra("yearlyTax", calcValues[2]);
                    mortIntent.putExtra("totalPaid", calcValues[5]);
                    mortIntent.putExtra("taxesPaid", calcValues[6]);
                    mortIntent.putExtra("HOAPaid", calcValues[7]);

                    startActivity(mortIntent);

                }
                else{
                    errorTextView.setVisibility(View.VISIBLE);
                    errorVisible = true;
                }
            }
        });
        paySummButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(allValsFilled() == true){
                    Intent payIntent = new Intent(MainActivity.this,paySumActivity.class);
                    String[] calcValues = performCalcs();
                    payIntent.putExtra("monthlyMort", calcValues[3]);
                    payIntent.putExtra("monthlyHOA",monthlyHOAVal.toString());
                    payIntent.putExtra("totalMonthlyPayment", calcValues[4]);

                    startActivity(payIntent);
                }
                else{
                    errorTextView.setVisibility(View.VISIBLE);
                    errorVisible = true;
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getVals();

        outState.putFloat("homeValSave",homeVal);
        outState.putFloat("loanAmtSave",loanAmtVal);
        outState.putFloat("loanTermSave",loanTermVal);
        outState.putFloat("insPerSave",insPerVal);
        outState.putFloat("intRateSave",intRatePerc);
        outState.putFloat("startDateSave", startYearVal);
        outState.putFloat("propTaxSave",propTaxPerc);
        outState.putFloat("monthlyHOASave",monthlyHOAVal);
        outState.putBoolean("errorVisible",errorVisible);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        homeVal = savedInstanceState.getFloat("homeValSave");
        loanAmtVal = savedInstanceState.getFloat("loanAmtSave");
        loanTermVal = savedInstanceState.getFloat("loanTermSave");
        insPerVal = savedInstanceState.getFloat("insPerSave");
        intRatePerc = savedInstanceState.getFloat("intRateSave");
        startYearVal = savedInstanceState.getFloat("startDateSave");
        propTaxPerc = savedInstanceState.getFloat("propTaxSave");
        monthlyHOAVal = savedInstanceState.getFloat("monthlyHOASave");
        errorVisible = savedInstanceState.getBoolean("errorVisible");

        getVals();

        homeValEdit.setText(homeVal.toString());
        loanAmtEdit.setText(loanAmtVal.toString());
        loanTermEdit.setText(loanTermVal.toString());
        insPerEdit.setText(insPerVal.toString());
        intRateEdit.setText(intRatePerc.toString());
        startYearEdit.setText(startYearVal.toString());
        propTaxEdit.setText(propTaxPerc.toString());
        monthlyHOAEdit.setText(monthlyHOAVal.toString());

        if(errorVisible)
            errorTextView.setVisibility(View.VISIBLE);

    }

    private boolean allValsFilled(){
        boolean rtrnVal = true;
        if(homeValEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(loanAmtEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(loanTermEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(insPerEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(intRateEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(startYearEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(propTaxEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        if(monthlyHOAEdit.getText().toString().equals("0.0"))
            rtrnVal = false;
        return rtrnVal;
    }

    private String[] performCalcs(){
        String[] rtrnVal = new String[8];
        getVals();

        Double totalLoanCost = loanAmtVal.doubleValue();
        Double totalInterest;
        Double paidToDate = 0.00;
        Double monthlyMortPayment;
        Double totalMonthlyPayment;
        Double yearlyTax;
        Double taxesPaid;
        Double HOAPaid;
        Calendar cal = Calendar.getInstance();
        int numYears = cal.get(Calendar.YEAR)-startYearVal.intValue();
        int month = cal.get(Calendar.MONTH);

        for(int i = 0; i<loanTermVal.intValue();i++){
            totalLoanCost = totalLoanCost + totalLoanCost*(intRatePerc.doubleValue()/100);
            totalLoanCost = totalLoanCost.doubleValue();
        }
        rtrnVal[0] = totalLoanCost.toString();

        totalInterest = totalLoanCost - loanAmtVal.doubleValue();
        rtrnVal[1] = totalInterest.toString();

        yearlyTax = homeVal*propTaxPerc.doubleValue()/100;
        rtrnVal[2] = yearlyTax.toString();

        monthlyMortPayment = totalLoanCost/loanTermVal.doubleValue()/12;
        rtrnVal[3] = monthlyMortPayment.toString();

        totalMonthlyPayment = monthlyMortPayment + monthlyHOAVal.doubleValue()
                +(yearlyTax/12)+(insPerVal.doubleValue()/12);
        rtrnVal[4] = totalMonthlyPayment.toString();

        paidToDate = totalMonthlyPayment*numYears*12+(totalMonthlyPayment*month);
        rtrnVal[5] = paidToDate.toString();

        taxesPaid = yearlyTax*numYears;
        rtrnVal[6] = taxesPaid.toString();

        HOAPaid = monthlyHOAVal.doubleValue()*numYears*12+(monthlyHOAVal.doubleValue()*month);
        rtrnVal[7] = HOAPaid.toString();


        return rtrnVal;
    }

    private void getVals(){
        homeVal = Float.parseFloat(homeValEdit.getText().toString());
        loanAmtVal = Float.parseFloat(loanAmtEdit.getText().toString());
        loanTermVal = Float.parseFloat(loanTermEdit.getText().toString());
        insPerVal = Float.parseFloat(insPerEdit.getText().toString());
        intRatePerc = Float.parseFloat(intRateEdit.getText().toString());
        startYearVal = Float.parseFloat(startYearEdit.getText().toString());
        propTaxPerc = Float.parseFloat(propTaxEdit.getText().toString());
        monthlyHOAVal = Float.parseFloat(monthlyHOAEdit.getText().toString());
    }


}