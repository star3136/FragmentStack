package com.allen.singleactivity.ui.fragment;

import com.allen.singleactivity.MainActivity;
import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentMainBinding;
import com.allen.singleactivity.entity.TestEvent;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by Allen on 2016/12/5.
 */

public class MainFragment extends BaseFragment<FragmentMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        binding.textView.setText("在MainFragment");
    }

    @Override
    protected void initData() {
        RxView.clicks(binding.gotoFragment1).debounce(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startFragment(OneFragment.class);
            }
        });
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void onEvent(TestEvent event){
        binding.textView.setText(event.str);
    }
}
