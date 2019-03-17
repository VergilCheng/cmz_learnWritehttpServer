package cmz_httpserver;



import java.io.IOException;
import java.net.Socket;

/**
 * 创建线程类，用来分发请求
 */
public class Dispatcher implements Runnable {


    private Socket client;
    private Request request;
    private Response response;
    private int code=200;

    Dispatcher(Socket client) {
        this.client=client;
        try {
            request = new Request(client.getInputStream());
            response = new Response(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            code =500;
            return;
        }

    }

    @Override
    public void run() {

        try {
            System.out.println(request.getUrl());//打桩
            Servlet servlet = WebApp.getServlet(request.getUrl());
            //System.out.println(servlet);//打桩
            if (null==servlet) {
                this.code=404;//找不到对应处理
            }
            servlet.service(request,response);
            response.pushToClient(code);
        } catch (Exception e) {
            e.printStackTrace();
            this.code=500;
            try {
                response.pushToClient(500);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
