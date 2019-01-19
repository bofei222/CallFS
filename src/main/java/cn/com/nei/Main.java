package cn.com.nei;

import cn.nei.tos3.config.StorageConfig;
import cn.nei.tos3.sf.StorageFile;
import cn.nei.tos3.sf.ToS3;
import org.apache.commons.io.IOUtils;
import org.omg.CORBA.IntHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class Main {
    static String url = "http://mirrors.cn99.com/centos/7.6.1810/isos/x86_64/CentOS-7-x86_64-DVD-1810.iso";
    static String url2 = "http://192.168.0.108:8080/uploadAndDownload/downloadFileAction";
    static String filePath = "C:/test/download/ss";

        public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Range", "bytes=" + 1 + "-" + 100);
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Resource.class );
        System.out.println(responseEntity.getBody().contentLength());
//        try {
//            System.out.println(responseEntity.getStatusCode());
//            HttpHeaders headers1 = responseEntity.getHeaders();
//            headers1.forEach((k,v) -> System.out.println(k + " " + v));
//            InputStream io = responseEntity.getBody().getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(io);
//            byte[] b = new byte[10];
//            int read = 0;
//            while (read != -1) {
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    public static void main3(String[] args) {
        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        StorageFile s3 = new ToS3(sc);

        RestTemplate restTemplate = new RestTemplate();
        test(restTemplate, s3, 0L);
    }


    public static void test(RestTemplate restTemplate, StorageFile s3, long l) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Range", "bytes=" + l+1 + "-" + "结尾");
        ResponseEntity<Resource> responseEntity = restTemplate.exchange( url, HttpMethod.GET, new HttpEntity<>(headers), Resource.class );

        InputStream responseInputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int read = 0;

        try {
            responseInputStream = responseEntity.getBody().getInputStream();
            read = responseInputStream.read(b);
            throw new IOException();
        }
        catch (IOException e) {
//            throw new RuntimeException(e);

            headers.set("Range", "bytes=-1");
            test(restTemplate, s3, l);
        }
        while (read != -1) {
            IntHolder holder = new IntHolder();
            s3.write(b, 0, b.length, holder);
            // 记录已写长度
            l += holder.value;
        }
    }

    public static void main2(String[] args) {


        RestTemplate restTemplate = new RestTemplate();
        try {

            InputStream inputStream = restTemplate.getForObject(url, InputStream.class);
            IOUtils.toByteArray(inputStream);
            HttpHeaders headers = new HttpHeaders();
//            headers.add("range", "-500");
            headers.set("Range", "bytes=" + 1 + "-" + 100);


            System.out.println(headers);
//            headers.setAccept("");
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);


            byte[] body = response.getBody();
            System.out.println(body.length);


//            ResponseEntity<byte[]> forEntity = restTemplate.getForEntity(url, byte[].class);
//            byte[] body = forEntity.getBody();
//            System.out.println(body.length);

            HttpHeaders headers1 = response.getHeaders();
            System.out.println(headers1);
            System.out.println(headers1.getContentType());
            System.out.println(headers1.getContentLength());
//            System.out.println(headers1.ra);
            HttpStatus statusCode = response.getStatusCode();
            System.out.println(statusCode);
            byte[] result = response.getBody();
            System.out.println(result.length);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
