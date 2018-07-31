package com.bill.annotationpractice.fun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bill.annotationpractice.R;

public class AnnotationFunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_fun);
    }

    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                ParseAnnotation.parseTypeAnnotation();
                break;
            case R.id.btn_2:
                ParseAnnotation.parseConstructAnnotation();
                break;
            case R.id.btn_3:
                ParseAnnotation.parseFieldAnnotation();
                break;
            case R.id.btn_4:
                ParseAnnotation.parseMethodAnnotation();
                break;
        }

    }

}
