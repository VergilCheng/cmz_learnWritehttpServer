package cmz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写服务器2：简单的helloworld服务器使用练手
 *
 * 获取post请求发送的消息实体
 */
public class DemoServer2 {

    private ServerSocket server ;
    public static void main(String[] args) {
        DemoServer2 server = new DemoServer2();
        server.start();
    }

    /**
     * 服务器启动
     */
    public void start() {
        try {
            server = new ServerSocket(8886);
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
            StringBuffer sb = new StringBuffer();
            //接受客户端的id流
            byte[] bytes = new byte[20480];
            int len = client.getInputStream().read(bytes);
            String requestInfo = new String(bytes,0,len);//请求头
            System.out.println(requestInfo);//打桩，输出请求头

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}
