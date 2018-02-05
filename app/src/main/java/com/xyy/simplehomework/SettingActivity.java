package com.xyy.simplehomework;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xyy.simplehomework.databinding.ActivitySettingBinding;
import com.xyy.simplehomework.entity.MySubject;
import com.xyy.simplehomework.handler.TestHandler;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        MySubject subject = new MySubject("测试科目", R.color.japanTea);
        TestHandler testHandler = new TestHandler(this);
        binding.setTestSubject(subject);
        binding.setClickHandler(testHandler);
        subject.name = "变了";
    }
}
