package cmz.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 手写服务器4：简单的helloworld服务器使用练手
 *
 * 对浏览器的请求进行响应,利用我们封装好的Response来进行回复
 */
public class DemoServer4 {

    private ServerSocket server ;

    public static final String CRLF = "\r\n";//字符串常量——回车
    public static final String BLANK = " ";//字符串常量——空格

    public static void main(String[] args) {
        DemoServer4 server = new DemoServer4();
        server.start();
    }

    /**
     * 服务器启动
     */
    public void start() {
        try {
            server = new ServerSocket(8888);
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

            //接受客户端的数据
            byte[] bytes = new byte[20480];
            int len = client.getInputStream().read(bytes);
            String requestInfo = new String(bytes, 0, len);//请求头
            System.out.println(requestInfo);//打桩，输出请求头

            //对请求作出响应
            Response resp = new Response(client.getOutputStream());
            resp.createBodyln("<html><head><title>hello world</title></head><body>你好我的服务器</body></html>");
            resp.pushToClient(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}
