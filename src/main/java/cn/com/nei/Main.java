package cn.com.nei;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class Main {
    static String url = "http://192.168.0.108:8080/uploadAndDownload/downloadFileAction";
    static String filePath = "C:/test/download/ss";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> responseEntity = restTemplate.exchange( url, HttpMethod.GET, someHttpEntity, Resource.class );

        InputStream responseInputStream;
        try {
            responseInputStream = responseEntity.getBody().getInputStream();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
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
