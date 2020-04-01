package server;


import java.util.List;

public class RegisterServlet implements Servlet {
    @Override
    public void service(Request request,Response response) {
        String uname = request.getNameByKey("uname");
        List<String> params = request.getParamsByKey("fav");
        response.print("<html>");
        response.print("<title>");
        response.print("注册成功");
        response.print("</title>");
        response.print("<body>");
        response.println("你好:" + uname + "!");
        response.print("你喜欢的女神为：");
        for (String str : params) {
            if(str.equals("0")) {
                response.println("刘亦菲");
            } else if (str.equals("1")) {
                response.println("刘诗诗");
            } else if (str.equals("2")) {
                response.println("章泽天");
            }
        }
        response.print("</body>");
        response.print("</html>");
    }
}
