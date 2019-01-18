package cn.com.nei;


import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.plugin2.message.HeartbeatMessage;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author bofei
 * @Date 2019/1/18 11:23
 * @Description
 */
public class TestHttpClient {
    public static void main(String[] args) throws IOException {
        String url = "http://192.168.0.108:8080/uploadAndDownload/downloadFileAction";
        String filePath = "C:/test/fs.properties";

        CloseableHttpClient httpclient = HttpClients.createDefault();

//        HttpHead httpHead = new HttpHead(url);
//        httpHead.addHeader("Range", "bytes=0-"+(100));

        HttpGet httpget = new HttpGet(url);
//        httpget.addHeader("Range", "bytes="+1+"-"+11);
        httpget.setHeader("Range", "bytes=" + 1 + "-" + 100);
        CloseableHttpResponse response = httpclient.execute(httpget);
        InputStream content = response.getEntity().getContent();

        BufferedInputStream bis = new BufferedInputStream(content);
//        Header[] allHeaders = response.getAllHeaders();
//        for (Header header : allHeaders) {
//            System.out.println(header.getName());
//        }
////        byte[] bytes = IOUtils.toByteArray(content);
////        System.out.println(new String(bytes));
////        System.out.println(bytes.length);
//        byte[] buff = new byte[1024 * 1024];
//        int l = 0;
////        new BufferedWriter(new)
//        while ((l = bis.read(buff, 0, buff.length)) != -1) {
//            System.out.println(new String(buff));
//        }
//        bis.close();

    }

    /**
     * 通过GET方式发起http请求
     */
//    @Test
    public void requestByGetMethod(){
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
