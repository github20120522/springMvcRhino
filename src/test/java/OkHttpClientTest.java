import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by fz on 2016/4/18.
 */
public class OkHttpClientTest {

    public static void main(String[] args) throws IOException {

        System.setProperty("jsse.enableSNIExtension", "false");
        String url = "https://login.weixin.qq.com/jslogin";
        url += "?appid=" + URLEncoder.encode("wx782c26e4c19acffb", "utf-8");
        url += "&fun=" + URLEncoder.encode("new", "utf-8");
        url += "&lang=" + URLEncoder.encode("zh_CN", "utf-8");
        url += "&_=" + URLEncoder.encode(System.currentTimeMillis() + "", "utf-8");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println(response.body().string());
    }
}
