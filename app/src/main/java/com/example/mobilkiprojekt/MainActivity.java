package com.example.mobilkiprojekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button locationButton;
    Button creditsButton;
    Button weatherButton;

    public static final String EXTRA_MESSAGE = "com.example.mobilkiprojekt.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void WeatherGo(View view){
        Intent intent = new Intent(this,WeatherView.class);
        weatherButton = (Button) findViewById(R.id.ShowWeatherButton);
        startActivity(intent);
    }

    public void CreditsGo(View view){
        Intent intent = new Intent(this,Credits.class);
        creditsButton = (Button) findViewById(R.id.CreditsButton);
        startActivity(intent);
    }

    public void LocationGo (View view){
        Intent intent = new Intent(this,GoogleMaps.class);
        locationButton = (Button) findViewById(R.id.ShowLocationButton);
        startActivity(intent);
    }

}