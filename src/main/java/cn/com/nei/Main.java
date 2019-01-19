package cn.com.nei;

import org.apache.commons.io.IOUtils;
import org.omg.CORBA.IntHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class Main {
    static String url = "http://192.168.0.108:8080/uploadAndDownload/downloadFileAction";
    static String filePath = "C:/test/download/ss";


    public static void main(String[] args) {
        StorageConfig sc = new StorageConfig("C:\\test\\s3.properties");
        sc.init();
        StorageFile toS3 = new ToS3(sc);

        RestTemplate restTemplate = new RestTemplate();
        test(restTemplate, 0L);
    }


    public static void test(RestTemplate restTemplate, StorageFile toS3, long l) {
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
            test(restTemplate, toS3, l);
        }
        while (read != -1) {
            IntHolder holder = new IntHolder();
            toS3.write(b, 0, b.length, holder);
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
