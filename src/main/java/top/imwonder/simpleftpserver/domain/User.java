package top.imwonder.simpleftpserver.domain;

import java.net.Socket;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends UserTemplate{

    // 用户信息
    private String uuid;
    private String username;
    private String passwd;

    // 数据连接
    private Socket ctrlSocket;
    private Socket dataSocket;
    private String ip;
    private int port;

    // 用户设置
    private String rootDir;
    private String currentDir;    
    private FTPOption type; // 文件类型(ascii 或 bin)
    private FTPOption mode; // 传输模式( 流 块 压缩 )

    // 状态
    private String reply; // 返回报告
    private FTPState state; // 用户状态标识符,在checkPASS中设置
    private boolean finished;

    public User(String uuid, Socket ctrlSocket) {
        this.uuid = uuid;
        this.ctrlSocket = ctrlSocket;
    }
}