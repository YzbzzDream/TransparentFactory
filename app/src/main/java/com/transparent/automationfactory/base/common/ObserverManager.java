package com.transparent.automationfactory.base.common;

import android.os.Handler;
import android.os.Looper;


import com.transparent.automationfactory.base.util.MultiHashMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/1/6.
 */
public class ObserverManager {

    private MultiHashMap<Integer, WeakReference<UIObserver>> observers = new MultiHashMap<>();

    private Handler handler = new Handler(Looper.getMainLooper());

    public static ObserverManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static ObserverManager instance = new ObserverManager();
    }

    public void registerObserver(int action, UIObserver observer) {
        synchronized (observers) {
            observers.put(action, new WeakReference<>(observer));
        }
    }

    public void unRegisterObserver(UIObserver observer) {
        synchronized (observers) {
            Set<Map.Entry<Integer, ArrayList<WeakReference<UIObserver>>>> entrySet = observers.entrySet();
            for (Map.Entry<Integer, ArrayList<WeakReference<UIObserver>>> entry : entrySet) {
                ArrayList<WeakReference<UIObserver>> list = entry.getValue();
                if (list == null || list.size() == 0) {
                    continue;
                }
                Iterator<WeakReference<UIObserver>> iterator = list.iterator();
                while (iterator.hasNext()) {
                    WeakReference<UIObserver> uiObserver = iterator.next();
                    if (observer == uiObserver.get()) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void unRegisterObserver(int action, UIObserver observer) {
        synchronized (observers) {
            ArrayList<WeakReference<UIObserver>> list = observers.get(action);
            if (list == null || list.size() == 0) {
                return;
            }
            Iterator<WeakReference<UIObserver>> iterator = list.iterator();
            while (iterator.hasNext()) {
                WeakReference<UIObserver> uiObserver = iterator.next();
                if (observer == uiObserver.get()) {
                    iterator.remove();
                }
            }
        }
    }

    public void notifyUi(final int action, final Object obj, final int stateCode) {
        ArrayList<WeakReference<UIObserver>> listeners = observers.get(action);
        if (null == listeners || listeners.size() == 0) {
            return;
        }
        listeners.removeAll(Collections.singleton(null));
        for (WeakReference<UIObserver> weakListener : listeners) {
            final UIObserver listener = weakListener.get();
            if (listener != null) {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            listener.onReceiverNotify(action, obj, stateCode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
