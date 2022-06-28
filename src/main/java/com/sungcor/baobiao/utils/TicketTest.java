package com.sungcor.baobiao.utils;

import java.util.ArrayList;
import java.util.List;

class TicketThread implements Runnable{
    private int total=100;

    public void run() {
        while (true){
            synchronized (this){
                if(total>0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"卖了第"+total--+"张票");
                }
            }
        }
    }
}

public class TicketTest {
    public static void main(String[] args) {
        TicketThread ticket = new TicketThread();
        List<Thread> threadList = new ArrayList<>();
        for (int i=1;i<=3;i++){
            Thread thread = new Thread(ticket,"窗口"+i);
            threadList.add(thread);
        }
        for(Thread thread:threadList){
            thread.start();
        }
    }
}

