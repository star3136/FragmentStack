package com.allen.singleactivity.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.singleactivity.MainActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentSingleTaskBinding;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/5.
 */

public class SingleTaskFragment extends BaseFragment<FragmentSingleTaskBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single_task;
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

        RxView.clicks(binding.exist).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                FragmentIntent intent = new FragmentIntent(OneFragment.class, FragmentIntent.FLAG_SINGLE_TASK);
                Bundle args = new Bundle();
                intent.putString("test", "测试测试");

                startFragment(intent);
            }
        });

        RxView.clicks(binding.notExist).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                FragmentIntent intent = new FragmentIntent(OtherFragment.class, FragmentIntent.FLAG_SINGLE_TASK);
                startFragment(intent);
            }
        });
    }

    @Override
    public void onNewIntent(FragmentIntent intent) {
        binding.textView.setText(intent.getString("test"));
    }

    @Override
    protected void initData() {

    }
}
