package ru.netologia.sharinm_task_512;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView txtDisplayResult;
    private TextView txtDisplayValue;
    private TextView txtDisplayOperation;
    private String value1 = " ";
    private char operation = ' ';
    private String value2 = " ";
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){

        saveInstanceState.putString("displayValue", txtDisplayValue.getText().toString());
        saveInstanceState.putString("displayResult", txtDisplayResult.getText().toString());
        saveInstanceState.putString("displayOperation",txtDisplayOperation.getText().toString());

        if (linearLayout.getVisibility() == View.VISIBLE){
            saveInstanceState.putBoolean("calculateEngineer", true);
        } else {
            saveInstanceState.putBoolean("calculateEngineer", false);
        }

        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);

        txtDisplayOperation.setText(saveInstanceState.getString("displayOperation"));
        txtDisplayResult.setText(saveInstanceState.getString("displayResult"));
        txtDisplayValue.setText(saveInstanceState.getString("displayValue"));

        if (saveInstanceState.getBoolean("calculateEngineer")){
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case R.id.menuCalculatorMain:
                linearLayout.setVisibility(View.GONE);
                break;
            case R.id.menuCalculatorEngineer:
                linearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.menuSettings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                 break;
        }

        item.setChecked(true);

        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        txtDisplayResult = findViewById(R.id.displayResult);
        txtDisplayValue = findViewById(R.id.displayValue);
        txtDisplayOperation = findViewById(R.id.displayOperation);

        NumberButtonClickListener numberListener = new NumberButtonClickListener();
        int[] buttonIdNumber = new int[] { R.id.button0, R.id.button1, R.id.button2,
                                     R.id.button3, R.id.button4, R.id.button5,
                                     R.id.button6, R.id.button7, R.id.button8,
                                     R.id.button9,R.id.buttonPoint};

        for(int buttonId : buttonIdNumber) {
            findViewById(buttonId).setOnClickListener(numberListener);
        }

        OperationButtonClickListener numberListenerOperation = new OperationButtonClickListener();
        int[] buttonIdsOperation = new int[] { R.id.buttonSing, R.id.buttonDivision, R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiplication,
                                                R.id.buttonEqually, R.id.buttonSin, R.id.buttonCos, R.id.buttonLog, R.id.buttonLn,
                                                R.id.buttonN, R.id.buttonPi, R.id.buttonSquare, R.id.buttonRadix, R.id.buttonDelete,
                                                R.id.buttonClear, R.id.buttonPercent};

        for(int buttonId : buttonIdsOperation) {
            findViewById(buttonId).setOnClickListener(numberListenerOperation);
        }

        OperationClearButtonClickListener operationClearListener = new OperationClearButtonClickListener();
        int[] buttonIdsClear = new int[] {  R.id.buttonDelete, R.id.buttonClear};

        for(int buttonId : buttonIdsClear) {
            findViewById(buttonId).setOnClickListener(operationClearListener);
        }

        linearLayout = findViewById(R.id.linearLayoutEngineer);
    }

    class OperationButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (txtDisplayResult.getText().toString().equals(" ")) {
                Toast.makeText(MainActivity.this, getString(R.string.textErrorValue), Toast.LENGTH_LONG).show();

            } else {
                if (operation == ' ') {
                    value1 = txtDisplayResult.getText().toString();
                    txtDisplayValue.setText(value1);
                } else {
                    value2 = txtDisplayResult.getText().toString();
                }

                if(!(txtDisplayOperation.getText().toString().equals(" ")) && (v.getId() != R.id.buttonEqually)) {
                    txtDisplayValue.setText(EquallyOperation(value1, value2, operation));
                    value1 = txtDisplayValue.getText().toString();
                }

                switch (v.getId()) {
                    case R.id.buttonSing:
                        txtDisplayResult.setText(EquallyOperation(txtDisplayResult.getText().toString(), "-1", '*'));
                        break;
                    case R.id.buttonPercent:
                        txtDisplayResult.setText(EquallyOperation(EquallyOperation(value1, txtDisplayResult.getText().toString(), '*'), "100", '/'));
                        break;
                    case R.id.buttonDivision:
                        if (value1.equals("0")) {
                            Toast.makeText(MainActivity.this, getString(R.string.textErrorZeroValue1), Toast.LENGTH_LONG).show();
                        } else {
                            if (value2.equals("0")) {
                                Toast.makeText(MainActivity.this, getString(R.string.textErrorZeroValue2), Toast.LENGTH_LONG).show();
                            } else {
                                operation = '/';
                                txtDisplayResult.setText(" ");
                                txtDisplayOperation.setText("/");
                            }
                        }
                        break;
                    case R.id.buttonPlus:
                        operation = '+';
                        txtDisplayOperation.setText("+");
                        txtDisplayResult.setText(" ");
                        break;
                    case R.id.buttonMinus:

                        operation = '-';
                        txtDisplayOperation.setText("-");
                        txtDisplayResult.setText(" ");
                        break;
                    case R.id.buttonMultiplication:
                        operation = '*';
                        txtDisplayOperation.setText("*");
                        txtDisplayResult.setText(" ");
                        break;
                    case R.id.buttonEqually:
                        if ((operation == '/') && ((value1.equals("0")) || (value2.equals("0")))) {
                            Toast.makeText(MainActivity.this, getString(R.string.textErrorZeroValue2), Toast.LENGTH_LONG).show();
                        } else {
                            txtDisplayResult.setText(EquallyOperation(value1, value2, operation));
                            operation = ' ';
                            txtDisplayValue.setText("");
                            txtDisplayOperation.setText("");
                            value2 = " ";
                            value1 = " ";
                        }
                        break;
                    default:
                        Toast.makeText(MainActivity.this, getString(R.string.textMessageNotification), Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }

    class OperationClearButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (txtDisplayResult.getText().toString().equals("0")) {
                Toast.makeText(MainActivity.this, getString(R.string.textMessageDeleteOrClear), Toast.LENGTH_LONG).show();
            } else {
                switch (v.getId()) {
                    case R.id.buttonClear:
                        String display = txtDisplayResult.getText().toString();
                        if (display.length() == 1) {
                            txtDisplayResult.setText("0");
                        } else {
                            txtDisplayResult.setText(display.substring(0, display.length() - 1));
                        }
                        break;
                    case R.id.buttonDelete:
                        txtDisplayResult.setText("0");
                        txtDisplayOperation.setText(" ");
                        txtDisplayValue.setText(" ");
                        operation = ' ';
                        value1 = " ";
                        value2 = " ";
                        break;
                }
            }
        }
    }

    class NumberButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String addText = "";
            String displayText = txtDisplayResult.getText().toString();

            switch (v.getId()) {
                case R.id.button0:
                    addText = "0";
                    break;
                case R.id.button1:
                    addText = "1";
                    break;
                case R.id.button2:
                    addText = "2";
                    break;
                case R.id.button3:
                    addText = "3";
                    break;
                case R.id.button4:
                    addText = "4";
                    break;
                case R.id.button5:
                    addText = "5";
                    break;
                case R.id.button6:
                    addText = "6";
                    break;
                case R.id.button7:
                    addText = "7";
                    break;
                case R.id.button8:
                    addText = "8";
                    break;
                case R.id.button9:
                    addText = "9";
                    break;
                case R.id.buttonPoint:
                    if (displayText.contains(".")) {
                        Toast.makeText(MainActivity.this, getString(R.string.textMessageTwoPoint), Toast.LENGTH_LONG).show();
                    } else {
                        if (displayText.equals("0")) {
                            addText = "0.";
                        } else {
                            addText = ".";
                        }
                    }
                    break;
            }

            if (displayText.equals("0")) {
                displayText = addText;
            } else {
                displayText += addText;
            }

            txtDisplayResult.setText(displayText);
        }
    }

    public String EquallyOperation(String val1, String val2, char oper) {
        String result = "";

        switch (oper) {
            case '/':
                result = String.valueOf((Float.parseFloat(val1) / Float.parseFloat(val2)));
                break;
            case '+':
                result = String.valueOf((Float.parseFloat(val1) + Float.parseFloat(val2)));
                break;
            case '-':
                result = String.valueOf((Float.parseFloat(val1) - Float.parseFloat(val2)));
                break;
            case '*':
                result = String.valueOf((Float.parseFloat(val1) * Float.parseFloat(val2)));
                break;
        }
        return result;
    }
}