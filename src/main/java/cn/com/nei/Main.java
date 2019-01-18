package cn.com.nei;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String url = "http://192.168.0.108:8080/uploadAndDownload/downloadFileAction";
        String filePath = "C:/test/download/ss";

        RestTemplate restTemplate = new RestTemplate();
        try {
            //		Object result = restTemplate.getForObject(url, Object.class);
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            ResponseEntity<byte[]> forEntity = restTemplate.getForEntity(url, byte[].class);
            byte[] body = forEntity.getBody();
            System.out.println(body.length);
//            HttpHeaders headers1 = response.getHeaders();
//            System.out.println(headers1);
            byte[] result = response.getBody();
            System.out.println(result.length);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
