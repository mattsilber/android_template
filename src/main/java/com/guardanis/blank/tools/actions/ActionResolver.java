package com.guardanis.blank.tools.actions;

import java.util.ArrayList;
import java.util.List;

public class ActionResolver {

    public interface Resolvable {
        public boolean isResolutionRequired();
        public void resolve(Runnable successListener);
    }

    private List<Resolvable> resolvables = new ArrayList<Resolvable>();

    public ActionResolver add(Resolvable resolvable) {
        resolvables.add(resolvable);
        return this;
    }

    public void resolve(final Runnable successListener) {
        for(Resolvable resolvable : resolvables){
            if(resolvable.isResolutionRequired()){
                resolvable.resolve(() ->
                        resolve(successListener));

                return;
            }
        }

        successListener.run();
    }
}
