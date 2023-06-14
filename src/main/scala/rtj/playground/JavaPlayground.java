package rtj.playground;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaPlayground {

    //private static ConcurrentMap<String, Integer> unsafeInventory = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, AtomicInteger> safeInventory = new ConcurrentHashMap<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args){
        System.out.println("Welcome to rock the jvm code!");

        //unsafeInventory.put("idli", 1000000);
        safeInventory.put("idli", new AtomicInteger(1000));

        Runnable safeTask = ()->{
            AtomicInteger inventory = safeInventory.get("idli");
            System.out.println("initial" +Thread.currentThread().getName() + "  "+inventory.get());

            for(int i = 1; i<=10; i++){
                boolean itrSu = false;
                while (!itrSu){
                    int initialInventory = inventory.get();
                    itrSu = inventory.compareAndSet(initialInventory, initialInventory-10);
                }
            }

            System.out.println("final" +Thread.currentThread().getName() + "  "+inventory.get());
        };
        System.out.println("initial inventory "+safeInventory.get("idli").get());
        List<Future<?>> futures = new ArrayList<>();
        for(int i = 1; i<=10; i++){
            Future<?> future = threadPool.submit(safeTask);
            futures.add(future);
        }
        //boolean isFinished = false;
        while(true){
            boolean incompleteFound = futures.stream().map(f -> f.isDone()).anyMatch(status -> !status);
            if(!incompleteFound) break;
        }
        System.out.println("final inventory "+safeInventory.get("idli").get());

        threadPool.shutdownNow();


    }

}
