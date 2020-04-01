package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 分发器
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;

    public Dispatcher(Socket client) {
        this.client = client;
        //获取请求协议，并拆解报文信息
        this.request = new Request(client);
        this.response = new Response(client);
    }

    @Override
    public void run() {
        //加入servlet解耦业务代码
        Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
        if (servlet != null) {
            servlet.service(request,response);
            //推送
            response.pushToBroser(200);
        } else {
            try {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                response.print(new String(is.readAllBytes(),"GBK"));
                response.pushToBroser(404);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        release();
    }

    public void release() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
