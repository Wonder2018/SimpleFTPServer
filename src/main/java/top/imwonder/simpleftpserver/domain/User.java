package top.imwonder.simpleftpserver.domain;

import java.net.Socket;

import lombok.Data;

@Data
public class User {
    private String uuid;
    private String username;
    private String passwd;

    private String rootDir;
    private String currentDir;

    private Socket ctrlSocket;
    private Socket dataSocket;
    private String ip;
    private int port;
    
    private FTPOption type; // 文件类型(ascII 或 bin)
    private String reply; // 返回报告
    private FTPState state; // 用户状态标识符,在checkPASS中设置

    private boolean finished;

    public User(String uuid, Socket ctrlSocket) {
        this.uuid = uuid;
        this.ctrlSocket = ctrlSocket;
    }
}