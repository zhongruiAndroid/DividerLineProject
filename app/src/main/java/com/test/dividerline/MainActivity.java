package com.test.dividerline;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etNum;
    EditText etCount;
    AppCompatSeekBar sbHGap;

    AppCompatSeekBar sbVGap;

    AppCompatSeekBar sbMarginStart;
    AppCompatSeekBar sbMarginEnd;
    //    EditText etCrossPosition;
//    EditText etCrossNum;
    Button btType1;
    Button btType2;
    Button btType3;
    EditText etStartNum;
    EditText etEndNum;

    RadioButton rb1;
    RadioButton rb2;
    CheckBox cbReverse;
    CheckBox cbShowTop;
    CheckBox cbShowBottom;
    Button btRandomPosition;
    Random random = new Random();
    private int temp1 = -1;
    private int temp2 = -1;
    private int temp3 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        etCrossPosition = findViewById(R.id.etCrossPosition);
//        etCrossNum = findViewById(R.id.etCrossNum);
        sbHGap = findViewById(R.id.sbHGap);
        sbVGap = findViewById(R.id.sbVGap);
        sbMarginStart = findViewById(R.id.sbMarginStart);
        sbMarginEnd = findViewById(R.id.sbMarginEnd);
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

        etStartNum = findViewById(R.id.etStartNum);
        etEndNum = findViewById(R.id.etEndNum);
        btRandomPosition = findViewById(R.id.btRandomPosition);

        btRandomPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 1;
                if (TextUtils.isEmpty(etCount.getText()) == false) {
                    count = Integer.valueOf(etCount.getText().toString());
                }
                temp1 = random.nextInt(count) + 1;
                temp2 = random.nextInt(count) + 1;
                temp3 = random.nextInt(count) + 1;

                btRandomPosition.setText("点击随机生成3个不画线的position[" + temp1 + "," + temp2 + "," + temp3 + "]");
            }
        });
        btRandomPosition.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                temp1 = -1;
                temp2 = -1;
                temp3 = -1;
                btRandomPosition.setText("点击随机生成3个不画线的position[0]");
                return true;
            }
        });

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
        int hGap = sbHGap.getProgress();

        int vGap = sbVGap.getProgress();

        int startNum = 0;
        if (TextUtils.isEmpty(etStartNum.getText()) == false) {
            startNum = Integer.valueOf(etStartNum.getText().toString());
        }
        int endNum = 0;
        if (TextUtils.isEmpty(etEndNum.getText()) == false) {
            endNum = Integer.valueOf(etEndNum.getText().toString());
        }

        int marginStart = sbMarginStart.getProgress();
        int marginEnd = sbMarginEnd.getProgress();

        intent.putExtra("num", num);
        intent.putExtra("type", type);
        intent.putExtra("orientation", rb1.isChecked());
        intent.putExtra("reverse", cbReverse.isChecked());
        intent.putExtra("count", count);
        intent.putExtra("hGap", hGap);
        intent.putExtra("vGap", vGap);
        intent.putExtra("showTop", cbShowTop.isChecked());
        intent.putExtra("showBottom", cbShowBottom.isChecked());
        intent.putExtra("startNum", startNum);
        intent.putExtra("endNum", endNum);
        intent.putExtra("marginStart", marginStart);
        intent.putExtra("marginEnd", marginEnd);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(temp1);
        list.add(temp2);
        list.add(temp3);
        intent.putIntegerArrayListExtra("skipList", list);
        startActivity(intent);
    }

}
