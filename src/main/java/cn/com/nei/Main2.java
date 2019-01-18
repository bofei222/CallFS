package cn.com.nei;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * @Author bofei
 * @Date 2019/1/18 10:12
 * @Description How to forward large files with RestTemplate?
 */
public class Main2 {
    public static void main(String[] args) {
        String url = "http://localhost:4000";
        String filePath = "C:/test/fs.properties";

        RestTemplate restTemplate = new RestTemplate();

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        restTemplate.setRequestFactory(requestFactory);

        File file = new File("");

        HttpEntity<FileSystemResource> requestEntity = new HttpEntity<>(new FileSystemResource(file));
        ResponseEntity e = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
        System.out.println(e.getBody());
    }
}
