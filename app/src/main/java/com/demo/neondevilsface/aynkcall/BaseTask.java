package com.demo.neondevilsface.aynkcall;


public abstract class BaseTask<R> implements CustomCallable<R> {
    @Override 
    public R call() throws Exception {
        return null;
    }

    @Override 
    public void setDataAfterLoading(R r) {
    }

    @Override 
    public void setUiForLoading() {
    }
}
