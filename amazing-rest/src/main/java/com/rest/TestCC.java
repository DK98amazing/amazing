package com.rest;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;

import javax.annotation.Nullable;
import java.nio.file.ReadOnlyFileSystemException;

public class TestCC {
    public static void main(String args[]) {
//        SettableFuture<String> listenableFuture = SettableFuture.create();
//        CheckedFuture futureb = Futures.makeChecked(listenableFuture, new Function<Exception, Exception>() {
//            @Nullable @Override public Exception apply(@Nullable Exception input) {
//                return new ReadOnlyFileSystemException();
//            }
//        });
//        listenableFuture.setException(new NullPointerException());
//        try {
//            System.out.println(futureb.checkedGet());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            String s = "s";
//            throw new NullPointerException("s");
//        } catch (Exception e) {
//            System.out.println("ex");
//        }

        ListenableFuture<String> future = getString();
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override public void onSuccess(@Nullable String result) {
                System.out.println("success");
            }

            @Override public void onFailure(Throwable t) {
                System.out.println("failed");
            }
        });
    }

    private static ListenableFuture<String> getString() {
        SettableFuture settableFuture = SettableFuture.create();
        try {
            settableFuture.set("qqqq");
            throw new NullPointerException("s");
        } catch (Exception e) {
            System.out.println("ex");
        }
        return settableFuture;
    }
}
