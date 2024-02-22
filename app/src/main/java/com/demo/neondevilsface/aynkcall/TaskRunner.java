package com.demo.neondevilsface.aynkcall;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TaskRunner {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();

    public <R> void executeAsync(CustomCallable<R> customCallable) {
        try {
            customCallable.setUiForLoading();
            this.executor.execute(new RunnableTask(this.handler, customCallable));
        } catch (Exception unused) {
        }
    }


    public static class RunnableTask<R> implements Runnable {
        private final CustomCallable<R> callable;
        private final Handler handler;

        public RunnableTask(Handler handler, CustomCallable<R> customCallable) {
            this.handler = handler;
            this.callable = customCallable;
        }

        @Override 
        public void run() {
            try {
                this.handler.post(new RunnableTaskForHandler(this.callable, this.callable.call()));
            } catch (Exception unused) {
            }
        }
    }


    public static class RunnableTaskForHandler<R> implements Runnable {
        private CustomCallable<R> callable;
        private R result;

        public RunnableTaskForHandler(CustomCallable<R> customCallable, R r) {
            this.callable = customCallable;
            this.result = r;
        }

        @Override 
        public void run() {
            Log.e("Image_list", "run: ");
            this.callable.setDataAfterLoading(this.result);
        }
    }
}
