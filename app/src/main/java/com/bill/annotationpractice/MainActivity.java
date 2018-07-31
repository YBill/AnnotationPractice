package com.bill.annotationpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bill.annotationpractice.fun.AnnotationFunActivity;
import com.bill.annotationpractice.demo.DemoActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                gotoAct(AnnotationFunActivity.class);
                break;
            case R.id.btn_2:
                gotoAct(DemoActivity.class);
                break;
        }

    }

    private void gotoAct(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }


}
