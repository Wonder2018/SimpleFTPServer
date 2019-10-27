package top.imwonder.simpleftpserver.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.ServePoint;
import top.imwonder.simpleftpserver.domain.User;
import top.imwonder.simpleftpserver.util.IdGen;

@Slf4j
public class ServerCore extends Thread {
    private Map<String,SingleUserThread> users = new HashMap<String,SingleUserThread>();

    public void run() {
        try {
            ServerSocket s = new ServerSocket(21);

            // 接受客户端请求
            Socket incoming = s.accept();
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);// 文本文本输出流
            out.println("220 Wellcome to Simple FTP Server Powered with ❤ by www.imwonder.top\r");
            // out.println("220     ┌─┐ ┌─┐ ┌─┐               ┌─┐          ┌───────┬───────┐ ┌─┐ ┌─────┐");
            // out.println("    │ │ │ │ │ │               │ │          │ ┌───┐ │ ┌───┐ ├─┘ │ │ ┌─┐ │");
            // out.println("    │ │ │ │ │ ├─────┬────┐┌───┘ ├─────┬───┐└─┘ ┌─┘ │ │ │ │ ├─┐ │ │ └─┘ │");
            // out.println("    │ └─┘ └─┘ │ ┌─┐ │ ┌─┐└┤ ┌─┐ │ │───┤ ┌─┘┌───┘ ┌─┤ │ │ │ │ │ │ └───┐ │");
            // out.println("    └─┐ ┌─┐ ┌─┤ └─┘ │ │ │ │ └─┘ │ │───┤ │  │ │ └───┤ └───┘ ├─┘ └─┬───┘ │");
            // out.println("      └─┘ └─┘ └─────┴─┘ └─┴─────┴─────┴─┘  └───────┴───────┴─────┴─────┘");
            // out.println("    Powered with ❤ by www.imwonder.top\r");// 命令正确的提示
            // 创建服务线程
            User user = new User(IdGen.uuid(),incoming);
            user.setRootDir("/home/wonder2019/");
            user.setCurrentDir(user.getRootDir());
            SingleUserThread sut = new SingleUserThread(user);
            // ServePoint sut = new ServePoint(incoming, 12);
            sut.start();
            users.put(user.getUuid(),sut); // 将此用户线程加入到这个 ArrayList 中
            log.info("A user connected !");
        } catch (IOException e) {
        }
    }

    public ServerCore() {
    }

	public static boolean allowedAnonymous() {
		return true;
	}

	public static boolean checkPASS(User user, String[] param) {
		return true;
	}
}