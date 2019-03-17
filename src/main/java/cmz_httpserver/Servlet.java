package cmz_httpserver;



/**
 * servlet用来做多线程与代码的封装
 *
 * 处理各种请求，不仅仅是登陆
 *
 */
public abstract class Servlet {




    public void service(Request req, Response resp) throws Exception {

        this.doGet(req,resp);
        this.doPost(req,resp);

    }

    public abstract void doGet(Request request,Response response) throws Exception;
    public abstract void doPost(Request request,Response response) throws Exception;

}
