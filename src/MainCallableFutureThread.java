import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

import static sun.misc.Version.println;

//Dùng để nhận về giá trị từ thread khi nó chạy xong

public class MainCallableFutureThread {

    public static void main(String[] args) {
        System.out.println("Đang xử lí...");
        ExecutorService pool= Executors.newCachedThreadPool();
        //Nhận giá trị trả về
        Future<Integer> getTong=pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int tong =0;
                Random rd1=new Random();
                Random rd2=new Random();
                tong=rd1.nextInt(5)+rd2.nextInt(5);
                System.out.println("Threading: "+Thread.currentThread().getName());
                return tong;
            }
        });
        pool.shutdown();//Chạy xong rồi dùng lại tiến trình này đi
        try {
            System.out.println("Tổng là: "+getTong.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Kết thúc");
    }
}
