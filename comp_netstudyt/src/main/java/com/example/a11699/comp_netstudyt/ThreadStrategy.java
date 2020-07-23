package com.example.a11699.comp_netstudyt;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Create time 2020/7/21
 * Create Yu
 * description:
 */
public enum ThreadStrategy {
    IO(Schedulers.io()),
    SINGLE(Schedulers.single()),
    COMPUTATION(Schedulers.computation()),
    TRAMPOLINE(Schedulers.trampoline()),
    NEW_THREAD(Schedulers.newThread()),
    MAIN(AndroidSchedulers.mainThread());

    private Scheduler mScheduler;

    ThreadStrategy(Scheduler scheduler) {
        this.mScheduler = scheduler;
    }

    public Scheduler cheduler() {
        return mScheduler;
    }
}
