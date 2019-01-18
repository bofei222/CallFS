package cn.com.nei;

import cn.nei.tos3.config.StorageConfig;
import cn.nei.tos3.sf.StorageFile;
import cn.nei.tos3.sf.ToS3;
import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.IntHolder;

/**
 * @Author bofei
 * @Date 2019/1/18 17:39
 * @Description
 */
public class Main10 {
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
