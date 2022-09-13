import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Mục đích khóa nhưng thread dùng chung dữ liệu chia sẽ. Tương tự như synchronized nhưng linh động hơn dùng ở mọi nơi
//Chú ý tránh tình trạng dealock, 2 thread chờ đợi lẫn nhau
class Runner{

    int dem=0;
    Lock lock=new ReentrantLock();
    Condition cond=lock.newCondition();
    void TangDem(){
        for (int i=0;i<10000;i++){
            dem++;
        }
    }
    //C1: Hoạt động tương tự như synchronized
//    void ProcessOne(){
//        lock.lock();
//        TangDem();
//        lock.unlock();
//
//    }
//    void ProcessTwo(){
//        lock.lock();
//        TangDem();
//        lock.unlock();
//
//    }
    //C2: Áp dụng thêm Condition để gửi và chờ tiến trình khác xử lí
    //Mô tả nghiệp vụ: Đầu tiền ProcessOne và ProcessTwo cùng chạy, nhưng sau đó thread 2 sẽ ngủ 1s.
    //Tiến trình 1 xử lí xuất ra câu println. Sau đó chờ cond.await() tiến trình 2 xử lí. Sau khi ngủ xuống tiến trình 2 kích hoạt.
    //Sau đó gửi thông báo cond.signalAll() để cho tiến trình 1 xử lí tiếp
    void ProcessOne(){
        lock.lock();
        System.out.println("Tiến trình một đang chờ xử lí...");
        try {
            cond.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TangDem();
        lock.unlock();

    }
    void ProcessTwo(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        TangDem();
        System.out.println("Nhập enter để chạy tiến trình một");
        Scanner scanner=new Scanner(System.in);
        scanner.nextLine();
        cond.signalAll();
        lock.unlock();

    }
    void XuatKetQua(){
        System.out.println("Đây là kết quả sau khi đếm được: "+dem);
    }
}

public class MainReentranLook {

    public static void main(String[] args) {
        System.out.println("Đang xử lí...");
        Runner runner=new Runner();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
            runner.ProcessOne();
            }
        });
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                runner.ProcessTwo();
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
        runner.XuatKetQua();

        System.out.println("Kết thúc");
    }
}
