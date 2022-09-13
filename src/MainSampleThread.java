import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static sun.misc.Version.println;


class SampleThread extends Thread{

    boolean isStop=true;

    @Override
    public void run() {
        super.run();
        while (isStop){
            try {
                System.out.println("Timer: run");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread(){
        isStop=false;
    }


}

public class MainSampleThread {

    public static void main(String[] args) {
        SampleThread thread=new SampleThread();
        thread.start();

        Scanner input=new Scanner(System.in);
        input.nextLine();
        //thread.stopThread();
        thread.stop();
    }
}
