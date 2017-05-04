package com.allen.fragmentstack;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * Created by Allen on 2016/12/6.
 */

public abstract class RootActivity extends AppCompatActivity {
    private FragmentStack fragmentStack;
    boolean fragmentAnimRunnging;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources.Theme theme = getTheme();
        if(getFragmentTheme() != 0){
            theme.applyStyle(getFragmentTheme(), true);
        }

        TypedArray a = theme.obtainStyledAttributes(R.styleable.FragmentTheme);
        int enterAnim = a.getResourceId(R.styleable.FragmentTheme_enter_fragment_anim, 0);
        int exitAnim = a.getResourceId(R.styleable.FragmentTheme_exit_fragment_anim, 0);
        int popEnterAnim = a.getResourceId(R.styleable.FragmentTheme_pop_enter_fragment_anim, 0);
        int popExitAnim = a.getResourceId(R.styleable.FragmentTheme_pop_exit_fragment_anim, 0);
        a.recycle();

        FragmentStack.Builder builder = new FragmentStack.Builder(getSupportFragmentManager());
        if(enterAnim != 0){
            builder.newEnterAnim(enterAnim);
        }
        if(exitAnim != 0){
            builder.newExitAnim(exitAnim);
        }
        if(popEnterAnim != 0){
            builder.oldEnterAnim(popEnterAnim);
        }
        if(popExitAnim != 0){
            builder.oldExitAnim(popExitAnim);
        }
        fragmentStack = new FragmentStack(builder);
    }

    protected abstract int getContainerId();

    protected int getFragmentTheme(){
        return R.style.BaseFragmentTheme;
    }

    public void startFragment(Class<? extends RootFragment> cls){
        startFragment(new FragmentIntent(cls));
    }

    public void startFragment(FragmentIntent intent){
        startFragmentForResult(intent, 0);
    }

    public void startFragmentForResult(FragmentIntent intent, int requestCode){
        fragmentAnimRunnging = true;
        fragmentStack.push(getContainerId(), intent, requestCode);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(fragmentAnimRunnging){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentStack.pop();
        if(fragment == null){
            super.onBackPressed();
        }
    }

    public void finishFragment(RootFragment fragment){
        Fragment f = fragmentStack.pop(fragment);
        if(f == null){
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentStack.onSaveInstance(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentStack.onRestoreInstance(savedInstanceState);
    }
}
