package com.allen.singleactivity.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.singleactivity.ForResultActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentForResultBinding;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/6.
 */

public class ForResultFragment extends BaseFragment<FragmentForResultBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_for_result;
    }

    @Override
    protected void initView() {
        RxView.clicks(binding.exist).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {

            }
        });

        RxView.clicks(binding.startAnother).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(activity, ForResultActivity.class);

                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 123){
            FragmentIntent intent = getIntent();
            Bundle args = new Bundle();
            intent.putString("test", data.getStringExtra("test"));
            setResult(123, intent);
            finish();
        }
    }

    @Override
    protected void initData() {

    }
}
