package top.imwonder.simpleftpserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.domain.Command;
import top.imwonder.simpleftpserver.domain.FTPState;
import top.imwonder.simpleftpserver.domain.User;
import top.imwonder.simpleftpserver.exception.IllegalFTPCommandException;
import top.imwonder.simpleftpserver.exception.IllegalUserState;
import top.imwonder.simpleftpserver.util.CommandAnalyze;

/*
 * @Author: Wonder2019 
 * @Date: 2019-10-27 11:28:51 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2019-10-28 21:42:25
 */
@Slf4j
public class SingleUserThread extends Thread {
    private String id;
    private User user;

    public SingleUserThread(User user) {
        this.user = user;
        this.id = user.getUuid();
        user.setFinished(false);
        user.setState(FTPState.FS_WAIT_LOGIN);
    }

    @Override
    public void run() {
        String fullcmd;
        boolean finished = user.isFinished();
        Socket ctrlSocket = user.getCtrlSocket();
        try (BufferedReader ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
                PrintWriter ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream(), true);) {

            while (!finished) {
                fullcmd = ctrlInput.readLine();
                log.debug(fullcmd);
                if (fullcmd == null)
                    finished = true; // 跳出while
                else {
                    try {
                        Command cmd = CommandAnalyze.analize(fullcmd);
                        cmd.setUuid(id);
                        CommandAnalyze.execute(user, cmd);
                    } catch (IllegalFTPCommandException e) {
                        log.debug("Unknow FTP Command \"{}\" !!", e.getMessage());
                        user.setReply("500 Unknow FTP Command '" + e.getMessage() + "'!");
                        user.setFinished(false);
                    }
                    finished = user.isFinished();
                }
                ctrlOutput.println(user.getReply() + "\r");
                ctrlOutput.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalUserState e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }

}