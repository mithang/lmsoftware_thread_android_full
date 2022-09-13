
import java.util.Random;
import java.util.concurrent.*;

//Mục đích dùng BlockingQueue để lưu giá trị từ thread này, nhưng có thể xuất ra từ thread khac


public class MainProducerCustomer {

    static int value;
    static BlockingQueue<Integer> queue=new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        System.out.println("Đang xử lí...");
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                Producer();
            }
        });
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                Customer();
            }
        });

        thread1.start();
        thread2.start();


        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Kết thúc");
    }

    private static void Customer() {
        Random rd=new Random();
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(rd.nextInt(10)==0){
                try {
                    value=queue.take();
                    System.out.println("Giá trị: "+value+" . Số block trong hàng đợi: "+queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void Producer() {
        Random rd=new Random();
        while (true){
            try {
                queue.put(rd.nextInt(10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
