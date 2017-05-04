package com.allen.singleactivity.rxbus;

import android.util.SparseArray;

import com.allen.singleactivity.rxbus.annotations.Subscribe;
import com.allen.singleactivity.rxbus.entity.SubscribeEvent;
import com.allen.singleactivity.rxbus.entity.SubscriptionBinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by Allen on 2016/12/2.
 */

public class RxBus {
    private static RxBus instance;

    private SerializedSubject<SubscribeEvent, SubscribeEvent> mPublishSubject;
    private Map<String, SubscribeEvent> mStickyEventMap;
    private SparseArray<SubscriptionBinder> mBinderMap;
    private Thread mThread;
    private SubscribeBinder mSubscribeBinder;

    private RxBus(){
        mPublishSubject = new SerializedSubject<>(PublishSubject.<SubscribeEvent>create());
        mStickyEventMap = new HashMap<>();
        mBinderMap = new SparseArray<>();
    }

    public static RxBus getDefault(){
        if(instance == null){
            synchronized (RxBus.class){
                if(instance == null){
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void init(SubscribeBinder binder, Scheduler main){
        if(binder == null){
            throw new NullPointerException("binder must not be null");
        }

        if(main == null){
            throw new NullPointerException("main must not be null");
        }
        if(mSubscribeBinder == null){
            mSubscribeBinder = binder;
        }

        if(mThread == null){
            mThread = new Thread(main);
        }
    }

    public SubscriptionBinder bind(Object obj){
        if(obj == null){
            throw new NullPointerException("object to be bind must not be null");
        }
        SubscriptionBinder binder = null;
        int uniqueId = System.identityHashCode(obj);
        if((binder = mBinderMap.get(uniqueId)) != null){
            if(binder.isunbind()){
                mBinderMap.remove(uniqueId);
            }
        }

        binder = mSubscribeBinder.bind(obj);
        mBinderMap.put(uniqueId, binder);
        return binder;
    }

    public void post(SubscribeEvent event){
        mPublishSubject.onNext(event);
    }

    public void post(String tag, Object data){
        post(new SubscribeEvent(tag, data));
    }

    public void post(String tag){
        post(new SubscribeEvent(tag, new Object()));
    }

    public interface SubscribeBinder{
        SubscriptionBinder bind(Object object);
    }

    public class Thread{
        private Scheduler mMain;

        private Scheduler mIo = Schedulers.io();
        private Scheduler mNew = Schedulers.newThread();

        public Thread(Scheduler scheduler) {
            mMain = scheduler;
        }

        public Scheduler get(Subscribe.Thread thread){
            switch (thread){
                case MAIN:
                    return mMain;
                case IO:
                    return mIo;
                case NEW:
                    return mNew;
                default:
                    return mMain;
            }
        }
    }
}
