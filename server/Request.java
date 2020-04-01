package server;


import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.*;

public class Request {
    private final String CRLF = "\r\n";
    private Socket client;
    private InputStream is;
    private byte[] data;
    private String method = "";
    private String url = "";
    private String param = "";
    private Map<String, List<String>> map;

    public Request(Socket client) {
        try {
            this.client = client;
            this.is = client.getInputStream();
            this.data = new byte[1024*1024];
            int len = is.read(data);
            this.map = new HashMap<>();
            if (len > 0) {
                String str = new String(data,0,len);
                System.out.println(str);
                parseInfo(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseInfo(String data) {
        int index = data.indexOf('/');
        this.method = data.substring(0,index).trim().toLowerCase();
        System.out.println("method:" + method);
        this.url = data.substring(index,data.indexOf("HTTP/")).trim();
        index = url.indexOf('?');
        if (index> 0) { //如果有参数
            this.param = url.substring(index + 1);
            this.url = url.substring(0,index);
        }
        System.out.println("url:" + this.url);

        //如果是get，请求参数在第一行，如果是post还可能在最后一行
        if (method.equals("post")) {
            index = data.lastIndexOf(CRLF);
            if (index > 0) {
                String temp = data.substring(index).trim();
                if (param.length() == 0) {
                    param = temp;
                } else {
                    param += "&"+ temp;
                }
            }
        }
        System.out.println("param:" + param);
        covertMap();
    }

    private void covertMap() {
        //根据&来分
        String[] params = param.split("&");
        //根据=来分
        for (String s : params) {
            String[] strs = s.split("=");
            strs = Arrays.copyOf(strs,2);
            if (!map.containsKey(strs[0])) {
                map.put(strs[0], new ArrayList<>());
            }
            map.get(strs[0]).add(strs[1]);
        }
    }

    public String getNameByKey(String str) {
        if (map.containsKey(str)) {
            return map.get(str).get(0);
        }
        return null;
    }

    public List<String> getParamsByKey(String str) {
        if (map.containsKey(str)) {
            return map.get(str);
        }
        return null;
    }

    public String getUrl() {
        return this.url;
    }
}
