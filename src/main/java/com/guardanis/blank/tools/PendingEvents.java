package com.guardanis.blank.tools;

import java.util.HashMap;

public class PendingEvents {

    private static PendingEvents instance;
    public static PendingEvents getInstance() {
        if(instance == null)
            instance = new PendingEvents();

        return instance;
    }

    private HashMap<String, Runnable> pendingEvents = new HashMap<String, Runnable>();

    protected PendingEvents() { }

    public void register(String key, Runnable event) {
        pendingEvents.put(key, event);
    }

    public void unregister(String key) {
        pendingEvents.put(key, null);
    }

    public void trigger(String key) {
        Runnable event = pendingEvents.get(key);

        if(event != null){
            event.run();
            unregister(key);
        }
    }

    public boolean has(String key) {
        return pendingEvents.get(key) != null;
    }

}