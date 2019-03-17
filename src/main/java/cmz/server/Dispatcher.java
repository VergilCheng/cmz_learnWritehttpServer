package cmz.server;

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

        Servlet servlet = new Servlet();
        servlet.service(request,response);
        try {
            response.pushToClient(code);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.pushToClient(500);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
