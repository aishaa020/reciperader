package com.example.reciperader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView sign_up_txt;
    private TextView user_name;
    private EditText txt2;
    private TextView passwd;
    private EditText passwd_txt;
    private TextView con_pass;
    private EditText con_pass_txt;
    private Button button;
    private TextView txt4;
    private TextView txt5;
    public static ArrayList<Users> ar = new ArrayList<>();
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hooks();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txt2.getText().toString() == "" || passwd_txt.getText().toString() == "" || con_pass_txt.getText().toString() == ""){
                    Toast toast = Toast.makeText(getApplicationContext(), "There is Empty fields!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (!passwd_txt.getText().toString().equals(con_pass_txt.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(), "Check the password!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    ar.add(new Users(txt2.getText().toString(),passwd_txt.getText().toString() ));
                    if(MainActivity.ar != null){
                        Toast toast = Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    saveDueData();
                }



            }
        });


        Intent intent1 = new Intent(this, MainActivity2.class);
        txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent1);
            }
        });


    }

    private void hooks(){
        imageView = findViewById(R.id.imageView);
        sign_up_txt = findViewById(R.id.sign_up_txt);
        user_name = findViewById(R.id.user_name);
        txt2 = findViewById(R.id.txt2);
        passwd = findViewById(R.id.passwd);
        passwd_txt = findViewById(R.id.passwd_txt);
        con_pass = findViewById(R.id.con_pass);
        con_pass_txt = findViewById(R.id.con_pass_txt);
        button = findViewById(R.id.button);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
    }
    // Method to save the ArrayList to SharedPreferences
    private void saveDueData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ar);
        editor.putString("done list", json);
        editor.apply();

    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("done list", null);
        Type type = new TypeToken<ArrayList<Users>>(){}.getType();
        ar = gson.fromJson(json, type);

        if(ar == null){
            ar = new ArrayList<>();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        txt2.setText(txt2.getText());
        passwd_txt.setText(passwd_txt.getText());
        con_pass_txt.setText(con_pass_txt.getText());

        saveDueData();

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();

    }


}