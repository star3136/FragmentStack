package com.allen.singleactivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.allen.singleactivity.databinding.ActivityForResultBinding;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2017/1/18.
 */

public class ForResultActivity extends AppCompatActivity {
    private ActivityForResultBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_for_result);

        RxView.clicks(binding.exist).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = getIntent();
                intent.putExtra("test", "从ForResultActivity返回的数据");
                setResult(123, intent);
                finish();
            }
        });
    }
}
