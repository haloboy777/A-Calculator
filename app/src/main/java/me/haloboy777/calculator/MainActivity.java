package me.haloboy777.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText calculation;
    private TextView newOp;
    private String op;
    private static final String TAG = "MainActivity";
    //Variables to hold the operands and type of calculations

    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";
//    private final String Cal_Content = "Calculation_content";
//    private final String Res_Content = "Result_content";
    private final String Op_Content = "Operation_content";
//    private final String Op_Val = "Operation_Value";
    private final String Op1_Content = "Operand1";
    private final String Op2_Content = "Operand2";
    private final String pendingOp_Content = "Pending_Operation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.result);
        calculation = (EditText) findViewById(R.id.calculation);
        newOp = (TextView) findViewById(R.id.textView);

        result.setText("");
        calculation.setText("");

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        Button buttonClear = (Button) findViewById(R.id.buttonClear);
        Button buttonPlus = (Button) findViewById(R.id.buttonPlus);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonEqual = (Button) findViewById(R.id.buttonEqual);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                calculation.append(b.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                op = b.getText().toString();
                Log.d(TAG, "onClick: operand has been registered");
                String value = calculation.getText().toString();
                try {
                    if (op.equals("C"))
                    {
                        Log.d(TAG, "onClick: C is pressed");
                        operand1 = null;
                        operand2 = null;
                        calculation.setText("");
                        result.setText("");
                        newOp.setText("");
                        op = "";
                    }
                    else {
                        Double doubleValue = Double.valueOf(value);
                        performOperation(doubleValue, op);
                    }
                } catch (NumberFormatException e) {
                    calculation.setText("");
                }
                pendingOperation = op;
                newOp.setText(op);
            }
        };
        View.OnClickListener negListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.d(TAG, "onClick: Neg has been pressed");
                    if (calculation.length() == 0){
                        calculation.setText("-");
                    } else {
                        try{
                            Double x;
                            x = Double.parseDouble(calculation.getText().toString());
                            x *= -1;
                            calculation.setText(String.valueOf(x));
                            Log.d(TAG, "onClick: Neg Value"+x);
                        } catch (NumberFormatException e) {
                            calculation.setText("");
                        }
                    }

            }
        };
        buttonMultiply.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonNeg.setOnClickListener(negListener);
        buttonClear.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonEqual.setOnClickListener(opListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString(Cal_Content, calculation.getText().toString());
//        outState.putString(Res_Content, result.getText().toString());
        outState.putString(Op_Content, newOp.getText().toString());
//        outState.putString(Op_Val, op);
        try {
            outState.putDouble(Op1_Content, operand1);
            outState.putString(pendingOp_Content, pendingOperation);
            outState.putDouble(Op2_Content, operand2);
        } catch (NullPointerException e){
            Log.d(TAG, "onSaveInstanceState: HHEEEERRRRRRRRRREEEEEEEEEEE!!!!!!!!!!");
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        String Cal_String = savedInstanceState.getString(Cal_Content);
//        String Res_String = savedInstanceState.getString(Res_Content);
//        calculation.setText(Cal_String);
//        result.setText(Res_String);
        newOp.setText(savedInstanceState.getString(Op_Content));
//        op = savedInstanceState.getString(Op_Val);
        Log.d(TAG, "onRestoreInstanceState: ########################");
        operand1 = savedInstanceState.getDouble(Op1_Content);
        operand2 = savedInstanceState.getDouble(Op2_Content);
        pendingOperation = savedInstanceState.getString(pendingOp_Content);
    }

    private void performOperation(Double value, String operation){
        if(null == operand1){
            operand1 =  value;
        }else {
            operand2 = value;
            if (pendingOperation.equals("=")){
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if (operand2==0){
                        operand1=0.0;
                    }else {
                        operand1/=operand2;
                    }
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
            }
        }
        result.setText(operand1.toString());
        calculation.setText("");
    }
}
