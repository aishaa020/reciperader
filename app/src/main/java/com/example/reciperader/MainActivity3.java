package com.example.reciperader;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private TextView textView2;
    private TextView textView6;
    private EditText rec_txt;
    private Button ser_btn;
    private EditText txtBox;
    private Button btn3;
    private Button btn4;
    private RequestQueue queue;
    private final static String key = "75dc93f47ca74bd2b4bc09d9f712caa8";

    //private static int recipeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        hooks();
        queue = Volley.newRequestQueue(this);

        ser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick_btn();


            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBox.setText("");
                rec_txt.setText("");
            }
        });


    }

    private void hooks(){
        textView2 = findViewById(R.id.textView2);
        textView6 = findViewById(R.id.textView6);
        rec_txt = findViewById(R.id.rec_txt);
        ser_btn = findViewById(R.id.ser_btn);
        txtBox = findViewById(R.id.txtBox);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
    }

    private void onclick_btn() {
        String url = "https://api.spoonacular.com/recipes/complexSearch" + "?apiKey=" + key;
        String query = url + "&query=" + rec_txt.getText().toString();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, query,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultsArray = response.getJSONArray("results");

                    if (resultsArray.length() > 0) {
                        JSONObject firstObject = resultsArray.getJSONObject(0);

                        int recipeId = firstObject.getInt("id");
                        String recipeTitle = firstObject.getString("title");

                        String recipeDetails = "Title: " + recipeTitle ;
                        txtBox.setText(recipeDetails);

                        sendSecondRequest(recipeId);
                        thirdRequest(recipeId);
                        fourthRequest(recipeId);


                    }
                } catch (JSONException exception) {
                    Log.d("volly", exception.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        queue.add(request);
    }


    private void sendSecondRequest(int recipeId) {
        String secondUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/ingredientWidget.json" + "?apiKey=" + key;

        JsonObjectRequest secondRequest = new JsonObjectRequest(Request.Method.GET, secondUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject secondResponse) {
                if (secondResponse.length() > 0) {
                    try {

                        JSONArray ingredientsArray = secondResponse.getJSONArray("ingredients");

                        List<String> ingredientNames = new ArrayList<>();
                        for (int i = 0; i < ingredientsArray.length(); i++) {
                            JSONObject ingredientObject = ingredientsArray.getJSONObject(i);
                            String ingredientName = ingredientObject.getString("name");
                            ingredientNames.add(ingredientName);
                        }

                        String existingText = txtBox.getText().toString();
                        String namesText = existingText + "\n" + "Ingredients:\n" + TextUtils.join("\n", ingredientNames);
                        txtBox.setText(namesText);


                    } catch (JSONException exception) {
                        Log.d("volly", exception.toString());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        queue.add(secondRequest);
    }


    private void thirdRequest(int recipeId) {
        String thirdUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/analyzedInstructions" + "?apiKey=" + key;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, thirdUrl,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                try {
                    if (responseArray.length() > 0) {

                        JSONObject firstObject = responseArray.getJSONObject(0);

                        JSONArray stepsArray = firstObject.getJSONArray("steps");

                        displaySteps(stepsArray);
                    }

                } catch (JSONException exception) {
                    Log.d("volly", exception.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        queue.add(request);
    }


    private void displaySteps(JSONArray stepsArray) {
        String stepDetails = "\n"+"Instructions:";
        try {
            for (int i = 0; i < stepsArray.length(); i++) {
                JSONObject stepObject = stepsArray.getJSONObject(i);

                String stepDescription = stepObject.getString("step");

                 stepDetails +=  "\n" + stepDescription;
                txtBox.append(stepDetails);
            }
        } catch (JSONException e) {
            Log.d("volly", e.toString());
        }
    }

    private void fourthRequest(int recipeId) {
        String fourthUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/nutritionWidget.json" + "?apiKey=" + key;
        Intent intent1 = new Intent(this, MainActivity4.class);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fourthUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray nutrientsArray = response.getJSONArray("nutrients");

                    StringBuilder nutrientsDetails = new StringBuilder("Nutrients:\n");
                    for (int i = 0; i < nutrientsArray.length(); i++) {
                        JSONObject nutrientObject = nutrientsArray.getJSONObject(i);
                        String name = nutrientObject.getString("name");
                        double amount = nutrientObject.getDouble("amount");
                        String unit = nutrientObject.getString("unit");


                        nutrientsDetails.append(name).append(": ").append(amount).append(" ").append(unit).append("\n");
                    }
                    intent1.putExtra("nutrients", nutrientsDetails.toString());

                    btn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(intent1);

                        }
                    });

                } catch (JSONException exception) {
                    Log.d("volly", exception.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });

        queue.add(request);
    }

    @Override
    protected void onStop() {
        super.onStop();
        txtBox.setText(txtBox.getText());
        rec_txt.setText(rec_txt.getText());

    }




}

