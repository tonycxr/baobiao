package com.sungcor.baobiao.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class Test00 {
    public static void main(String[] args) throws Exception{
        ForkJoinPool pool=new ForkJoinPool();
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread()+" start job1,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job1,time->"+System.currentTimeMillis());
            return 1.2;
        },pool);
        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        CompletableFuture cf2=cf.thenApply((result)->{
            System.out.println(Thread.currentThread()+" start job2,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread()+" exit job2,time->"+System.currentTimeMillis());
            return "test:"+result;
        }).thenAccept((result)-> { //接收上一个任务的执行结果作为入参，但是没有返回值
            System.out.println(Thread.currentThread()+" start job3,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println(result);
            System.out.println(Thread.currentThread()+" exit job3,time->"+System.currentTimeMillis());
        }).thenRun(()->{ //无入参，也没有返回值
            System.out.println(Thread.currentThread()+" start job4,time->"+System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            System.out.println("thenRun do something");
            System.out.println(Thread.currentThread()+" exit job4,time->"+System.currentTimeMillis());
        });
        System.out.println("main thread start cf.get(),time->"+System.currentTimeMillis());
        //等待子任务执行完成
        System.out.println("run result->"+cf.get());
        System.out.println("main thread start cf2.get(),time->"+System.currentTimeMillis());
        //cf2 等待最后一个thenRun执行完成
        System.out.println("run result->"+cf2.get());
        System.out.println("main thread exit,time->"+System.currentTimeMillis());
    }
}
