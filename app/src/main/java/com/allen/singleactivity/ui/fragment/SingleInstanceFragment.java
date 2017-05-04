package com.allen.singleactivity.ui.fragment;

import android.widget.Toast;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.singleactivity.MainActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentSingleInstanceBinding;
import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/6.
 */

public class SingleInstanceFragment extends BaseFragment<FragmentSingleInstanceBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single_instance;
    }

    @Override
    protected void initView() {
        RxView.clicks(binding.notExist).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragment(new FragmentIntent(SingleInstance1Fragment.class));
            }
        });

    }

    @Override
    protected void initData() {

    }
}
