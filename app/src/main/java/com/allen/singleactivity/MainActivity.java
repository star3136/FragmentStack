package com.allen.singleactivity;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.allen.fragmentstack.FragmentIntent;
import com.allen.fragmentstack.FragmentRecord;
import com.allen.fragmentstack.FragmentStack;
import com.allen.singleactivity.ui.fragment.HomeFragment;
import com.allen.singleactivity.ui.fragment.MainFragment;

public class MainActivity extends com.allen.fragmentstack.RootActivity {

    @Override
    protected int getContainerId() {
        return 1 << 24;
    }

    @Override
    protected int getFragmentTheme() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(1 << 24);
        setContentView(frameLayout);
        startFragment(HomeFragment.class);
    }
}
