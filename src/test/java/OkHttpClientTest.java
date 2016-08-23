import okhttp3.*;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by fz on 2016/4/18.
 */
public class OkHttpClientTest {

    public static void main(String[] args) throws Exception {

        File file = new File("README.md");
        OkHttpClient client = new OkHttpClient
                .Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), file);
        Request request = new Request
                .Builder()
                .url("http://middleware.cloud.cainiao.com/dss/putObject.do")
                .header("Content-Type", "application/octet-stream;charset=utf-8")
                .addHeader("Date", SignGenTest.parseGMT(new Date()))
                .addHeader("Auth", SignGenTest.genSign("yichun", "yichun", "GET", "text/xml", null, null, null))
                .addHeader("OrderType", "OrderType")
                .addHeader("OrderNo", "OrderNo")
                .addHeader("ObjectName", "ObjectName")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
