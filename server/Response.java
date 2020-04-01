package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

public class Response {
    private Socket client;
    private OutputStream os;
    private StringBuilder head;
    private StringBuilder content;
    private int len;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";

    public Response(Socket client){
        this.client = client;
        this.head = new StringBuilder();
        this.content = new StringBuilder();
        this.len = 0;
        try {
            this.os = this.client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创造响应头
    public void creatHead(int code) throws UnsupportedEncodingException {
        len = content.toString().getBytes("GBK").length;
        head.append("HTTP/1.1").append(BLANK);
        head.append(code).append(BLANK);
        switch (code) {
            case 200:
                head.append("OK").append(CRLF);
                break;
            case 404:
                head.append("Not Found").append(CRLF);
                break;
            case 505:
                head.append("Server Error").append(CRLF);
                break;
        }
        //响应头(最后一行是空行)
        head.append("Date:").append(new Date()).append(CRLF);
        head.append("Server:ZD;charset=GBK").append(CRLF);
        head.append("Content-type:text/html").append(CRLF);
        head.append("Content-length:").append(len).append(CRLF);
        head.append(CRLF);
    }

    //创造响应内容
    public Response print(String info) {
        if (info == null) return this;
        this.content.append(info);
        return this;
    }

    public Response println(String info) {
        this.content.append(info).append(CRLF);
        return this;
    }

    //推送给服务器
    public void pushToBroser(int code) {
        try {
            creatHead(code);
            os.write(head.append(content).toString().getBytes("GBK"));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
