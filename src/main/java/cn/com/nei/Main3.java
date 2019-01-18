package cn.com.nei;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author bofei
 * @Date 2019/1/18 10:38
 * @Description
 */
public class Main3 {
    /**
     * 断点下载
     *
     * @throws InterruptedException
     */
//    @Test
    public void testDebugDownload() throws IOException, InterruptedException {
        // 创建URL对象
        URL url = new URL("http://localhost:8080/file.apk");
        // 使用url获取HttpURLConnection对象
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 客户端的请求方式
        conn.setRequestMethod("GET");
        // 已经下载的字节数
        long alreadySize = 0;
        // 将文件写到download/file.apk中
        File file = new File("/download/file.apk");
        // 如果存在，说明原来下载过，不过可能没有下载完
        if (file.exists()) {
            // 如果文件存在，就获取当前文件的大小
            alreadySize = file.length();
        }
        /**
         * Range头域可以请求实体的一个或者多个子范围。 例如: 表示头500个字节：bytes=0-499
         * 表示第二个500字节：bytes=500-999 表示最后500个字节：bytes=-500
         * 表示500字节以后的范围：bytes=500- 第一个和最后一个字节：bytes=0-0,-1
         * 同时指定几个范围：bytes=500-600,601-999
         * 但是服务器可以忽略此请求头，如果无条件GET包含Range请求头，响应会以状态码206（PartialContent）返回而不是以200
         * （OK）。
         */
        conn.addRequestProperty("range", "bytes=" + alreadySize + "-");
        conn.connect();

        // 206,一般表示断点续传
        // 获取服务器回馈的状态码
        int code = conn.getResponseCode();
        // 如果响应成功，因为使用了range请求头，那么响应成功的状态码为206，而不是200
        if (code == 206) {
            // 获取未下载的文件的大小
            // 本方法用来获取响应正文的大小，但因为设置了range请求头，那么这个方法返回的就是剩余的大小
            long unfinishedSize = conn.getContentLength();
            // 文件的大小
            long size = alreadySize + unfinishedSize;

            // 获取输入流
            InputStream in = conn.getInputStream();
            // 获取输出对象,参数一：目标文件，参数2表示在原来的文件中追加
            OutputStream out = new BufferedOutputStream(new FileOutputStream(file, true));

            // 开始下载
            byte[] buff = new byte[2048];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
                // 将下载的累加到alreadSize中
                alreadySize += len;
                // 下载进度
                System.out.printf("%.2f%%\n", alreadySize * 1.0 / size * 100);
                // 由于文件大小可以看得到，那么我们这里使用阻塞
                //Thread.sleep(2);
                //break;
            }
            out.close();
            System.out.println("下载完成！！！");
        } else {
            System.out.println("下载失败！！！");
        }

        // 断开连接
        conn.disconnect();
    }

}
