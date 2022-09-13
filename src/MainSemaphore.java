import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//Mục đích là giới hạn số lượng truy cập vào. Ví dụ: chỉ cho phép 2 luồng kết nối, kết nối thứ ba sẽ bị từ chối
//Khi giải phóng thì luồng tiếp theo mới được kết nối tiếp

class Connect{

    static Connect newConnect=new Connect();
    int dem=0;
    Semaphore semaphore=new Semaphore(20);//Cho phép 20 luồng truy cập

    public Connect(){

    }
    public static Connect openConnect(){
        return newConnect;
    }
    public void DemSoLuongTruyCap(){
        synchronized (this){
            try {
                semaphore.acquire();// Giảm số luồng truy cập
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dem++;
            System.out.println("Số luồng truy cập hiện tại: "+dem);
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this){
            dem--;
            semaphore.release();//Tăng số luồng truy cập sau 2s
        }

    }
}

public class MainSemaphore {
    public static void main(String[] args) {
        System.out.println("Đang xử lí...");

//        Semaphore semaphore=new Semaphore(2);
//        try {
//            semaphore.acquire();// Giảm số lượng kết nối xuống một, khi có 1 user truy cập
//            semaphore.release();// Tăng số lượng kết nối lên một, khi có user out ra
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Giá trị cho phép truy cập: "+semaphore.availablePermits());

        Connect.openConnect().DemSoLuongTruyCap();
        ExecutorService pool= Executors.newCachedThreadPool();//Tự bắt số luồng truy cập vì newFixedThreadPool phải chỉ định số luồng
        for (int i=0;i<300;i++){
            pool.submit(new Runnable() {
                @Override
                public void run() {
                Connect.openConnect().DemSoLuongTruyCap();
                }
            });
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Kết thúc");
    }
}
