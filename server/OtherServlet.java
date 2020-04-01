package server;

public class OtherServlet implements Servlet{
    @Override
    public void service(Request request,Response response) {
        response.print("<html>");
        response.print("<title>");
        response.print("第三个servlet");
        response.print("</title>");
        response.print("<body>");
        response.print("其他测试页面！");
        response.print("</body>");
        response.print("</html>");
    }
}
