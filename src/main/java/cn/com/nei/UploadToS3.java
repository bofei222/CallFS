package cn.com.nei;


import cn.nei.tos3.config.StorageConfig;
import cn.nei.tos3.sf.StorageFile;
import cn.nei.tos3.sf.ToS3;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.omg.CORBA.IntHolder;
import org.springframework.http.HttpHeaders;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @Author bofei
 * @Date 2019/1/18 11:23
 * @Description
 */
public class UploadToS3 {

    static String s3ConfigPath;
    static StorageFile toS3;
    static String url = null;
    static String dest = null;

    //
    static byte[] b = new byte[100 * 1024];

//    static String url2 = "http://mirrors.cn99.com/centos/7.6.1810/isos/x86_64/CentOS-7-x86_64-DVD-1810.iso";
//    static String url2 = "http://45.77.21.15:81/test.html";
//    static String dest = "bofei的一个11文件";
//    static String s3ConfigPath = "c:/test/s3.properties";

    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("Ex: url dest s3configpath");
            System.exit(1);
        }
        url = args[0];
        dest = args[1];
        s3ConfigPath = args[2];
        System.out.println("src " + url);
        System.out.println("dest " + dest);
        System.out.println("s3ConfigPath " + s3ConfigPath);

        StorageConfig sc = new StorageConfig(s3ConfigPath);
        sc.init();
        toS3 = new ToS3(sc);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.setHeader("Range", "bytes=" + 2 + "-");
        CloseableHttpResponse response = httpclient.execute(request);

        toS3.open(dest, "w");
        test(httpclient, toS3, 0);
        toS3.close();

//        Header[] allHeaders = response.getAllHeaders();

//        for (Header header : allHeaders) {
//            System.out.println(header.getName() + " " + header.getValue());
//        }
//        System.out.println(response.getStatusLine());
//
//        InputStream is = response.getEntity().getContent();
//        BufferedInputStream bis = new BufferedInputStream(is);
//
//        byte[] b = new byte[10];
//        long l = 0;
//        while ((l = bis.read(b)) != -1) {
//            System.out.println(new String(b));
//        }
    }

    private static int count = 0;
    private static int count2 = 0;
    /**
     * @Author: bofei
     * @Date: 2019/1/19 17:20
     * @Param: [httpclient, s3, l]
     * @return: 递归请求资源 写到s3
     * @Description:
     */
    public static void test(CloseableHttpClient httpclient, StorageFile s3, long l) {

        HttpHeaders headers = new HttpHeaders();
        HttpGet request = new HttpGet(url);

        request.setHeader("Range", "bytes=" + l + "-");

        InputStream is = null;
        BufferedInputStream bis = null;
        CloseableHttpResponse response = null;
        int read = 0;

        try {
            System.out.println("第 " + ++count + " 次http请求");
            response = httpclient.execute(request);

            is = response.getEntity().getContent();
            bis = new BufferedInputStream(is);
            long contentLength = response.getEntity().getContentLength();

            for (Header header : response.getAllHeaders()) {
                System.out.println(header.getName() + header.getValue());
            }

            while ((read = bis.read(b)) != -1) {
                IntHolder holder = new IntHolder();
                System.out.println("write的次数========" + ++count2);
                // read 是这次从流中读取到b中的实际数据
                s3.write(b, 0, read, holder);

//                System.out.println(read);
                // 记录已写长度
                l += read;
//                System.out.println("记录的长度 " + l);
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            test(httpclient, s3, l);
        }
    }


}
