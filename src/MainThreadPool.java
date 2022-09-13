import java.util.Random;
import java.util.concurrent.*;
//Semaphore tương tự nhưng khác ở chỗ là cần có thông báo để chạy tiếp, còn thread pool thì không cần
//Mục đích dùng để upload hay download hình ảnh

class RunPool implements Runnable{
    int id;
    @Override
    public void run() {
        System.out.println("Đang xử lí tiến trình: "+id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Đã xử lí tiến trình: "+id);
    }
    public RunPool(int id){
        this.id=id;
    }
}

public class MainThreadPool {
    public static void main(String[] args) {

        System.out.println("Đang xử lí...");
        int numberDefault=5;// Só tiến trình cần xử lí mặt định
        int numberProcess=5;// Số tiền trình tối đa để chạy một lần xử lí. Yêu cầu phải lớn hơn hoặc bằng numberDefault
        int timer=1;// Thời gian sống của một tiến trình mà xử lí một lúc 5 thread
        //C1" Dùng ThreadPoolExecutor
//        ArrayBlockingQueue<Runnable> queues=new ArrayBlockingQueue<Runnable>(100);
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(numberDefault,numberProcess,timer,TimeUnit.SECONDS,queues);
//        //Add 20 tiến trình vào hàng đợi. Hệ thống sẽ tự lấy 5 tiến trình ra để xử lí, sau đó tiếp tục lấy 5 tiến trình khác xử lí tiếp cho đến hết 20 tiến trình trong hàng đợi
//        for (int i=0;i<20;i++){
//            pool.execute(new RunPool(i));
//        }
        //C2: Dùng ExecutorService
        ExecutorService pool=Executors.newFixedThreadPool(numberProcess);
        for (int i=0;i<20;i++){
            pool.submit(new RunPool(i));
        }
        try {
            pool.awaitTermination(timer,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();//Khi chạy xong thì không thực thi nữa
        System.out.println("Kết thúc");
    }
}
