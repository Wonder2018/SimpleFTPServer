package top.imwonder.simpleftpserver;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import top.imwonder.simpleftpserver.util.PathUtil;

public class PathTest {
    @Test
    public void testPath() {
        String parent = "/home/wonder2019/fdfcf/";
        String child = "../as/../fcgcg/./hvuh/vhvh/../gg/../..";
        try{
            System.out.println(new File( parent , child).getCanonicalPath());
            PathUtil.getTargetPath("", "");
        }catch(IllegalArgumentException e){
            System.out.println("捕获到了非法参数异常！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
