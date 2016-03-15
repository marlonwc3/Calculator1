package com.example.mras.calculadora1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String output_text = "";
    long first_num;
    long second_num;
    char operator;
    boolean has_operator = false;
    boolean has_first = false;
    boolean has_second = false;

    private boolean canApply(){
        return has_first && has_operator && has_second;
    }
    private long apply(char op){
        if(op=='+'){
            return first_num+second_num;
        }
        else if(op=='-'){
            return first_num-second_num;
        }
        else if(op == '/'){
            return first_num/second_num;
        }
        else{
            return first_num*second_num;
        }
    }
    private void setResult(){

        long result = apply(operator);
        first_num = result;
        output_text = "" + result;
        has_first = true;
        has_operator = false;
        has_second  = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void setInput(View view){
        String text = ((Button)view).getText().toString();
        char op = text.charAt(0);
        if(op == '=' ){
            if(!canApply()) return;
            else if(second_num == 0 && operator == '/'){
                has_first = has_second = has_operator = false;
                output_text = "Cannot divide by zero.";
            }
            else{
                setResult();
            }
        }
        else if(op == '+' || op == '-' || op == '*' || op == '/'){
            if(!has_first) return;
            if(has_first && has_operator && !has_second) return;
            if(canApply()){
                if(second_num == 0 && operator == '/'){
                    has_first = has_second = has_operator = false;
                    output_text = "Cannot divide by zero.";
                    return;
                }
                else {
                    setResult();
                }
            }
            operator = op;
            output_text += operator;
            has_operator = true;
        }
        else if( op >= '0' && op <= '9'){
            if(!has_first){
                first_num = op-'0';
                has_first = true;
            }
            else if(has_first && !has_operator){
                first_num = 10*first_num + op-'0';
            }
            else if(!has_second){
                second_num = op-'0';
                has_second = true;
            }
            else if(has_second){
                second_num = 10*second_num + op - '0';
            }
            output_text = ""+first_num;
            if(has_operator) output_text += operator;
            if(has_second) output_text += second_num;
        }
        else if(op == 'C'){
            has_first = has_second = has_operator = false;
            output_text = "Let's do some math?";
        }
        TextView editText = (TextView) findViewById(R.id.output_text);
        editText.setText(output_text);
    }
}

