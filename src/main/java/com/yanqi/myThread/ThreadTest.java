package com.yanqi.myThread;

/**
 * @author YQQ
 * @program: People's Daily
 * @description:
 * @date 2021-09-29 17:12:01
 */
public class ThreadTest {


    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        Thread thread = threadTest.getThread(new MyRun());

        thread.start();
        System.out.println("主线程完成了");
    }

    private Thread getThread(Runnable r){
        return new Thread(r);
    }

    private static class MyRun implements Runnable{
        @Override
        public void run() {
            System.out.println("支线程");
        }
    }
}
