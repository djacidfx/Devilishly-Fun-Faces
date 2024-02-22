package com.demo.neondevilsface.aynkcall;

import java.util.concurrent.Callable;


public interface CustomCallable<R> extends Callable<R> {
    void setDataAfterLoading(R r);

    void setUiForLoading();
}
