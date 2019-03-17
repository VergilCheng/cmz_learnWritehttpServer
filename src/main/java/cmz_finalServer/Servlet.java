package cmz_finalServer;


/**
 * servlet用来做多线程与代码的封装
 *
 * 处理各种请求，不仅仅是登陆
 *
 */
public abstract class Servlet {




    protected void service(Request req, Response resp) throws Exception {

        this.doGet(req,resp);
        this.doPost(req,resp);

    }

    protected abstract void doGet(Request request, Response response) throws Exception;
    protected abstract void doPost(Request request, Response response) throws Exception;

}
