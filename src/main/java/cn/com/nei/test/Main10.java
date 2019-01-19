package cn.com.nei.test;

import cn.nei.tos3.config.StorageConfig;
import cn.nei.tos3.sf.StorageFile;
import cn.nei.tos3.sf.ToS3;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.IntHolder;

/**
 * @Author bofei
 * @Date 2019/1/18 17:39
 * @Description
 */
public class Main10 {

    /**
     * 通过GET方式发起http请求
     */
//    @
    public static void main2(String[] args) {
        requestByGetMethod();
    }

    public static void requestByGetMethod() {
        //创建默认的httpClient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet("http://www.baidu.com");
            System.out.println("执行get请求:...." + get.getURI());
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpclient.execute(get);
            try {
                //response实体
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity) {
                    System.out.println("响应状态码:" + httpResponse.getStatusLine());
                    System.out.println("-------------------------------------------------");
                    System.out.println("响应内容:" + EntityUtils.toString(entity));
                    System.out.println("-------------------------------------------------");
                }
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try{
//                closeHttpClient(httpClient);
//            } catch (IOException e){
//                e.printStackTrace();
//            }
        }

    }

    public static void main(String[] args) {
        int p1 = 5 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p2 = 3 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        int p3 = 3 * 1024 * 1024; //分段大小在 5MB - 5GB 之间，只有最后一个分段才允许小于 5MB，不可避免的
        byte[] b1 = RandomStringUtils.randomAlphabetic(p1).getBytes(); //填充一个 5MB 的字符串
        byte[] b2 = RandomStringUtils.randomAlphabetic(p2).getBytes(); //填充一个 5MB 的字符串
        byte[] b3 = RandomStringUtils.randomAlphabetic(p3).getBytes(); //填充一个 5MB 的字符串
        byte[] b4 = "abcdefgh".getBytes();

        StorageConfig sc = new StorageConfig("C:/test/s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);
        boolean open = toS3.open("bofei的一个13文件", "w");
        IntHolder holder = new IntHolder();
//        toS3.write(b1, 0, b1.length, holder);
//        toS3.write(b2, 0, b2.length, holder);
//        toS3.write(b3, 0, b3.length, holder);
        toS3.write(b4, 0, b4.length, holder);
        toS3.close();

    }
}
