package server;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket serverSocket;
    private boolean isRunning;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8888);
            isRunning = true;
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败！");
        }
    }

    public void receive() {
        while (isRunning) {
            try {
                //阻塞等待连接
                Socket client = serverSocket.accept();
                System.out.println("一个客户端连接！");
                new Thread(new Dispatcher(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
                stop();
            }
        }
    }
    public void stop() {
        isRunning = false;
        try {
            this.serverSocket.close();
            System.out.println("服务器已停止！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
