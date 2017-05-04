package com.allen.singleactivity.ui.fragment;

import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.allen.singleactivity.R;
import com.allen.singleactivity.databinding.FragmentRootBinding;

/**
 * Created by Allen on 2016/12/5.
 */

public class HomeFragment extends BaseFragment<FragmentRootBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_root;
    }

    @Override
    protected void initView() {
        MainFragment mainFragment = (MainFragment) getFragmentManager().findFragmentByTag(MainFragment.class.getName());
        if(mainFragment == null){
            showFragment(R.id.main_container, new MainFragment());
        }
        MenuFragment menuFragment = (MenuFragment) getFragmentManager().findFragmentByTag(MenuFragment.class.getName());
        if(menuFragment == null){
            showFragment(R.id.menu_container, new MenuFragment());
        }
    }

    @Override
    protected void initData() {
    }

    private void showFragment(int containerId, Fragment fragment){
        if(getActivity().isFinishing()){
            return;
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment inManager = null;
        if((inManager = fm.findFragmentByTag(fragment.getClass().getName())) != null){
            fm.beginTransaction().show(inManager).commitAllowingStateLoss();
        }else {
            fm.beginTransaction().add(containerId, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        }
    }
}
