import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 由于 ExecutorService 只是壹個接口， 接口在 java.util.concurrent 包中有如下实现类：
 * ThreadPoolExecutor
 * ScheduledThreadPoolExecutor
 * Executors是一个工厂类，可以创建不同类型的ExecutorService实例：
 *
 *      ExecutorService executorService1 = Executors.newSingleThreadExecutor();
 *
 *      创建一个固定大小的线程池：
 *      ExecutorService executorService2 = Executors.newFixedThreadPool(10);
 *
 *      ExecutorService executorService3 = Executors.newScheduledThreadPool(10);
 *
 *
 * There are a few different ways to delegate tasks for execution to an ExecutorService:
 *
 * execute(Runnable) : return void
 * submit(Runnable)  : return Future对象，Future 对象可以用于判断 Runnable 是否结束执行
 * submit(Callable)  : Callable 的 call() 方法可以返回壹個结果。方法 Runnable.run() 则不能返回结果,Callable 的返回值可以从方法 submit(Callable) 返回的 Future 对象中获取
 * invokeAny(...)
 * invokeAll(...)
 *
 * 为了关闭在 ExecutorService 中的线程，你需要调用 shutdown() 方法。ExecutorService 并不会马上关闭，而是不再接收新的任务，壹但所有的线程结束执行当前任务，ExecutorServie 才会真的关闭。所有在调用 shutdown() 方法之前提交到 ExecutorService 的任务都会执行。
 * 如果你希望立即关闭 ExecutorService，你可以调用 shutdownNow() 方法。这個方法会尝试马上关闭所有正在执行的任务，并且跳过所有已经提交但是还没有运行的任务。但是对于正在执行的任务，是否能够成功关闭它是无法保证的，有可能他们真的被关闭掉了，也有可能它会壹直执行到任务结束。这是壹個最好的尝试。
 * */
public class ExecutorServiceDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
        executorService.shutdown();
    }

}
