import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static sun.misc.Version.println;

//Mục đích là khóa nhưng hàm, biến dùng chung khi có nhiều thread truy cập cùng lúc

class MyThreadBlock extends Thread{

    int sum=0;
    Object lock1=new Object();
    //C1: Khóa toàn bộ một hàm menthod
//    public synchronized void AddSum(int value){
//        sum+=value;
//    }
    //C2: Chỉ khóa nội dung cần block
    public synchronized void AddSum(int value){
        //Có bao nhieu synchronized thì cần có bao nhiêu lock1,lock2...
        synchronized (lock1){
            sum+=value;
        }

    }

    public void Process(){
        for(int i=0;i<10000;i++){
            AddSum(1);
        }
    }
}

public class MainBlockThread {

    public static void main(String[] args) {

        MyThreadBlock thread=new MyThreadBlock();
        System.out.println("Đang xử lí...");
        //thread.Process();//Kết quả trả về đúng bằng 10000
        //System.out.println("Kết thúc tổng: "+thread.sum);

        //Nếu tạo 2 thread chạy bắt đồng bộ và dùng chung biên sum, kết quả không đúng. Cần block thread lại.
        //Nếu thread nào vào trước sẽ xử lí trước dùng synchronized
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                thread.Process();
            }
        });
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                thread.Process();
            }
        });
        thread1.start();
        thread2.start();

        try {
            thread1.join();//thread1 chạy xong rồi mới đến thread2.
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Chờ đợi 2 task thực hiện xong mới in ra kết quả
        System.out.println("Kết thúc tổng: "+thread.sum);
    }
}
