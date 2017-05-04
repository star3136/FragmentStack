package com.allen.singleactivity.ui.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;

/**
 * Created by Allen on 2016/12/5.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends com.allen.fragmentstack.RootFragment {
    protected T binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initView();
        initData();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe
    public void onEvent(int type){

    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
}
