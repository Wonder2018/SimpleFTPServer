package top.imwonder.simpleftpserver.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.Exception.IllegalFTPCommandException;
import top.imwonder.simpleftpserver.Exception.IllegalFTPState;
import top.imwonder.simpleftpserver.Exception.IllegalUserState;
import top.imwonder.simpleftpserver.Server.ServerCore;
import top.imwonder.simpleftpserver.util.FileOperatingUtil;
import top.imwonder.simpleftpserver.util.PathUtil;

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

    public void commandCWD(User user, Command cmd) throws IllegalFTPCommandException, IOException {
        String path = cmd.getParam();
        if (path != null && !path.isEmpty()) {
            try {
                path = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), path);
            } catch (IOException e) {
                throw new IllegalFTPCommandException("550 This path does not exist!");
            } catch (IllegalArgumentException e) {
                throw new IOException("500 Server Error");
            }
            File newDir = new File(path);
            if (newDir.exists()) {
                user.setCurrentDir(path);
                user.setReply("250 directory change to: " + path.replace(user.getRootDir(), "/") + "!\r");
            } else {
                throw new IllegalFTPCommandException("550 This path does not exist!");
            }
        } else {
            user.setReply("212 The directory has not changed!\r");
        }
        
    }

    public void commandDELE(User user, Command cmd) throws IllegalArgumentException, IOException {
        String targetPath = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
        if (!targetPath.startsWith(user.getRootDir())) {
            throw new IOException("450 Permission denied!");
        }
        File targetFile = new File(targetPath);
        if(targetFile.exists()){
            targetFile.delete();
            user.setReply("250 File "+targetPath.substring(targetPath.lastIndexOf("/")+1)+" is deleted!\r");
            return;
        }
        user.setReply("550 File "+targetPath.substring(targetPath.lastIndexOf("/")+1)+" is not exists!\r");
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
            Socket dataSocket = new Socket(user.getIp(), user.getPort(), user.getCtrlSocket().getLocalAddress(),(int) (Math.random() * 64000 + 1024));
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream());
            PrintWriter dout = new PrintWriter(dataSocket.getOutputStream(), true);
            ctrlOutput.println("150 print file list...\r");
            ctrlOutput.flush();
            if (path == null || path.isEmpty() || path.equals("-a")) {
                dout.print(PathUtil.generateFileList(new File(user.getCurrentDir())));
            } else {
                try {
                    path = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), path);
                } catch (IOException e) {
                    dout.close();
                    dataSocket.close();
                    throw new IllegalFTPCommandException("550 This path does not exist!");
                } catch (IllegalArgumentException e) {
                    dout.close();
                    dataSocket.close();
                    throw new IOException("500 Server Error");
                }
                File newDir = new File(path);
                if (newDir.exists()) {
                    dout.print(PathUtil.generateFileList(newDir));
                }
            }
            dout.close();
            dataSocket.close();
            
            user.setReply("226 print successfully\r");
        } catch (IOException e) {
            throw new IOException("451 Requested action aborted: local error in processing");
        } catch (Exception e) {
            throw new IOException("500 FileRead Error!");
        }
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

    public void commandMODE(User user, Command cmd) throws IllegalFTPCommandException {
        String mode = cmd.getParam();
        if (mode == null || mode.isEmpty()) {
            throw new IllegalFTPCommandException("501 The Command MODE need a useable Parament!");
        }
        switch (mode) {
        case "S":
            user.setMode(FTPOption.FMODE_STREAM);
            user.setReply("200 the mode Change to Stream!\r");
            
            break;
        case "C":
            user.setMode(FTPOption.FMODE_COMPRESSED);
            user.setReply("200 the mode change to compressed!\r");
            
            break;
        case "B":
            user.setMode(FTPOption.FMODE_BLOCK);
            user.setReply("200 the mod change to block!\r");
            
            break;
        default:
            throw new IllegalFTPCommandException("501 The Command MODE need a useable Parament!");
        }

    }

    public void commandNLST(User user, Command cmd) throws IOException {
        commandLIST(user, cmd);
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
                
                log.info("User {} has just successfully logged in", username);
            } else {
                throw new IllegalUserState("430 This Server don't allow anonymous, please login!");
            }
        } else {
            if (ServerCore.checkPASS(user, passwd)) {
                user.setReply("230 User " + username + " successfully logged in!\r");
                user.setState(FTPState.FS_LOGIN);
                log.info("User {} has just successfully logged in", username);
                
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
            throw new IllegalFTPCommandException("501 The Command PORT need a useable Parament!");
        }
        String[] ip = tcpPort.split(",");
        try {
            for (String item : ip) {
                Integer.valueOf(item);
            }
        } catch (NumberFormatException e) {
            throw new IllegalFTPCommandException("501 The Command PORT need a useable Parament!");
        }
        user.setIp(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
        user.setPort(Integer.valueOf(ip[4]) * 256 + Integer.valueOf(ip[5]));
        user.setReply("200 The port is set successfully.\r");
        
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

    public void commandRETR(User user, Command cmd) throws IOException, IllegalFTPState {
        String requestPath = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
        File requestFile = new File(requestPath);
        if (requestFile.isDirectory() || !requestFile.exists()) {
            throw new IOException("550 The File requested is not exists!");
        }
        try {
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream(), true);
            Socket dataSocket = new Socket(user.getIp(), user.getPort(), user.getCtrlSocket().getLocalAddress(), (int) (Math.random() * 64000 + 1024));
            switch (user.getType()) {
            case FTYPE_BIN:
                ctrlOutput.println("150 Transfer Binary file: " + requestPath.substring(requestPath.lastIndexOf("/")+1) + "\r");
                FileOperatingUtil.transferBinaryFile(new FileInputStream(requestFile), dataSocket.getOutputStream());
                dataSocket.close();
                user.setReply("226 File transfered successfulli!\r");
                break;
            case FTYPE_ASCII:
                ctrlOutput.println("150 Transfer Ascii file: " + requestPath.substring(requestPath.lastIndexOf("/")+1) + "\r");
                FileOperatingUtil.transferAsciiFile(new FileInputStream(requestFile), dataSocket.getOutputStream(),true);
                dataSocket.close();
                user.setReply("226 File transfered successfulli!\r");
                break;
            default:
                throw new IllegalFTPState("501 Illegal File TYPE \""+user.getType()+"\"!");
            }
        } catch (IOException e) {
            throw new IOException("451 Transfer Error!");
        }
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

    public void commandSTOR(User user, Command cmd) throws IllegalArgumentException, IOException, IllegalFTPState {
        String storePath = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
        if (!storePath.startsWith(user.getRootDir())) {
            throw new IOException("450 Permission denied!");
        }
        File storeFile = new File(storePath);
        try {
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream(), true);
            Socket dataSocket = new Socket(user.getIp(), user.getPort(), user.getCtrlSocket().getLocalAddress(), (int) (Math.random() * 64000 + 1024));
            switch (user.getType()) {
            case FTYPE_BIN:
                ctrlOutput.println("150 Transfer Binary file: " + storePath.substring(storePath.lastIndexOf("/")+1) + "\r");
                FileOperatingUtil.transferBinaryFile(dataSocket.getInputStream(), new FileOutputStream(storeFile));
                dataSocket.close();
                user.setReply("226 File transfered successfulli!\r");
                break;
            case FTYPE_ASCII:
                ctrlOutput.println("150 Transfer Ascii file: " + storePath.substring(storePath.lastIndexOf("/")+1) + "\r");
                FileOperatingUtil.transferAsciiFile(dataSocket.getInputStream(), new FileOutputStream(storeFile));
                dataSocket.close();
                user.setReply("226 File transfered successfulli!\r");
                break;
            default:
                throw new IllegalFTPState("501 Illegal File TYPE \""+user.getType()+"\"!");
            }
        } catch (IOException e) {
            throw new IOException("451 Transfer Error!");
        }
    }

    public void commandSTOU(User user, Command cmd) {
    }

    public void commandSTRU(User user, Command cmd) {
    }

    public void commandSYST(User user, Command cmd) {
        user.setReply("215 Unix Type: L8 - Simple FTP Server\r");
        
    }

    public void commandTHMB(User user, Command cmd) {
    }

    public void commandTYPE(User user, Command cmd) throws IllegalFTPCommandException {
        String type = cmd.getParam();
        if (type == null || type.isEmpty()) {
            throw new IllegalFTPCommandException("501 The Command TYPE need a useable Parament!");
        }
        switch (type) {
        case "A":
            user.setType(FTPOption.FTYPE_ASCII);
            user.setReply("200 the type Change to ASCII!\r");
            
            break;
        case "I":
            user.setType(FTPOption.FTYPE_BIN);
            user.setReply("200 the type change to BINARY!\r");
            
            break;
        default:
            throw new IllegalFTPCommandException("501 The Command TYPE need a useable Parament!");
        }
    }

    public void commandUSER(User user, Command cmd) {
        String username = cmd.getParam();
        user.setReply("331 User name okay, need password.\r");
        user.setState(FTPState.FS_WAIT_PASS);
        
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