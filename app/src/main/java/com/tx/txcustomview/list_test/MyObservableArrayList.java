package com.tx.txcustomview.list_test;

import java.util.Collection;

import androidx.databinding.ObservableArrayList;

/**
 * create by xu.tian
 *
 * @date 2021/9/22
 */
public class MyObservableArrayList<T> extends ObservableArrayList<T> {

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
    }

  /*  @Override
    protected void removeAll(Collection<>)*/
}
