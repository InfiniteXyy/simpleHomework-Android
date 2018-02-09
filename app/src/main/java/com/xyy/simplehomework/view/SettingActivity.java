package com.xyy.simplehomework.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.ActivitySettingBinding;
import com.xyy.simplehomework.entity.MySubject;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        final MySubject subject = new MySubject("测试科目", R.color.japanTea);
        binding.setTestSubject(subject);
        subject.name = "变了";
    }
}
