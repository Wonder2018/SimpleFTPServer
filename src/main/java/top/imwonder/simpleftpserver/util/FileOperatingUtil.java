/*
 * @Author: Wonder2019 
 * @Date: 2019-11-14 17:29:01 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2019-11-14 17:29:01 
 */
package top.imwonder.simpleftpserver.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileOperatingUtil {
    public static void transferBinaryFile(InputStream in, OutputStream out) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bof = new BufferedOutputStream(out);
        byte[] buf = new byte[1024];
        for (int len = bis.read(buf); len != -1; len = bis.read(buf)) {
            bof.write(buf, 0, len);
        }
        bis.close();
        bof.close();
    }

    public static void transferAsciiFile(InputStream in, OutputStream out) throws IOException {
        transferAsciiFile(in, out, false);
    }

    public static void transferAsciiFile(InputStream in, OutputStream out, boolean autoflush) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pw = new PrintWriter(out, autoflush);
        String s;
        while ((s = br.readLine()) != null) {
            pw.println(s);
        }
        br.close();
        pw.close();
    }
}