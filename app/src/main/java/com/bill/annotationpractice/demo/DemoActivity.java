package com.bill.annotationpractice.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bill.annotationpractice.R;
import com.bill.annotationpractice.demo.IdInject;
import com.bill.annotationpractice.demo.IdInjectProcessor;

public class DemoActivity extends AppCompatActivity {

    @IdInject(R.id.text)
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        IdInjectProcessor.inject(this);

        if (text == null) {
            Toast.makeText(getApplicationContext(), "text is null", Toast.LENGTH_LONG).show();
        } else {
            text.setText("Running annotation!");
        }
    }
}
