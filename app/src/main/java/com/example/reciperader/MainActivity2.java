package com.example.reciperader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    public static final String NAME = "NAME";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private ImageView image1;
    private TextView sign_in_txt;
    private TextView User_name1;
    private EditText txt1;
    private TextView pass1;
    private EditText pass_txt1;
    private CheckBox checkBox;
    private Button loginBtn1;
    private TextView ask1;
    private TextView txtSign;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        hooks();
        setupSharedPrefs();
        checkPrefs();



        loginBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnLoginOnClick();
            }
        });

        Intent intent2 = new Intent(this, MainActivity.class);
        txtSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);

            }
        });




    }

    private void hooks(){

        image1 = findViewById(R.id.image1);
        sign_in_txt = findViewById(R.id.sign_in_txt);
        User_name1 = findViewById(R.id.User_name1);
        txt1 = findViewById(R.id.txt1);
        pass1 = findViewById(R.id.pass1);
        pass_txt1 = findViewById(R.id.pass_txt1);
        checkBox = findViewById(R.id.checkBox);
        loginBtn1 = findViewById(R.id.loginBtn1);
        ask1 = findViewById(R.id.ask1);
        txtSign = findViewById(R.id.txtSign);

    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String name = prefs.getString(NAME, "");
            String password = prefs.getString(PASS, "");
            txt1.setText(name);
            pass_txt1.setText(password);
            checkBox.setChecked(true);
        }
    }

    private void setupSharedPrefs() {
        prefs = getSharedPreferences("shared", MODE_PRIVATE);
        editor = prefs.edit();
    }
    public void btnLoginOnClick() {

        String name = txt1.getText().toString();
        String password = pass_txt1.getText().toString();

        if(checkBox.isChecked()){
            if(!flag) {
                editor.putString(NAME, name);
                editor.putString(PASS, password);
                editor.putBoolean(FLAG, true);
                editor.commit();
            }

        }
        Users user = new Users(name, password);
        Intent intent3 = new Intent(this, MainActivity3.class);

        if(MainActivity.ar.contains(user)){
            startActivity(intent3);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "check you information", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


    }


}