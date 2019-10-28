package top.imwonder.simpleftpserver.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class PathUtil {
    public static String generateFileList(File dir) throws Exception {
        Date time;
        File[] fileList = dir.listFiles();
        String listBuffer = "";
        for (File item : fileList) {
            time = new Date(item.lastModified());
            listBuffer += item.isDirectory() ? "d" : "-";
            String perm = item.canRead() ? "r" : "-";
            long size = 0;
            perm += item.canWrite() ? "w-" : "--";
            listBuffer += perm;
            listBuffer += perm;
            listBuffer += perm;
            if (item.isDirectory()) {
                listBuffer += " ";
                listBuffer += item.list().length;
            } else {
                size = item.length();
                listBuffer += " 0";
            }
            listBuffer += " ftp ftp ";
            listBuffer += size;
            listBuffer += String.format(new Locale("en"), " %tb %te %tY ", time, time, time);
            listBuffer += item.getName();
            listBuffer += "\r\n";
        }
        return listBuffer.toString();
    }

    public static String getTargetPath(String parent, String child) throws IllegalArgumentException,IOException {
        if(parent == null||parent.isEmpty()){
            throw new IllegalArgumentException("The argument 'parent' can not be Empty!");
        }
        if(child == null || child.isEmpty()){
            throw new IllegalArgumentException("The argument 'child' can not be Empty!");
        }
        return new File( parent,child).getCanonicalPath();
    }

    public static String getPathRequested(String rootDir,String currentDir,String path) throws IllegalArgumentException, IOException {
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