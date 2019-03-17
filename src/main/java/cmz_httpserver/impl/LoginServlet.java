package cmz_httpserver.impl;

import cmz_httpserver.Request;
import cmz_httpserver.Response;
import cmz_httpserver.Servlet;

public class LoginServlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        System.out.println(request);//输出req
        response.createBodyln("<html><head><title>hello myServer</title>");
        response.createBodyln("</head><body>");
        //这里就体现出为什么createBodyIn为什么要返回resq对象了！！可以连续调用createBodyIn方法，方便做循环操作
        response.createBodyln("欢迎：").createBodyln(request.getParameter("name")).createBodyln("回来");
        response.createBodyln("</body></html>");
        System.out.println(response);
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {

    }
}
