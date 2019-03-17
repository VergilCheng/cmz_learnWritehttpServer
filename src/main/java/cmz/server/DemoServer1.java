package cmz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 手写服务器1：简单的helloworld服务器使用练手
 *我们运用设计模式，将我们的服务器封装重构越来越向着正轨的服务器进发，版本越高，越接近。
 *
 * 获取get请求发送的数据，但是获取不到post请求发送的请求消息实体，但是能够获取请求头
 */
public class DemoServer1 {

    private ServerSocket server ;
    public static void main(String[] args) {
        DemoServer1 server = new DemoServer1();
        server.start();
    }

    /**
     * 服务器启动
     */
    public void start() {
        try {
            server = new ServerSocket(8885);
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
            //接受客户端的信息
            String msg = null;

            //用bufferedReader来读取客户端发送的数据，如果是get请求发送可以从请求头的url中得到消息
            //但是bufferedReader获取不到post方法发送的消息实体。需要用
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while ((msg=br.readLine())!=null) {
                if(msg.length()==0){
                    break;
                }
                sb.append(msg);
                sb.append("\r\n");//换行
            }
            String requestInfo = sb.toString();//请求头
            System.out.println(requestInfo);//打桩，输出请求头

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}
