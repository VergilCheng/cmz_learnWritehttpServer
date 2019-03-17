package cmz.server;

import java.io.IOException;
import java.net.Socket;

/**
 * servlet用来做多线程与代码的封装
 */
public class Servlet {




    public void service(Request req, Response resp) {
        System.out.println(req);//输出req
        resp.createBodyln("<html><head><title>hello world</title>");
        resp.createBodyln("</head><body>");
        //这里就体现出为什么createBodyIn为什么要返回resq对象了！！可以连续调用createBodyIn方法，方便做循环操作
        resp.createBodyln("欢迎：").createBodyln(req.getParameter("name")).createBodyln("回来");
        resp.createBodyln("</body></html>");
        System.out.println(resp);//谁出resp

    }

}
