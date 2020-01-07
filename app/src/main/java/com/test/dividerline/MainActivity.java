package com.test.dividerline;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etNum;
    EditText etCount;
    //    EditText etCrossPosition;
//    EditText etCrossNum;
    Button btType1;
    Button btType2;
    Button btType3;

    RadioButton rb1;
    RadioButton rb2;
    CheckBox cbReverse;
    CheckBox cbShowTop;
    CheckBox cbShowBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        etCrossPosition = findViewById(R.id.etCrossPosition);
//        etCrossNum = findViewById(R.id.etCrossNum);
        etNum = findViewById(R.id.etNum);
        etCount = findViewById(R.id.etCount);
        btType1 = findViewById(R.id.btType1);
        btType2 = findViewById(R.id.btType2);
        btType3 = findViewById(R.id.btType3);

        btType1.setOnClickListener(this);
        btType2.setOnClickListener(this);
        btType3.setOnClickListener(this);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        cbReverse = findViewById(R.id.cbReverse);
        cbShowTop = findViewById(R.id.cbShowTop);
        cbShowBottom = findViewById(R.id.cbShowBottom);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btType1:
                start(1);
                break;
            case R.id.btType2:
                start(2);
                break;
            case R.id.btType3:
                start(3);
                break;
        }
    }

    public void start(int type) {
        Intent intent = new Intent(this, ListActivity.class);
        int num = 1;
        if (TextUtils.isEmpty(etNum.getText()) == false) {
            num = Integer.valueOf(etNum.getText().toString());
        }
        int count = 1;
        if (TextUtils.isEmpty(etCount.getText()) == false) {
            count = Integer.valueOf(etCount.getText().toString());
        }
//        int crossPosition = 1;
//        if (TextUtils.isEmpty(etCrossPosition.getText()) == false) {
//            crossPosition = Integer.valueOf(etCrossPosition.getText().toString());
//        }
//        int crossNum = 1;
//        if (TextUtils.isEmpty(etCrossNum.getText()) == false) {
//            crossNum = Integer.valueOf(etCrossNum.getText().toString());
//        }
        intent.putExtra("num", num);
        intent.putExtra("type", type);
        intent.putExtra("orientation", rb1.isChecked());
        intent.putExtra("reverse", cbReverse.isChecked());
        intent.putExtra("count", count);
//        intent.putExtra("crossPosition",crossPosition);
//        intent.putExtra("crossNum",crossNum);
        intent.putExtra("showTop", cbShowTop.isChecked());
        intent.putExtra("showBottom", cbShowBottom.isChecked());
        startActivity(intent);
    }

}
