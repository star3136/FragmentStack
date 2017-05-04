package com.allen.singleactivity.ui.fragment;

import android.databinding.ViewDataBinding;

import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentMenuBinding;

/**
 * Created by Allen on 2016/12/5.
 */

public class MenuFragment extends BaseFragment<FragmentMenuBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initView() {
        binding.textView.setText("在MenuFragment");
    }

    @Override
    protected void initData() {

    }
}
