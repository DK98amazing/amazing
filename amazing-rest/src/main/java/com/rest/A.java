package com.rest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.LongAdder;

/**
 * A.
 *
 * @author liguoyao
 */
public class A {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    public A() {
    }

    public A(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public A(String a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return String.valueOf("--" + a + "--" + b);
    }

    public static void main(String[] args)
        throws ExecutionException, InterruptedException, ClassNotFoundException, IllegalAccessException,
        InstantiationException, InvocationTargetException {
        //        CountDownLatch countDownLatch = new CountDownLatch(3);
        //        SettableFuture<Integer> settableFuture = SettableFuture.create();
        //        SettableFuture<Integer> settableFuture1 = SettableFuture.create();
        //        List<SettableFuture<Integer>> settableFutures = Arrays.asList(settableFuture, settableFuture1);
        //        for (SettableFuture<Integer> settableFuture2 : settableFutures) {
        //            Futures.addCallback(settableFuture2, new FutureCallback<Integer>() {
        //                @Override
        //                public void onSuccess(@Nullable Integer result) {
        //                    countDownLatch.countDown();
        //                    System.err.println("current :" + countDownLatch.getCount());
        //                    if (countDownLatch.getCount() == 0) {
        //                        System.out.println(2222);
        //                    }
        //                }
        //
        //                @Override
        //                public void onFailure(Throwable t) {
        //
        //                }
        //            });
        //        }
        //        new Thread(() -> {
        //            try {
        //                TimeUnit.SECONDS.sleep(2);
        //                settableFuture.set(1);
        //                TimeUnit.SECONDS.sleep(2);
        //                settableFuture1.set(3);
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //        }).start();
        //
        //        LongAdder a = new LongAdder();
        //        deal(a);
        //        System.out.println(a);

        //        SettableFuture<Integer> settableFuture = SettableFuture.create();
        //        settableFuture.setException(new NullPointerException());
        //        ListenableFuture<Integer> listenableFuture =
        //            Futures.catching(settableFuture, NullPointerException.class, new Function<NullPointerException, Integer>() {
        //                @Nullable
        //                @Override
        //                public Integer apply(@Nullable NullPointerException input) {
        //                    return null;
        //                }
        //            }, MoreExecutors.directExecutor());
        //        System.out.println(listenableFuture.get());
        //        ListenableFuture<Integer> future =
        //            Futures.withTimeout(settableFuture, 3, TimeUnit.SECONDS, Executors.newScheduledThreadPool(1));
        //        Futures.addCallback(future, new FutureCallback<Integer>() {
        //            @Override
        //            public void onSuccess(@Nullable Integer result) {
        //
        //            }
        //
        //            @Override
        //            public void onFailure(Throwable t) {
        //                System.out.println(t.getMessage());
        //            }
        //        });
        List<Class<?>> classes = new ArrayList<Class<?>>();
        // 循环1000w次生成1000w个不同的类。
        //        for (int i = 0; i < 10000000; ++i) {
        //            ClassWriter cw = new ClassWriter(0);
        //            // 定义一个类名称为Class{i}，它的访问域为public，父类为java.lang.Object，不实现任何接口
        //            cw.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "Class" + i, null, "java/lang/Object", null);
        //            // 定义构造函数<init>方法
        //            MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        //            // 第一个指令为加载this
        //            mw.visitVarInsn(Opcodes.ALOAD, 0);
        //            // 第二个指令为调用父类Object的构造函数
        //            mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        //            // 第三条指令为return
        //            mw.visitInsn(Opcodes.RETURN);
        //            mw.visitMaxs(1, 1);
        //            mw.visitEnd();
        //
        //            A test = new A(0);
        //            byte[] code = cw.toByteArray();
        //            // 定义类
        //            Class<?> exampleClass = test.defineClass("Class" + i, code, 0, code.length);
        //            classes.add(exampleClass);
        //        }

        Class clazz = Class.forName("com.rest.A");
        for (Constructor constructor : clazz.getConstructors()) {
            for (Parameter parameter : constructor.getParameters()) {
                System.out.println(parameter.getName());
            }
        }

        String fieldname = "_qazwsx".substring(1, "_qazwsx".length());
        String fileName = fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1, fieldname.length());
        System.err.println(fileName);

    }

    public static void deal(LongAdder a) {
        a.increment();
    }
}
