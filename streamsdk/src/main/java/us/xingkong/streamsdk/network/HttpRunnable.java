package us.xingkong.streamsdk.network;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 饶翰新 on 2017/12/20.
 * <p>
 * HTTP请求执行器
 */

public abstract class HttpRunnable implements Runnable {

    /**
     * 要执行的Http请求
     */
    private HttpRequest request;

    /**
     * 默认构造函数
     *
     * @param request 要执行的Http请求
     */
    public HttpRunnable(HttpRequest request) {
        this.request = request;
    }

    /**
     * 获取请求
     *
     * @return
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * 请求完成后的触发事件
     *
     * @param result 请求结果
     * @param e      请求时异常
     */
    public abstract void onDone(String result, Exception e);

    @Override
    public void run() {
        //本方法实现对请求对象的请求
        try {
            HttpURLConnection conn;
            String url = request.parseURL();//获取请求对象的完整url

            conn = (HttpURLConnection) (new URL(url).openConnection());
            conn.setDoOutput(true);
            conn.setConnectTimeout(2000);//设置连接超时
            conn.setReadTimeout(3000);//设置读取数据超时

            conn.setRequestProperty("Cookie", request.getCookie());//将本地Cookie传递给服务器

            conn.getOutputStream().write(request.parsePOST().getBytes());//将POST传递给服务器
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            if (conn.getHeaderField("Set-Cookie") != null)
                request.setCookie(conn.getHeaderField("Set-Cookie"));//将服务器的Set-Cookie存进request

            //读取并保存数据
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            out.flush();

            request.setResult(new String(out.toByteArray()));//保存结果进request

            onDone(request.getResult(), null);//触发回调事件
        } catch (Exception e) {
            onDone(request.getResult(), e);//触发异常事件
        }

    }
}
