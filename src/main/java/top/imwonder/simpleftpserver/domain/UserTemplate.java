package top.imwonder.simpleftpserver.domain;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.Exception.IllegalFTPCommandException;
import top.imwonder.simpleftpserver.Exception.IllegalUserState;
import top.imwonder.simpleftpserver.Server.ServerCore;
import top.imwonder.simpleftpserver.domain.Command;
import top.imwonder.simpleftpserver.domain.FTPOption;
import top.imwonder.simpleftpserver.domain.FTPState;
import top.imwonder.simpleftpserver.domain.User;

@Slf4j
public abstract class UserTemplate {

    public void commandABOR(User user, Command cmd) {
    }

    public void commandACCT(User user, Command cmd) {
    }

    public void commandADAT(User user, Command cmd) {
    }

    public void commandALLO(User user, Command cmd) {
    }

    public void commandAPPE(User user, Command cmd) {
    }

    public void commandAUTH(User user, Command cmd) {
    }

    public void commandAVBL(User user, Command cmd) {
    }

    public void commandCCC(User user, Command cmd) {
    }

    public void commandCDUP(User user, Command cmd) {
    }

    public void commandCONF(User user, Command cmd) {
    }

    public void commandCSID(User user, Command cmd) {
    }

    public void commandCWD(User user, Command cmd) {
        String path = cmd.getParam();
        if (path != null && !path.isEmpty()) {
            if (path.startsWith("/")) {
                path = user.getRootDir() + path.substring(1);
            } else {
                path = user.getCurrentDir() + path;
            }
            log.info("user want to cd :{}",path);
            File newDir = new File(path);
            if (newDir.exists()) {
                user.setCurrentDir(path);
                user.setReply("250 directory change to: " + path.replace(user.getRootDir(), "/") + "!\r");
            } else {
                user.setReply("550 This path does not exist!\r");
            }
        } else {
            user.setReply("212 The directory has not changed!\r");
        }
        user.setFinished(false);
    }

    public void commandDELE(User user, Command cmd) {
    }

    public void commandDSIZ(User user, Command cmd) {
    }

    public void commandENC(User user, Command cmd) {
    }

    public void commandEPRT(User user, Command cmd) {
    }

    public void commandEPSV(User user, Command cmd) {
    }

    public void commandFEAT(User user, Command cmd) {
    }

    public void commandHELP(User user, Command cmd) {
    }

    public void commandHOST(User user, Command cmd) {
    }

    public void commandLANG(User user, Command cmd) {
    }

    public void commandLIST(User user, Command cmd) throws IOException {
        String path = cmd.getParam();
        try {
            Socket dataSocket = new Socket(user.getIp(), user.getPort(), user.getCtrlSocket().getLocalAddress(), (int)(Math.random()*64000+1024));
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream());
            PrintWriter dout = new PrintWriter(dataSocket.getOutputStream(), true);
            ctrlOutput.println("150 print file list...\r");
            ctrlOutput.flush();
            if (path == null || path.isEmpty() || path.equals("-a")) {
                dout.print(generateFileList(new File(user.getCurrentDir())));
            } else {
                if (path.startsWith("/")) {
                    path = user.getRootDir() + path.substring(1);
                    File newDir = new File(path);
                    if (newDir.exists()) {
                        dout.print(generateFileList(newDir).getBytes());
                    }
                } else {
                    path = user.getCurrentDir() + path;
                    File newDir = new File(path);
                    if (newDir.exists()) {
                        dout.print(generateFileList(newDir));
                    }
                }
            }
            dout.close();
            dataSocket.close();
            user.setFinished(false);
            user.setReply("226 print successfully\r");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("a");
            throw new IOException("451 Requested action aborted: local error in processing");
        } catch (Exception e) {
            log.info("b");
            throw new IOException("500 FileRead Error!");
        }
    }

    private String generateFileList(File dir) throws Exception {
        Date time;
        File[] fileList = dir.listFiles();
        String listBuffer = "";
        for (File item : fileList) {
            time = new Date(item.lastModified());
            listBuffer+=item.isDirectory() ? "d" : "-";
            String perm = item.canRead() ? "r" : "-";
            long size = 0;
            perm += item.canWrite() ? "w-" : "--";
            listBuffer+=perm;
            listBuffer+=perm;
            listBuffer+=perm;
            if (item.isDirectory()) {
                listBuffer+=" ";
                listBuffer+=item.list().length;
            } else {
                size = item.length();
                listBuffer+=" 0";
            }
            listBuffer+=" ftp ftp ";
            listBuffer+=size;
            listBuffer+=String.format(new Locale("en")," %tb %te %tY ", time, time, time);
            listBuffer+=item.getName();
            listBuffer+="\r\n";
        }
        return listBuffer.toString();
    }

    public void commandLPRT(User user, Command cmd) {
    }

    public void commandLPSV(User user, Command cmd) {
    }

    public void commandMDTM(User user, Command cmd) {
    }

    public void commandMFCT(User user, Command cmd) {
    }

    public void commandMFF(User user, Command cmd) {
    }

    public void commandMFMT(User user, Command cmd) {
    }

    public void commandMIC(User user, Command cmd) {
    }

    public void commandMKD(User user, Command cmd) {
    }

    public void commandMLSD(User user, Command cmd) {
    }

    public void commandMLST(User user, Command cmd) {
    }

    public void commandMODE(User user, Command cmd) {
    }

    public void commandNLST(User user, Command cmd) {
    }

    public void commandNOOP(User user, Command cmd) {
    }

    public void commandOPTS(User user, Command cmd) {
    }

    public void commandPASS(User user, Command cmd) throws IllegalUserState {
        String passwd = cmd.getParam();
        String username = user.getUsername();
        if (username == null || username.equals("")) {
            throw new IllegalUserState("500 Can't get the Username!");
        } else if (username.equals("anonymous")) {
            if (ServerCore.allowedAnonymous()) {
                user.setReply("230 User " + username + " successfully logged in!\r");
                user.setState(FTPState.FS_LOGIN);
                user.setFinished(false);
                log.info("User {} has just successfully logged in", username);
            } else {
                throw new IllegalUserState("430 This Server don't allow anonymous, please login!");
            }
        } else {
            if (ServerCore.checkPASS(user, passwd)) {
                user.setReply("230 User " + username + " successfully logged in!\r");
                user.setState(FTPState.FS_LOGIN);
                log.info("User {} has just successfully logged in", username);
                user.setFinished(false);
            } else {
                user.setReply("530 User " + username + " password is incorrect\r");
                user.setFinished(true);
            }
        }
    }

    public void commandPASV(User user, Command cmd) {
    }

    public void commandPBSZ(User user, Command cmd) {
    }

    public void commandPORT(User user, Command cmd) throws IllegalFTPCommandException {
        String tcpPort = cmd.getParam();
        if (tcpPort == null || tcpPort.isEmpty()) {
            user.setFinished(false);
            throw new IllegalFTPCommandException("500 The Command PORT need a useable Parament!");
        }
        String[] ip = tcpPort.split(",");
        try {
            for (String item : ip) {
                Integer.valueOf(item);
            }
        } catch (NumberFormatException e) {
            user.setFinished(false);
            throw new IllegalFTPCommandException("500 The Command PORT need a useable Parament!");
        }
        user.setIp(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
        user.setPort(Integer.valueOf(ip[4]) * 256 + Integer.valueOf(ip[5]));
        user.setReply("200 The port is set successfully.\r");
        user.setFinished(false);
    }

    public void commandPROT(User user, Command cmd) {
    }

    public void commandPWD(User user, Command cmd) {
    }

    public void commandQUIT(User user, Command cmd) {
    }

    public void commandREIN(User user, Command cmd) {
    }

    public void commandREST(User user, Command cmd) {
    }

    public void commandRETR(User user, Command cmd) {
    }

    public void commandRMD(User user, Command cmd) {
    }

    public void commandRMDA(User user, Command cmd) {
    }

    public void commandRNFR(User user, Command cmd) {
    }

    public void commandRNTO(User user, Command cmd) {
    }

    public void commandSITE(User user, Command cmd) {
    }

    public void commandSIZE(User user, Command cmd) {
    }

    public void commandSMNT(User user, Command cmd) {
    }

    public void commandSPSV(User user, Command cmd) {
    }

    public void commandSTAT(User user, Command cmd) {
    }

    public void commandSTOR(User user, Command cmd) {
    }

    public void commandSTOU(User user, Command cmd) {
    }

    public void commandSTRU(User user, Command cmd) {
    }

    public void commandSYST(User user, Command cmd) {
        user.setReply("215 Unix Type: L8 - Simple FTP Server\r");
        user.setFinished(false);
    }

    public void commandTHMB(User user, Command cmd) {
    }

    public void commandTYPE(User user, Command cmd) throws IllegalFTPCommandException {
        String type = cmd.getParam();
        if (type == null || type.isEmpty()) {
            user.setFinished(false);
            throw new IllegalFTPCommandException("500 The Command TYPE need a useable Parament!");
        }
        if (type.equals("A")) {
            user.setType(FTPOption.FTYPE_ASCII);
            user.setReply("200 Change to ASCII mode!\r");
            user.setFinished(false);
        } else if (type.equals("I")) {
            user.setType(FTPOption.FTYPE_BIN);
            user.setReply("200 change to BINARY mode\r");
            user.setFinished(false);
        } else {
            user.setFinished(false);
            throw new IllegalFTPCommandException("500 The Command TYPE need a useable Parament!");
        }
    }

    public void commandUSER(User user, Command cmd) {
        String username = cmd.getParam();
        user.setReply("331 User name okay, need password.\r");
        user.setState(FTPState.FS_WAIT_PASS);
        user.setFinished(false);
        if (username == null || username.isEmpty()) {
            user.setUsername("anonymous");
        } else {
            user.setUsername(username);
        }
    }

    public void commandXCUP(User user, Command cmd) {
    }

    public void commandXMKD(User user, Command cmd) {
    }

    public void commandXPWD(User user, Command cmd) {
    }

    public void commandXRCP(User user, Command cmd) {
    }

    public void commandXRMD(User user, Command cmd) {
    }

    public void commandXRSQ(User user, Command cmd) {
    }

    public void commandXSEM(User user, Command cmd) {
    }

    public void commandXSEN(User user, Command cmd) {
    }

}