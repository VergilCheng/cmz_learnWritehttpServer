package cmz.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 手写服务器3：简单的helloworld服务器使用练手
 *
 * 对浏览器的请求进行响应
 */
public class DemoServer3 {

    private ServerSocket server ;

    public static final String CRLF = "\r\n";//字符串常量——回车
    public static final String BLANK = " ";//字符串常量——空格

    public static void main(String[] args) {
        DemoServer3 server = new DemoServer3();
        server.start();
    }

    /**
     * 服务器启动
     */
    public void start() {
        try {
            server = new ServerSocket(8887);
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
        BufferedWriter bufferedWriter = null;
        try {
            Socket client = server.accept();//客户端是浏览器，不需要我们手写Client，并手写Socket

            //接受客户端的数据
            byte[] bytes = new byte[20480];
            int len = client.getInputStream().read(bytes);
            String requestInfo = new String(bytes, 0, len);//请求头
            System.out.println(requestInfo);//打桩，输出请求头

            //对请求作出响应
            StringBuilder responseContent = new StringBuilder();//发送给浏览器的网页
            responseContent.append("<html><head><title>hello world</title></head><body>你好我的服务器</body></html>");
            StringBuilder res = new StringBuilder();

            //响应头的http协议版本，状态代码，描述
            res.append("HTTP/1.1").append(BLANK).append("200").append(BLANK).append("OK").append(CRLF);
            //响应头的信息
            res.append("Server:cmz Server/0.0.1").append(CRLF);//服务器版本
            res.append("Date:").append(new Date()).append(CRLF);//日期
            res.append("Content-Type:text/html;charset=utf-8").append(CRLF);//数据的编码格式与文本类型
            res.append("Content-Length:").append(res.toString().getBytes().length).append(CRLF);//数据的字节长度
            //响应实体
            res.append(CRLF);
            res.append(responseContent);

            //将响应信息发送给浏览器
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bufferedWriter.write(res.toString());
            System.out.println(res.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

    }
}
