package cmz.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写服务器4：简单的helloworld服务器使用练手
 *
 * 对于浏览器请求，我们封装request进行对信息的处理和存储
 * 对浏览器的请求进行响应,利用我们封装好的Response来进行回复
 *
 * 并利用servlet做封装,servlet用来封装resp向浏览器发送数据的方法。
 */
public class DemoServer6 {

    private ServerSocket server ;

    public static final String CRLF = "\r\n";//字符串常量——回车
    public static final String BLANK = " ";//字符串常量——空格

    public static void main(String[] args) {
        DemoServer6 server = new DemoServer6();
        server.start();
    }

    /**
     * 服务器启动
     */
    public void start() {
        try {
            server = new ServerSocket(8890);
            System.out.println("DemoServer服务器启动成功");//打桩
            System.out.println("等待客户端连接");
            this.receive();
            System.out.println("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器接受客户端请求开启一个socket的方法封装
     */
    private void receive() {

        try {
            Socket client = server.accept();//客户端是浏览器，不需要我们手写Client，并手写Socket

            //用servlet封装req与resp来做处理
            Servlet servlet = new Servlet();
            Request request = new Request(client.getInputStream());
            Response response = new Response(client.getOutputStream());
            servlet.service(request,response);
            response.pushToClient(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}
