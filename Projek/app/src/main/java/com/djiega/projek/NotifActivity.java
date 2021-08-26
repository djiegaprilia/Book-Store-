package com.djiega.projek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NotifActivity extends AppCompatActivity {
    private EditText editTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        editTextInput = findViewById(R.id.edit_text_input);
    }
    public void startService(View v){
        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", input);

        startService(serviceIntent);
    }
//    public void stopService(View v){
//        Intent serviceIntent = new Intent(this, ExampleService.class);
//        startService(serviceIntent);
//    }
}
