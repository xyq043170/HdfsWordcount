import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test2 {
    private static CyclicBarrier cycb = new CyclicBarrier(5);
    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    private static int THREAD_COUNT = 4;
    public static void main(String[] args) throws Exception{
        for (int i = 0; i < 5; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName()+"start ...");
                        Thread.sleep(new Random().nextInt(5000));
                        System.out.println(Thread.currentThread().getName()+"end ...");
                        cycb.await();
                        System.out.println(Thread.currentThread().getName()+"123 ...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        System.out.println("main task");
        pool.shutdown();
    }
}
