import java.sql.Time;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.*;

public class AtomicIntegerTest implements Runnable {
    private AtomicInteger i = new AtomicInteger(0);
    public int getValue() {
        return i.get();
    }
    private void evenIncrement() {i.addAndGet(2);}
    public void run() {
        while (true) {
            evenIncrement();
        }
    }
    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 5000);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicIntegerTest ait = new AtomicIntegerTest();
        executorService.execute(ait);
        while(true) {
            int val = ait.getValue();
            if(val%2!=0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
