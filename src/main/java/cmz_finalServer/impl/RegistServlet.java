package cmz_finalServer.impl;

import cmz_finalServer.Request;
import cmz_finalServer.Response;
import cmz_finalServer.Servlet;

public class RegistServlet extends Servlet {
    @Override
    public void doGet(Request request, Response response) throws Exception {

    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.createBodyln("<html><head><title>返回注册</title>");
        response.createBodyln("</head><body>");
        response.createBodyln("你的用户名为:").createBodyln(request.getParameter("name"));
        response.createBodyln("</body></html>");
    }
}
