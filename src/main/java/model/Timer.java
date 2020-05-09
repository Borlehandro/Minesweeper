package model;

import java.util.Date;

public class Timer extends Thread {

    private boolean run = true;

    long start;

    public Timer() {
        start = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (run)
            System.out.print("\r" + new Date(System.currentTimeMillis() - start));
    }

    public void terminate() {
        run = false;
    }
}
