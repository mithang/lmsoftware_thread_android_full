import java.util.ArrayList;
import java.util.List;

import static sun.misc.Version.println;

//Có hai cách tạo thread.
//1. Extends từ Thread
class MyThreadOne extends Thread{


    @Override
    public void run() {
        super.run();

        //System.out.println("Tên Thread "+MyThreadOne.class.getName());
        System.out.println(this.getName());
    }
}

//2. Implements từ Runnable
class MyThreadTwo implements Runnable{

    @Override
    public void run() {
        //System.out.println("Tên Thread "+MyThreadTwo.class.getName());
        System.out.println(Thread.currentThread().getName()); // Lấy ra tên thread đã đặt trước đó
    }
}




public class MainCreateThread {

    public static void main(String[] args) {

        MyThreadOne threadOne = new MyThreadOne();
        threadOne.setName("Thread 1"); //Set thread với một tên để quản lí

        MyThreadTwo runable=new MyThreadTwo();
        Thread threaTwo=new Thread(runable);
        threaTwo.setName("Thread 2");//Set thread với một tên để quản lí

        threadOne.start();
        threaTwo.start();
        

    }
}
