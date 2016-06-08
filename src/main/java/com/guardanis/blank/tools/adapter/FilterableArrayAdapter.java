package com.guardanis.blank.tools.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.guardanis.collections.tools.ListUtils;

import java.util.List;

public abstract class FilterableArrayAdapter<T> extends ArrayAdapter<T> {

    protected List<T> activeData;
    protected List<T> fullData;

    protected String lastFilter = "";

    public FilterableArrayAdapter(Context context, int resource, @NonNull List<T> data) {
        super(context, resource, data);
        this.activeData = data;

        for(T t : data)
            fullData.add(t);
    }

    public void filter(@NonNull String value){
        lastFilter = value;

        this.activeData.clear();

        for(T t : new ListUtils<T>(fullData)
                .filter(data -> isFilterMatched(value, data))
                .values())
            activeData.add(t);

        notifyDataSetChanged();
    }

    protected abstract boolean isFilterMatched(@NonNull String value, T t);

    public void setFilterableDataSet(@NonNull List<T> values){
        this.fullData = values;

        filter(lastFilter);
    }

    public List<T> getFullData(){
        return fullData;
    }

    public List<T> getActiveData(){
        return activeData;
    }

    public String getSearchFilter(){
        return lastFilter;
    }

}
