import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alcereo on 19.03.17.
 */
public class Main {

    private static String targetURL = "http://localhost:8991/counter";
//    private static String targetURL = "http://localhost:8080/counter/counter";
//    private static String targetURL = "http://localhost:8080/web-service/sample";


    private static AtomicInteger counter = new AtomicInteger();
    private static AtomicInteger failCounter = new AtomicInteger();
    private static AtomicInteger throughputCounter = new AtomicInteger();

    private static int throughputLimit = 5;


    public static void main(String[] args) throws InterruptedException {

        int nThreads = 8;

        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        System.out.println("Start");

        HttpURLConnection connection = null;


        for (int i = 0; i < nThreads; i++) {
//            new Thread(() -> SendRequest(200, targetURL)).start();
            pool.submit(() -> SendRequest(200, targetURL));
        }

        pool.shutdown();

//        try {
//            pool.awaitTermination(120, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        pool.shutdownNow();

        int currentCount = 0;
        int throughput = 0;

        while (!Thread.currentThread().isInterrupted()){

            Thread.sleep(1000);
            throughput = counter.get() - currentCount;
            currentCount = counter.get();

            System.out.printf("\r");
            System.out.printf("Count: "+counter+". Fails:"+failCounter.get()+". Threshold: "+throughput);

            throughputCounter.set(0);

        }

        System.out.println("Finish");

    }

    private static boolean SendRequest(int status, String StringUrl) {

        System.out.println("SEND");
        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL(StringUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        while (!Thread.currentThread().isInterrupted()){
            try {
                //Create connection


                    if (throughputCounter.incrementAndGet() <= throughputLimit) {
                        try{
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");

                            try {
                                int responseCode = connection.getResponseCode();
                                if (responseCode != status) {
                                    System.out.println("REQUEST FAULT!");
                                } else {
                                    counter.incrementAndGet();
                                }
                            } catch (NoRouteToHostException exception) {
//                    System.out.println("NRTHE: "+exception.getLocalizedMessage());
                                failCounter.incrementAndGet();
                            }

                        }finally {
                            if (connection!=null)
                                connection.disconnect();
                        }

                    }


            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("OUT");

        return false;
    }
}
