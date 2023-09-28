package chapter_22;
import java.util.LinkedList;

public class LinkedListCorruption{
    public static void main(String[] args) throws Exception {
        LinkedList<Integer> list = new LinkedList<>();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    synchronized (list){
                        list.add(i);
                        System.out.println("Size after added "+ list.size());
                    }
                    try{Thread.sleep(10);} catch(Exception e){}
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    synchronized (list){
                        if(!list.isEmpty()) list.remove();
                        System.out.println("Size after removed "+ list.size());
                    }
                    try{Thread.sleep(10);} catch(Exception e){}
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
