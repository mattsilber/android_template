package com.guardanis.blank.tools.observables;


import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class ObservableHelper {

    public static void safelyUnsubscribe(Subscription subscription){
        try{
            subscription.unsubscribe();
        }
        catch(Throwable e){ e.printStackTrace(); }
    }

    public static Subscription safelySubscribe(Observable observable, Action1 action){
        try{
            return observable.subscribe(action);
        }
        catch(Throwable e){ e.printStackTrace(); }

        return null;
    }

}
