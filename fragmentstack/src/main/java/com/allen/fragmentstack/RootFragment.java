package com.allen.fragmentstack;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Allen on 2016/12/5.
 */

public class RootFragment extends Fragment {
    static final String REQUEST_CODE = "inner_request_code";
    static final String RESULT_CODE = "inner_result_code";
    static final String INTENT = "inner_intent";

    protected RootActivity activity;
    int requestCode;
    int resultCode;
    FragmentIntent intent;
    FragmentIntent resultIntent;

    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case MSG_ENTER:
                    if(activity == null || getView() == null){
                        return false;
                    }else {
                        onEnter();
                        return true;
                    }
                case MSG_EXIT:
                    onExit();
                    return true;
                case MSG_POP_ENTER:
                    onPopEnter();
                    return true;
                case MSG_POP_EXIT:
                    onPopExit();
                    return true;
            }
            return false;
        }
    });
    private static final int MSG_ENTER = 1;
    private static final int MSG_EXIT = 2;
    private static final int MSG_POP_ENTER = 3;
    private static final int MSG_POP_EXIT = 4;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof RootActivity)){
            throw new IllegalArgumentException("the activity RootFragment attached must be RootActivity");
        }
        activity = (RootActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public void onNewIntent(FragmentIntent intent) {
        this.intent = intent;
    }

    public final void enter(){
        handler.sendEmptyMessage(MSG_ENTER);
    }

    public final void exit(){
        handler.sendEmptyMessage(MSG_EXIT);
    }

    public final void popEnter(){
        handler.sendEmptyMessage(MSG_POP_ENTER);
    }

    public final void popExit(){
        handler.sendEmptyMessage(MSG_POP_EXIT);
    }

    protected void onEnter(){

    }

    protected void onExit(){

    }

    protected void onPopEnter(){

    }

    protected void onPopExit(){

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REQUEST_CODE, requestCode);
        outState.putInt(RESULT_CODE, resultCode);
        outState.putParcelable(INTENT, intent);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            requestCode = savedInstanceState.getInt(REQUEST_CODE);
            resultCode = savedInstanceState.getInt(RESULT_CODE);
            intent = savedInstanceState.getParcelable(INTENT);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(nextAnim != 0){
            Animation animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            if(animation != null){
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        activity.fragmentAnimRunnging = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
            return animation;
        }
        return null;
    }

    public FragmentIntent getIntent(){
        return intent;
    }

    public void setResult(int resultCode, FragmentIntent intent){
        synchronized (this){
            this.resultCode = resultCode;
            this.resultIntent = intent;
        }
    }

    public void finish(){
        activity.finishFragment(this);
    }

    public void startFragment(Class<? extends RootFragment> cls){
        activity.startFragment(cls);
    }

    public void startFragment(FragmentIntent intent){
        activity.startFragment(intent);
    }

    public void startFragmentForResult(FragmentIntent intent, int requestCode){
        activity.startFragmentForResult(intent, requestCode);
    }

    public void onFragmentResult(int requestCode, int resultCode, FragmentIntent intent){

    }
}
