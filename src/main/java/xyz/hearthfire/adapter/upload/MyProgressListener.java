package xyz.hearthfire.adapter.upload;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

/**
 * Created by fz on 2015/5/31.
 */
public class MyProgressListener implements ProgressListener {

    private long bytesRead = 0;
    private long contentLength = 0;
    private int items = 0;
    private boolean multipartFinished = false;

    public MyProgressListener(HttpServletRequest request) {
        request.getSession().setAttribute("ProgressListener_" + request.getParameter("upload_key"), this);
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
        this.items = items;
        // System.out.println(bytesRead + "," + contentLength + "," +items);
    }

    public void setMultipartFinished() {
        this.multipartFinished = true;
    }

    public boolean isFinished() {
        return multipartFinished;
    }

    public int getItems(){
        return items;
    }

    public long getPercentDone() {
        double percent = (double)bytesRead / contentLength;
        return Math.round(percent * 100);
    }

    public static void main(String[] args){
        /*
            http://bbs.csdn.net/topics/270010124
            需要将除号两端的任何一个数转为 float 或者是 double 才能计算出小数位数，
            否则的话整数类型与整数类型计算得出的结果还是整数类型，直接用 long/long
            或者 int/int 的话，得出的结果是数学值的舍尾取整。

            而转成 float 或 double 就不一样了，任何数与 float 和 double 进行计算时
            会自动地进行类型提升，即将另外一个 long 数据提升到 double 类型再进行计
            算的。

            写成这样也是一样的 a / (double)(a + b)
         */
        long a = 5;
        long b = 10;
        double percent = (double)a / (a + b);
        DecimalFormat format = new DecimalFormat("0.00%");
        String result = format.format(percent);
        System.out.println(result);

        long c = 555555555;
        long d = 666666666;
        double p = (double) c / d;
        System.out.println(Math.round(p * 100));
    }
}
