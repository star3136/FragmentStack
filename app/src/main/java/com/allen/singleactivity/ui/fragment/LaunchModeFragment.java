package com.allen.singleactivity.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.singleactivity.MainActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentTwoBinding;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/5.
 */

public class LaunchModeFragment extends BaseFragment<FragmentTwoBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        OneFragment fragment = (OneFragment) fm.findFragmentByTag(OneFragment.class.getName());
        if(fragment == null){
            binding.textView.setText("fragment 为空");
        }else {
            binding.textView.setText("fragment 不为空");
        }

        RxView.clicks(binding.btnSingleTop).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                FragmentIntent intent = new FragmentIntent(LaunchModeFragment.class, FragmentIntent.FLAG_SINGLE_TOP);
                intent.putString("test", "测试测试");
                startFragment(intent);
            }
        });
        RxView.clicks(binding.btnSingleTask).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragment(SingleTaskFragment.class);
            }
        });

        RxView.clicks(binding.btnSingleInstance).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragment(new FragmentIntent(SingleInstanceFragment.class, FragmentIntent.FLAG_SINGLE_INSTANCE));
            }
        });
    }

    @Override
    public void onNewIntent(FragmentIntent intent) {
        super.onNewIntent(intent);
        binding.textView.setText(intent.getString("test"));
    }

    @Override
    protected void initData() {

    }
}
