package cn.com.nei;


import cn.nei.tos3.config.StorageConfig;
import cn.nei.tos3.sf.StorageFile;
import cn.nei.tos3.sf.ToS3;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.IntHolder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author bofei
 * @Date 2019/1/18 11:23
 * @Description
 */
public class TestHttpClient {
    public static void main(String[] args) {
        String url = "http://192.168.0.108:8080/uploadAndDownload/downloadFileAction";
        CloseableHttpClient httpclient = HttpClients.createDefault();

//        HttpHead httpHead = new HttpHead(url);
//        httpHead.addHeader("Range", "bytes=0-"+(100));

        HttpGet httpget = new HttpGet(url);
//        httpget.addHeader("Range", "bytes="+1+"-"+11);
        httpget.setHeader("Range", "bytes=" + 1 + "-" + 100);
        CloseableHttpResponse response = httpclient.execute(httpget);
        System.out.println(response.getStatusLine());
        System.out.println(response.getEntity().getContentLength());

        InputStream is = response.getEntity().getContent();
        BufferedInputStream bis = new BufferedInputStream(is);

        byte[] b = new byte[1024 * 1024];
        int l = 0;
        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        IntHolder holder = new IntHolder();

        boolean open = toS3.open("bofei的一个10文件", "w");
        try {
            l = bis.read(b);
        } catch (IOException e) {

            e.printStackTrace();
        }
        while (l != -1) {
            toS3.write(b, 0, b.length, holder);
        }
        toS3.close();

    }
    /**
     * 通过GET方式发起http请求
     */
//    @
    public static void main2(String[] args) {
        requestByGetMethod();
    }
    public static void requestByGetMethod(){
        //创建默认的httpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet("http://www.baidu.com");
            System.out.println("执行get请求:...."+get.getURI());
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpclient.execute(get);
            try{
                //response实体
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    System.out.println("响应状态码:"+ httpResponse.getStatusLine());
                    System.out.println("-------------------------------------------------");
                    System.out.println("响应内容:" + EntityUtils.toString(entity));
                    System.out.println("-------------------------------------------------");
                }
            }
            finally{
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
//            try{
//                closeHttpClient(httpClient);
//            } catch (IOException e){
//                e.printStackTrace();
//            }
        }

    }


}
