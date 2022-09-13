import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Mục đích nếu có nhiều tiến trình xử lí, thì tại một thời điểm chỉ có một threas được hoạt động, và các thread khác ngủ chở đợi. Khi thread 1 chạy xong.
//Sau đó gửi thông báo đến các thread khác để chạy tiếp theo.
//VD: Có 2 thread: 1 là ngưởi gủi tin nhắn, 1 là người nhận tin nhắn. 2 thread này chạy cùng lúc. thread 1 chạy trước nạp nội dung rồi thông báo cho thread 2
//đã sử lí xong, và lấy tin nhắn đi

class TinNhan{
    String noidung;

    public TinNhan(String noidung) {
        this.noidung = noidung;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
class NguoiGuiTinNhan implements Runnable{
    TinNhan tinNhan;

    NguoiGuiTinNhan(TinNhan tn){
        tinNhan=tn;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (tinNhan){

                tinNhan.setNoidung("Xin chào bạn");
                tinNhan.notifyAll();

        }
    }
}
class NguoiNhanTinNhan implements Runnable{
    TinNhan tinNhan;
    NguoiNhanTinNhan(TinNhan tn){
        tinNhan=tn;
    }
    @Override
    public void run() {
        synchronized (tinNhan){
            System.out.println("Đang chờ lấy tin nhắn...");
            try {
                tinNhan.wait();
                System.out.println("Đã nhận nội dung: "+tinNhan.getNoidung());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class MainWaitNotify {
    public static void main(String[] args) {
        System.out.println("Đang xử lí...");
        TinNhan tn=new TinNhan("Em ngủ chưa");
        Thread t1=new Thread(new NguoiGuiTinNhan(tn));
        Thread t2=new Thread(new NguoiNhanTinNhan(tn));
        t1.start();
        t2.start();
        System.out.println("Kết thúc");
    }
}
