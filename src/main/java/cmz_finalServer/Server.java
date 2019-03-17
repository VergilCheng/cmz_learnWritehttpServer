package cmz_finalServer;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写服务器4：简单的helloworld服务器使用练手
 *
 * 对于浏览器请求，我们封装request进行对信息的处理和存储
 * 对浏览器的请求进行响应,利用我们封装好的Response来进行回复
 *
 * 并利用servlet做封装,dispatcher做多线程分发
 */
public class Server {

    private ServerSocket server;

    public static final String CRLF = "\r\n";//字符串常量——回车
    public static final String BLANK = " ";//字符串常量——空格

    //判断服务器是否该shutDown
    private boolean isShutDown = false;


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    /**
     * 服务器启动
     */

    public void start() {
        start(8893);
    }

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("DemoServer服务器启动成功");//打桩
            System.out.println("等待客户端连接");
            this.receive();
            System.out.println("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }

        /**
         * 服务器接受客户端请求开启一个socket的方法封装
         */


    }
    private void receive () {


        Socket client = null;
        try {
            while (!isShutDown) {
                client = server.accept();//客户端是浏览器，不需要我们手写Client，并手写Socket
                Thread clientHandler = new Thread(new Dispatcher(client));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }


    }

    public void stop(){
        isShutDown = true;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}