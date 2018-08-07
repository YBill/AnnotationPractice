package com.bill.annotationpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bill.testlib.GenerateCode;

@GenerateCode
public class MainActivity extends AppCompatActivity {

    private MainActivity$GA hello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hello = new MainActivity$GA();
    }


    public void handleClick(View view) {
        hello.print();
    }
}
