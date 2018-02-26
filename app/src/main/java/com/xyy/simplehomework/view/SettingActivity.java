package com.xyy.simplehomework.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.databinding.ActivitySettingBinding;
import com.xyy.simplehomework.entity.MySubject;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private MySubject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        subject = new MySubject("测试科目", R.color.japanTea);
        binding.setTestSubject(subject);
        subject.name = "1";
    }

    @Override
    public void onClick(View v) {
        try {
            subject.setName(String.valueOf(Integer.parseInt(subject.name) + 1));
        } catch (NumberFormatException e) {
            subject.setName("1");
            Toast.makeText(SettingActivity.this, "sb, 输入数字！", Toast.LENGTH_SHORT).show();
        }
    }
}
