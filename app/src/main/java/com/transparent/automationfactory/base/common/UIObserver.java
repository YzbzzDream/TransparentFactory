package com.transparent.automationfactory.base.common;

/**
 * Created by admin on 2016/1/6.
 */
public interface UIObserver {

    void registerObserver();

    void onReceiverNotify(int action, Object object, int stateCode);
}
