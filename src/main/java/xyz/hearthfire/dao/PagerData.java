package xyz.hearthfire.dao;

import java.util.List;

/**
 * Created by fz on 2015/11/7.
 */
public class PagerData<T> {

    private List<T> data;

    private int totalCount;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

