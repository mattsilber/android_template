package com.guardanis.blank.tools.observables;

import android.os.Handler;
import android.os.Looper;

import com.guardanis.blank.tools.Callback;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DebouncedEventController<T> {

    private Subscription subscription;
    private Callback<T> updateSubscriptionTrigger;
    private Observable notifyObservable;

    private Action1<T> eventCallback;

    public DebouncedEventController(final Callback<T> eventCallback, long delayMs){
        this(eventCallback, null, delayMs);
    }

    public DebouncedEventController(final Callback<T> eventCallback, final T initialEvent, long delayMs){
        final Handler handler = new Handler(Looper.getMainLooper());

        this.eventCallback = v ->
                handler.post(() ->
                        eventCallback.onCalled(v));

        this.notifyObservable = Observable.create(subscriber -> {
                    subscriber.onNext(initialEvent);

                    updateSubscriptionTrigger = (v) ->
                        subscriber.onNext(v);
                })
                .subscribeOn(Schedulers.newThread())
                .debounce(delayMs, TimeUnit.MILLISECONDS);
    }

    public void subscribe(){
        this.subscription = ObservableHelper.safelySubscribe(notifyObservable, eventCallback);
    }

    public void unsubscribe(){
        ObservableHelper.safelyUnsubscribe(subscription);
        this.subscription = null;
    }

    public void trigger(T value){
        if(subscription == null)
            subscribe();

        if(updateSubscriptionTrigger != null)
            updateSubscriptionTrigger.onCalled(value);
    }

}
