package top.imwonder.simpleftpserver.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.server.ServerCore;
import top.imwonder.simpleftpserver.util.FileOperatingUtil;
import top.imwonder.simpleftpserver.util.PathUtil;

@Slf4j
public abstract class UserTemplate {

    public void commandABOR(User user) {
        try {
            user.closeDataSocket();
        } catch (IOException e) {
            user.setReply("226 Close Connection With Error!");
            return;
        }
        user.setReply("226 Connection Close Successfully!");
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

    public void commandCDUP(User user) {
        String path = "./..";
        try {
            path = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), path);
        } catch (IOException e) {
            user.setReply("550 Permission denied!");
            return;
        } catch (IllegalArgumentException e) {
            user.setReply("510 Server Error!");
            return;
        }
        File newDir = new File(path);
        if (newDir.exists()) {
            user.setCurrentDir(path);
            user.setReply("250 directory change to: " + path.replace(user.getRootDir(), "/") + "!");
        } else {
            user.setReply("550 This path does not exist!");
            return;
        }
    }

    public void commandCONF(User user, Command cmd) {
    }

    public void commandCSID(User user, Command cmd) {
    }

    public void commandCWD(User user, Command cmd) {
        String path = cmd.getParam();
        if (path != null && !path.isEmpty()) {
            try {
                path = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), path);
            } catch (IOException e) {
                user.setReply("550 Permission denied!");
                return;
            } catch (IllegalArgumentException e) {
                user.setReply("510 Server Error!");
                return;
            }
            File newDir = new File(path);
            if (newDir.exists()) {
                user.setCurrentDir(path);
                user.setReply("250 directory change to: " + path.replace(user.getRootDir(), "/") + "!");
            } else {
                user.setReply("550 This path does not exist!");
                return;
            }
        } else {
            user.setReply("212 The directory has not changed!");
        }

    }

    public void commandDELE(User user, Command cmd) {
        String targetPath;
        try {
            targetPath = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
        } catch (IOException e) {
            user.setReply("450 Permission denied!");
            return;
        } catch (IllegalArgumentException e) {
            user.setReply("510 Server Error!");
            return;
        }
        if (!targetPath.startsWith(user.getRootDir())) {
            user.setReply("550 Permission denied!");
            return;
        }
        File targetFile = new File(targetPath);
        if (targetFile.exists()) {
            targetFile.delete();
            user.setReply("250 File " + targetPath.substring(targetPath.lastIndexOf("/") + 1) + " is deleted!");
            return;
        }
        user.setReply("550 File " + targetPath.substring(targetPath.lastIndexOf("/") + 1) + " is not exists!");
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
        StringBuffer featList = new StringBuffer("211-This Server feat command\n");
        featList.append(" ABOR\n");
        featList.append(" CDUP\n");
        featList.append(" CWD\n");
        featList.append(" DELE\n");
        featList.append(" FEAT\n");
        featList.append(" LIST\n");
        featList.append(" MKD\n");
        featList.append(" MODE\n");
        featList.append(" NLST\n");
        featList.append(" NOOP\n");
        featList.append(" OPTS\n");
        featList.append(" PASS\n");
        featList.append(" PASV\n");
        featList.append(" PORT\n");
        featList.append(" PWD\n");
        featList.append(" QUIT\n");
        featList.append(" REST\n");
        featList.append(" RETR\n");
        featList.append(" STOR\n");
        featList.append(" SYST\n");
        featList.append(" TYPE\n");
        featList.append(" USER\n");
        featList.append(" UTF8\n");
        featList.append("211 Feat Over!");
        user.setReply(featList.toString());
    }

    public void commandHELP(User user, Command cmd) {
    }

    public void commandHOST(User user, Command cmd) {
    }

    public void commandLANG(User user, Command cmd) {
    }

    public void commandLIST(User user, Command cmd) {
        String path = cmd.getParam();
        try {
            Socket dataSocket = user.connect();
            log.info("get socket!");
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream(), true);
            PrintWriter dout = new PrintWriter(dataSocket.getOutputStream(), true);
            ctrlOutput.println("150 print file list...\r");
            if (path == null || path.isEmpty() || path.equals("-a")) {
                dout.print(PathUtil.generateFileList(new File(user.getCurrentDir())));
            } else {
                try {
                    path = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), path);
                } catch (IOException e) {
                    dout.close();
                    user.closeDataSocket();
                    user.setReply("550 Permission denied!");
                    return;
                } catch (IllegalArgumentException e) {
                    dout.close();
                    user.closeDataSocket();
                    user.setReply("510 Server Error!");
                    return;
                }
                File newDir = new File(path);
                if (newDir.exists()) {
                    dout.print(PathUtil.generateFileList(newDir));
                }
            }
            dout.close();
            user.closeDataSocket();
            user.setReply("226 print successfully");
        } catch (IOException e) {
            user.setReply("425 Requested action aborted: local error in processing!");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            user.setReply("500 File Read Error!");
            return;
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
        try {
            String path = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
            if (path.equals(user.getRootDir())) {
                user.setReply("550 Permission denied!");
                return;
            }
            File dir = new File(path);
            if (dir.exists()) {
                user.setReply("550 The directory is already exists!");
                return;
            } else {
                dir.mkdirs();
                user.setReply("250 mkdir successfully!");
            }
        } catch (IOException e) {
            user.setReply("550 Permission denied!");
            return;
        } catch (IllegalArgumentException e) {
            user.setReply("510 Server Error!");
            return;
        }
    }

    public void commandMLSD(User user, Command cmd) {
    }

    public void commandMLST(User user, Command cmd) {
    }

    public void commandMODE(User user, Command cmd) {
        String mode = cmd.getParam();
        if (mode == null || mode.isEmpty()) {
            user.setReply("501 The Command \"MODE\" need a useable Parament! ( \"S\", \"C\", \"B\" )");
            return;
        }
        switch (mode) {
        case "S":
            user.setMode(FTPOption.FMODE_STREAM);
            user.setReply("200 the mode Change to Stream!");
            break;
        case "C":
            user.setMode(FTPOption.FMODE_COMPRESSED);
            user.setReply("200 the mode change to compressed!");
            break;
        case "B":
            user.setMode(FTPOption.FMODE_BLOCK);
            user.setReply("200 the mod change to block!");
            break;
        default:
            user.setReply("501 The Command \"MODE\" need a useable Parament! ( \"S\", \"C\", \"B\" )");
            return;
        }

    }

    public void commandNLST(User user, Command cmd) {
        commandLIST(user, cmd);
    }

    public void commandNOOP(User user) {
        user.setReply("200 Keep alive...");
    }

    public void commandOPTS(User user, Command cmd) {
        if(cmd.getParam().startsWith("UTF8 ON")){
            user.setReply("200 Use UTF8 only!");
        }
        user.setReply("500 unknown options!");
    }

    public void commandPASS(User user, Command cmd) {
        String passwd = cmd.getParam();
        String username = user.getUsername();
        if (username == null || username.equals("")) {
            user.setReply("503 Can't get the Username!");
            return;
        } else {
            if (ServerCore.checkPASS(user, passwd)) {
                user.setReply("230 User " + username + " successfully logged in!");
                user.setState(FTPState.FS_LOGIN);
                log.info("User {} has just successfully logged in", username);
            } else {
                user.setReply("530 User " + username + " password is incorrect");
                user.setFinished(true);
            }
        }
    }

    public void commandPASV(User user) {
        int port;
        try {
            port = user.initPasv();
        } catch (IOException e) {
            e.printStackTrace();
            user.setReply("425 Cannot open passive connection.");
            return;
        }
        log.info("pasv port --:{}", port);
        String ip = user.getCtrlSocket().getLocalAddress().getHostAddress().replace(".", ",");
        user.setReply("227 Entering passive mode (" + ip + "," + port / 256 + "," + port % 256 + ")");
    }

    public void commandPBSZ(User user, Command cmd) {
    }

    public void commandPORT(User user, Command cmd) {
        String tcpPort = cmd.getParam();
        if (tcpPort == null || tcpPort.isEmpty()) {
            user.setReply("501 The Command \"PORT\" need a useable Parament! ( \"Number\", \"Number\", \"Number\", \"Number\", \"Number\" )");
            return;
        }
        String[] params = tcpPort.split(",");
        try {
            for (String item : params) {
                Integer.valueOf(item);
            }
        } catch (NumberFormatException e) {
            user.setReply("501 The Command \"PORT\" need a useable Parament! ( \"Number\", \"Number\", \"Number\", \"Number\", \"Number\" )");
            return;
        }
        String ip = params[0] + "." + params[1] + "." + params[2] + "." + params[3];
        int port = Integer.valueOf(params[4]) * 256 + Integer.valueOf(params[5]);
        user.initPort(ip, port);
        user.setReply("200 The port is set successfully.");

    }

    public void commandPROT(User user, Command cmd) {
    }

    public void commandPWD(User user) {
        user.setReply("257 \"" + user.getCurrentDir().replace(user.getRootDir(), "/") + "\" is current directory.");
    }

    public void commandQUIT(User user) {
        user.setReply("200 Bye!");
        user.setFinished(true);
    }

    public void commandREIN(User user, Command cmd) {
    }

    public void commandREST(User user, Command cmd) {
        Long skip;
        try {
            skip = Long.valueOf(cmd.getParam());
        } catch (NumberFormatException e) {
            user.setReply("501 The Command \"REST\" need a useable Parament! ( Number )");
            return;
        }
        user.setSkip(skip);
        user.setReply("350 Restarting at " + skip + ". Send \"STORE\" or \"RETR\" to initiate transfer.");
    }

    public void commandRETR(User user, Command cmd) {
        String requestPath;
        try {
            requestPath = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
        } catch (IOException e) {
            user.setReply("550 Permission denied!");
            return;
        } catch (IllegalArgumentException e) {
            user.setReply("510 Server Error!");
            return;
        }
        File requestFile = new File(requestPath);
        if (requestFile.isDirectory() || !requestFile.exists()) {
            user.setReply("550 The File requested is not exists!");
            return;
        }
        String replyPath = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        try {
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream(), true);
            Socket dataSocket = user.connect();
            Long skip = user.getSkip();
            switch (user.getType()) {
            case FTYPE_BIN:
                ctrlOutput.println("150 Transfer Binary file: " + replyPath + "\r");
                FileInputStream fis = new FileInputStream(requestFile);
                if (skip != null && skip > 0) {
                    long skiped = fis.skip(skip);
                    if (skiped != skip) {
                        user.setReply("551 Error on input file.Should skip " + skip + " bytes but +" + skiped + "!");
                        return;
                    }
                    user.setSkip(null);
                }
                FileOperatingUtil.transferBinaryFile(fis, dataSocket.getOutputStream());
                user.closeDataSocket();
                user.setReply("226 File transfered successfulli!");
                break;
            case FTYPE_ASCII:
                if (skip != null && skip > 0) {
                    user.setReply("551 Please use binary mode!");
                    return;
                }
                ctrlOutput.println("150 Transfer Ascii file: " + replyPath + "\r");
                FileOperatingUtil.transferAsciiFile(new FileInputStream(requestFile), dataSocket.getOutputStream(), true);
                user.closeDataSocket();
                user.setReply("226 File transfered successfulli!");
                break;
            default:
                user.setReply("501 Illegal File TYPE \"" + user.getType() + "\"!");
                return;
            }
        } catch (IOException e) {
            user.setReply("425 Cannot open the data connection!");
            return;
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

    public void commandSTOR(User user, Command cmd) {
        String storePath;
        try {
            storePath = PathUtil.getPathRequested(user.getRootDir(), user.getCurrentDir(), cmd.getParam());
        } catch (IllegalArgumentException e) {
            user.setReply("510 Server Error!");
            return;
        } catch (IOException e) {
            user.setReply("550 Permission denied!");
            return;
        }
        if (!storePath.startsWith(user.getRootDir())) {
            user.setReply("550 Permission denied!");
            return;
        }
        File storeFile = new File(storePath);
        try {
            PrintWriter ctrlOutput = new PrintWriter(user.getCtrlSocket().getOutputStream(), true);
            Socket dataSocket = user.connect();
            switch (user.getType()) {
            case FTYPE_BIN:
                ctrlOutput.println(
                        "150 Transfer Binary file: " + storePath.substring(storePath.lastIndexOf("/") + 1) + "\r");
                FileOperatingUtil.transferBinaryFile(dataSocket.getInputStream(), new FileOutputStream(storeFile));
                user.closeDataSocket();
                user.setReply("226 File transfered successfulli!");
                break;
            case FTYPE_ASCII:
                ctrlOutput.println(
                        "150 Transfer Ascii file: " + storePath.substring(storePath.lastIndexOf("/") + 1) + "\r");
                FileOperatingUtil.transferAsciiFile(dataSocket.getInputStream(), new FileOutputStream(storeFile));
                user.closeDataSocket();
                user.setReply("226 File transfered successfulli!");
                break;
            default:
                user.setReply("501 Illegal File TYPE \"" + user.getType() + "\"!");
                return;
            }
        } catch (IOException e) {
            user.setReply("451 Transfer Error!");
            return;
        }
    }

    public void commandSTOU(User user, Command cmd) {
    }

    public void commandSTRU(User user, Command cmd) {
    }

    public void commandSYST(User user) {
        user.setReply("215 Unix Type: Simple FTP Server");

    }

    public void commandTHMB(User user, Command cmd) {
    }

    public void commandTYPE(User user, Command cmd) {
        String type = cmd.getParam();
        if (type == null || type.isEmpty()) {
            user.setReply("501 The Command \"TYPE\" need a useable Parament! ( \"A\", \"I\" )");
            return;
        }
        switch (type) {
        case "A":
            user.setType(FTPOption.FTYPE_ASCII);
            user.setReply("200 the type Change to ASCII!");

            break;
        case "I":
            user.setType(FTPOption.FTYPE_BIN);
            user.setReply("200 the type change to BINARY!");

            break;
        default:
            user.setReply("501 The Command \"TYPE\" need a useable Parament! ( \"A\", \"I\" )");
            return;
        }
    }

    public void commandUSER(User user, Command cmd) {
        String username = cmd.getParam();
        user.setReply("331 User name okay, need password.");
        user.setState(FTPState.FS_WAIT_PASS);
        if (username == null || username.isEmpty() || username.equals("anonymous")) {
            if (ServerCore.allowedAnonymous()) {
                user.setReply("230 User anonymous successfully logged in!");
                user.setState(FTPState.FS_LOGIN);
                log.info("User {} has just successfully logged in", username);
            } else {
                user.setReply("430 This Server don't allow anonymous, please login!");
                return;
            }
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