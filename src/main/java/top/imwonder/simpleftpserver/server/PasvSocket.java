package top.imwonder.simpleftpserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasvSocket extends Thread {
    private  ArrayList<Socket> sockets = new ArrayList<Socket>();

    @Getter
    private int port;
    private ServerSocket ss;
    private String hostAddr;
    @Setter
    private boolean runing;

    public PasvSocket(String hostAddr,int port) {
        this.port = port;
        this.hostAddr = hostAddr;
        runing = true;
    }

    public ArrayList<Socket> getDataSocket(String hostAddr) {
        if(this.hostAddr.equals(hostAddr)){
            return sockets;
        }
        return null;
    }

    public void closeDataSocket() throws IOException {
        for (Socket item : sockets) {
            item.close();
        }
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(port);
            for (; runing;) {
                Socket dataSocket = ss.accept();
                String hostAddr = dataSocket.getInetAddress().getHostAddress();
                if(this.hostAddr.equals(hostAddr)){
                    sockets.add(dataSocket);
                    log.info("new PasvDataSocket connected IP: {}", hostAddr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}