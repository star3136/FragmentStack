package com.allen.singleactivity.ui.fragment;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.singleactivity.MainActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentSingleInstance1Binding;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/6.
 */

public class SingleInstance1Fragment extends BaseFragment<FragmentSingleInstance1Binding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single_instance1;
    }

    @Override
    protected void initView() {
        RxView.clicks(binding.exist).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragment(new FragmentIntent(SingleInstanceFragment.class, FragmentIntent.FLAG_SINGLE_INSTANCE));
            }
        });
    }

    @Override
    protected void initData() {

    }
}
