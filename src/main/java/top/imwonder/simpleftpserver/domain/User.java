package top.imwonder.simpleftpserver.domain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class User extends UserTemplate {

    // 用户信息
    private String uuid;
    private String username;
    private String passwd;

    // 数据连接
    private Socket ctrlSocket;
    private Socket dataSocket;
    private ServerSocket pasvServer;
    private String ip;
    private int port;
    private int pasvPort;

    // 用户设置
    private String rootDir;
    private String currentDir;
    private FTPOption type = FTPOption.FTYPE_BIN; // 文件类型(ascii 或 bin)
    private FTPOption mode; // 传输模式( 流 块 压缩 )

    // 状态
    private FTPOption transferMode;
    private Long skip;// 跳过字节数
    private String reply; // 返回报告
    private FTPState state; // 用户状态标识符,在checkPASS中设置
    private boolean finished;

    private final InetSocketAddress isa;

    public User(String uuid, Socket ctrlSocket) {
        this.uuid = uuid;
        this.ctrlSocket = ctrlSocket;
        this.transferMode = FTPOption.FTRANS_PORT;
        this.isa = new InetSocketAddress(ctrlSocket.getLocalAddress(), 20);
    }

    public Socket connect() throws IOException {
        if(transferMode == FTPOption.FTRANS_PASV){
            // for(;;){
                dataSocket = pasvServer.accept();
                log.info(dataSocket.getInetAddress().getHostAddress());
                // if(dataSocket.getInetAddress().getHostAddress().equals(ip)){
                    return dataSocket;
                // }
            // }
        }
        this.dataSocket = new Socket();
        dataSocket.setReuseAddress(true);
        dataSocket.bind(isa);
        dataSocket.connect(new InetSocketAddress(ip, port));
        return dataSocket;
    }

    public void closeDataSocket() throws IOException {
        dataSocket.close();
    }
    
    public void closePasvServer() throws IOException {
        pasvServer.close();
        pasvServer = null;
    }

    public int initPasv() throws IOException {
        transferMode = FTPOption.FTRANS_PASV;
        if(pasvServer != null){
            return pasvPort;
        }
        pasvPort = (int) (Math.random() * 64500 + 1024);
        pasvServer = new ServerSocket(pasvPort);
        return pasvPort;
    }

    public void initPort(String ip, int port){
        transferMode=FTPOption.FTRANS_PORT;
        this.ip = ip;
        this.port = port;
    }
}