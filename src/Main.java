import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class Main {
    static SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) throws Exception {

//        System.out.println("Hello World!");
////        Thread.currentThread().isInterrupted()
//        ScheduledThreadPoolExecutor sch = new ScheduledThreadPoolExecutor(10);
//        sch.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(sdf.format(new Date()));
//            }
//        },0,100, TimeUnit.MILLISECONDS);

        Thread t1 = new Thread(new Runnabless("abc"));
        Thread t2 = new Thread(new Runnabless("abc"));
        Thread t3 = new Thread(new Runnabless("abc"));

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();

        final String str="abc";
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("1"+str);
            }
        });executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("2"+str);
            }
        });executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("3"+str);
            }
        });

    }
}

class Runnabless implements Runnable{

    String str ="";
    public Runnabless(String str)
    {
        this.str = str;
    }
    @Override
    public void run() {
        System.out.println(str);
    }
}
