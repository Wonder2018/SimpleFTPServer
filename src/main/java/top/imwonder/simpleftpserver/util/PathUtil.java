package top.imwonder.simpleftpserver.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathUtil {
    public static String generateFileList(File dir) throws Exception {
        Date time;
        File[] fileList = dir.listFiles();
        StringBuffer listBuffer = new StringBuffer();
        for (File item : fileList) {
            time = new Date(item.lastModified());
            listBuffer.append(item.isDirectory() ? "d" : "-");
            String perm = item.canRead() ? "r" : "-";
            long size = 0;
            perm += item.canWrite() ? "w-" : "--";
            listBuffer.append(perm);
            listBuffer.append(perm);
            listBuffer.append(perm);
            if (item.isDirectory()) {
                log.info("{}",item);
                listBuffer.append("\t");
                listBuffer.append(0);
            } else {
                size = item.length();
                listBuffer.append("\t0");
            }
            listBuffer.append("\tftp\tftp\t");
            listBuffer.append(size);
            listBuffer.append(String.format(new Locale("en"), "\t%tb %te %tY\t", time, time, time));
            listBuffer.append(item.getName());
            listBuffer.append("\r\n");
        }
        return listBuffer.toString();
    }

    public static String getTargetPath(String parent, String child) throws IllegalArgumentException, IOException {
        if (parent == null || parent.isEmpty()) {
            throw new IllegalArgumentException("The argument 'parent' can not be Empty!");
        }
        if (child == null || child.isEmpty()) {
            throw new IllegalArgumentException("The argument 'child' can not be Empty!");
        }
        return new File(parent, child).getCanonicalPath();
    }

    public static String getPathRequested(String rootDir, String currentDir, String path)
            throws IllegalArgumentException, IOException {
        if (path.startsWith("/")) {
            path = getTargetPath(rootDir, path);
        } else {
            path = getTargetPath(currentDir, path);
        }
        if (!path.startsWith(rootDir)) {
            path = rootDir;
        }
        return path;
    }
}