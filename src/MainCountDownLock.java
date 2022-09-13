import java.util.Random;
import java.util.concurrent.*;

//Mục đích là đếm số tiến trình, nếu đủ thì mới chạy. Áp dụng khi dùng hot deal, nếu sản phẩm có 3 người mua thì nó mới chạy

class RunnerDown implements Runnable{
    CountDownLatch l;
    public RunnerDown(CountDownLatch l){
        this.l=l;

    }
    @Override
    public void run() {
        System.out.println("Đang xử lí...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        l.countDown();//Tăng biến đếm lên một
        System.out.println("Đã xử lí xong.");

    }
}

public class MainCountDownLock {

    public static void main(String[] args) {
        System.out.println("Đang xử lí...");
        CountDownLatch latch=new CountDownLatch(3);// cần 3 tiến trình thì mới chạy được
        ExecutorService pool=Executors.newFixedThreadPool(3);
        for (int i=0;i<2;i++){
            pool.submit(new RunnerDown(latch));
        }
        try {
            //latch.await(1,TimeUnit.SECONDS);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Đoạn code "Kết thúc" sẽ được in ra nếu số tiến trình lớn hơn hoặc bằng 3, <3 thì chờ mãi mãi
        System.out.println("Kết thúc");
    }
}
