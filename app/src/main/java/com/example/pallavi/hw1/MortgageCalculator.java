package com.example.pallavi.hw1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MortgageCalculator extends AppCompatActivity {

    private EditText amountBorrowed;
    private TextView seekBarVal,monthlyPayment;
    private RadioGroup loanTermGroup;
    private RadioButton loanTermRadioVal;
    private CheckBox checkboxMonthlyPayment;
    private SeekBar interestRate;
    private double seekBarProgressValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_mortgage);

        amountBorrowed = (EditText) findViewById(R.id.amtBorrowed);
        amountBorrowed.setText(String.valueOf(0.0));

        final Button calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loanTermGroup = (RadioGroup) findViewById(R.id.radioGroupLoanterm);
                int selectedLoanTermVal = loanTermGroup.getCheckedRadioButtonId();
                String errorMssg = getString(R.string.radio_error_mssg);

                if(amountBorrowed.getText().toString().trim().equals("") || amountBorrowed.getText().toString().trim().equals("."))
                {
                    amountBorrowed.setError(getString(R.string.amtBorrowed_error_mssg));
                    return;
                }
                if (selectedLoanTermVal==-1)
                {
                    Toast.makeText(MortgageCalculator.this, errorMssg , Toast.LENGTH_LONG).show();
                    return;
                }
                loanTermRadioVal = (RadioButton) findViewById(selectedLoanTermVal);
                String radioValue = loanTermRadioVal.getText().toString();

                double T = 0;
                double M = 0, J, N,P;

                J=seekBarProgressValue/1200;
                N = Double.parseDouble(radioValue) * 12;
                P = Double.parseDouble(amountBorrowed.getText().toString());
                checkboxMonthlyPayment = (CheckBox) findViewById(R.id.taxes);
                boolean includeMonthlyPayment = checkboxMonthlyPayment.isChecked();

                if (includeMonthlyPayment) {
                    T = (0.1 / 100) * P;
                }

                if (seekBarProgressValue == 0) {
                    M = (P / N) + T;
                } else {
                    M = (P * (J / (1 - Math.pow((1 + J), -N)))) + T;
                }

                monthlyPayment = (TextView) findViewById(R.id.monthlyPayment);
                DecimalFormat twoDForm = new DecimalFormat("#.##");
                monthlyPayment.setText("$"+String.valueOf(twoDForm.format(M)));


            }
        });

        interestRate = (SeekBar) findViewById(R.id.interestRate);
        seekBarVal = (TextView) findViewById(R.id.interestRateValue);
        interestRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgressValue=(double)progress/10;
                seekBarVal.setText(String.valueOf(seekBarProgressValue)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mortgage_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
