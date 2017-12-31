package com.example.josepablo.tipsavingcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormatValue = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormatValue = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double tipPercent = 0.25;
    private TextView textBillAmount;
    private TextView textTipPercent;
    private TextView textTip;
    private TextView textTotalBillAmount;

    private double totalSalary = 0.0;
    private double savingsPercent = 0.25;
    private TextView textSavePercent;
    private TextView textMoneySaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assignment of ids and listeners

        textBillAmount = (TextView) findViewById(R.id.textBillAmount);
        textTipPercent = (TextView) findViewById(R.id.textTipPercent);
        textTip = (TextView) findViewById(R.id.textTip);
        textTotalBillAmount = (TextView) findViewById(R.id.textTotalBillAmount);

        textTip.setText(currencyFormatValue.format(0));
        textTotalBillAmount.setText(currencyFormatValue.format(0));

        textSavePercent = (TextView) findViewById(R.id.textSavePercent);
        textMoneySaved = (TextView) findViewById(R.id.textMoneySaved);
        textMoneySaved.setText(currencyFormatValue.format(0));

        EditText edtMoneyAmount = (EditText) findViewById(R.id.edtMoneyAmount);
        edtMoneyAmount.addTextChangedListener(tipEdtMoneyAmountTextWatcher);

        SeekBar seekBarPercent = (SeekBar) findViewById(R.id.seekBarTipPercent);
        seekBarPercent.setOnSeekBarChangeListener(tipSeekBarChangeListener);

        EditText edtSalary = (EditText) findViewById(R.id.edtSalary);
        edtSalary.addTextChangedListener(edtSalaryChangedTextWatcher);

        SeekBar seekBarSavePercent = (SeekBar) findViewById(R.id.seekBarSavePercent);
        seekBarSavePercent.setOnSeekBarChangeListener(seekBarSavePercentChangeListener);

    }

    //Tip calculator logic

    private final TextWatcher tipEdtMoneyAmountTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        //When the user types the value it is divided by 100.0 so it can be possible to
        //work with numbers that have 2 digits. Then the app shows the value to the user
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            try {

                billAmount = Double.parseDouble(charSequence.toString()) / 100.0;
                textBillAmount.setText(currencyFormatValue.format(billAmount));

            } catch (NumberFormatException exception) {

                textBillAmount.setText("");
                billAmount = 0.0;
            }

            showAllTipsValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private final SeekBar.OnSeekBarChangeListener tipSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        //Takes the value of the progress bar to divide it by 100 so it can be possible to obtain the
        //percent of the bill amount in the function " showAllTipsValues" then shows all the tips values
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            tipPercent = progress / 100.0;
            showAllTipsValues();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {


        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private double calculateTipValue(){

        double tipValue = billAmount * tipPercent;
        return tipValue;

    }

    private double calculateTotalBillValue(double tipValue)
    {
        double totalValue = billAmount + tipValue;
        return totalValue;
    }

    private void showAllTipsValues() {

        textTipPercent.setText(percentFormatValue.format(tipPercent));

        double tipValue = calculateTipValue();
        double totalValue = calculateTotalBillValue(tipValue);

        textTip.setText(currencyFormatValue.format(tipValue));
        textTotalBillAmount.setText(currencyFormatValue.format(totalValue));

    }

    //Savings calculator logic

    private final TextWatcher edtSalaryChangedTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            try{

                totalSalary = Double.parseDouble(charSequence.toString());
                showSavedValues();

            }catch (NumberFormatException exception){

                totalSalary = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            
        }
    };

    private final SeekBar.OnSeekBarChangeListener seekBarSavePercentChangeListener =
            new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            savingsPercent = progress / 100.0;
            showSavedValues();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private double calculateSavedMoney(){

        double savedMoney = (totalSalary * savingsPercent);
        return savedMoney;
    }

    private void showSavedValues(){

        textSavePercent.setText(percentFormatValue.format(savingsPercent));
        double savedMoney = calculateSavedMoney();
        textMoneySaved.setText(currencyFormatValue.format(savedMoney));
    }
}