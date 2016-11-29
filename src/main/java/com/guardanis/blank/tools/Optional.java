package com.guardanis.blank.tools;

import android.support.annotation.Nullable;

public class Optional<T> {

    private static final Optional<Object> ABSENT = new Optional<Object>();

    private T value;

    protected Optional(T value){
        this.value = value;
    }

    protected Optional(){ }

    public static <T> Optional<T> from(@Nullable T value){
        return value == null
                ? (Optional<T>) ABSENT
                : new Optional(value);
    }

    public boolean isPresent(){
        return value != null;
    }

    public T get(){
        return value;
    }

    public T get(T defaultValue){
        return value == null
                ? defaultValue
                : value;
    }

    public Optional<T> bind(Callback<T> onValuePresent){
        bind(onValuePresent, null);

        return this;
    }

    public Optional<T> bind(Callback<T> onValuePresent, @Nullable Runnable onNull){
        if(value != null)
            onValuePresent.onCalled(value);
        else if(onNull != null)
            onNull.run();

        return this;
    }

}