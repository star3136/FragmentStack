package com.allen.singleactivity.ui.fragment;

import android.os.Bundle;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.singleactivity.MainActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentOneBinding;
import com.allen.singleactivity.entity.TestEvent;
import com.hwangjr.rxbus.RxBus;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/5.
 */

public class OneFragment extends BaseFragment<FragmentOneBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView() {
        RxView.clicks(binding.change).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                TestEvent event = new TestEvent();
                event.str = binding.textView.getText().toString();
                RxBus.get().post(event);
            }
        });
        RxView.clicks(binding.btnLaunchMode).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragment(LaunchModeFragment.class);
            }
        });

        RxView.clicks(binding.btnForResult).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragmentForResult(new FragmentIntent(ForResultFragment.class), 1000);
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, FragmentIntent intent) {
        if(requestCode == 1000 && resultCode == 123){
            Bundle args = intent.getArguments();
            binding.textView.setText(args.getString("test"));
        }
    }

    @Override
    public void onNewIntent(FragmentIntent intent) {
        binding.textView.setText(intent.getString("test"));
    }

    @Override
    protected void initData() {

    }
}
