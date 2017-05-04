package com.allen.singleactivity.rxbus.entity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Allen on 2016/12/2.
 */

public class SubscriptionBinder {
    private CompositeSubscription compositeSubscription;

    public SubscriptionBinder() {
        compositeSubscription = new CompositeSubscription();
    }

    public void add(Subscription subscription){
        if(compositeSubscription == null){
            throw new IllegalArgumentException("add subsciption failed, compositeSubscription already unbind");
        }

        if(subscription == null){
            throw new NullPointerException("subscription is null");
        }

        compositeSubscription.add(subscription);
    }

    public void unbind(){
        if(compositeSubscription != null && !compositeSubscription.isUnsubscribed()){
            compositeSubscription.unsubscribe();
        }
    }

    public boolean isunbind(){
        return compositeSubscription != null;
    }
}
