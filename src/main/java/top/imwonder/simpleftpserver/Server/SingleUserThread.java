package top.imwonder.simpleftpserver.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.Exception.IllegalFTPCommandException;
import top.imwonder.simpleftpserver.Exception.IllegalUserState;
import top.imwonder.simpleftpserver.domain.Command;
import top.imwonder.simpleftpserver.domain.FTPState;
import top.imwonder.simpleftpserver.domain.User;
import top.imwonder.simpleftpserver.util.CommandAnalyze;
import top.imwonder.simpleftpserver.util.UserOperating;

/*
 * @Author: Wonder2019 
 * @Date: 2019-10-27 11:28:51 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2019-10-27 12:03:01
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
        try {
            BufferedReader ctrlInput = new BufferedReader(new InputStreamReader(ctrlSocket.getInputStream()));
            PrintWriter ctrlOutput = new PrintWriter(ctrlSocket.getOutputStream(), true);
            while (!finished) {

            UserOperating uo = new UserOperating();
                fullcmd = ctrlInput.readLine();
                log.debug(fullcmd);
                if (fullcmd == null)
                    finished = true; // 跳出while
                else {
                    try {
                        Command cmd = CommandAnalyze.analize(fullcmd);
                        cmd.setUuid(id);
                        switch (user.getState()) {
                        case FS_WAIT_LOGIN:
                            uo.commandUSER(user, cmd);
                            break;
                        case FS_WAIT_PASS:
                            uo.commandPASS(user, cmd);
                            break;
                        case FS_LOGIN:
                            switch (cmd.getFc()) {
                            case ABOR:
                                uo.commandABOR(user, cmd);
                                break;
                            case ACCT:
                                uo.commandACCT(user, cmd);
                                break;
                            case ADAT:
                                uo.commandADAT(user, cmd);
                                break;
                            case ALLO:
                                uo.commandALLO(user, cmd);
                                break;
                            case APPE:
                                uo.commandAPPE(user, cmd);
                                break;
                            case AUTH:
                                uo.commandAUTH(user, cmd);
                                break;
                            case AVBL:
                                uo.commandAVBL(user, cmd);
                                break;
                            case CCC:
                                uo.commandCCC(user, cmd);
                                break;
                            case CDUP:
                                uo.commandCDUP(user, cmd);
                                break;
                            case CONF:
                                uo.commandCONF(user, cmd);
                                break;
                            case CSID:
                                uo.commandCSID(user, cmd);
                                break;
                            case CWD:
                                uo.commandCWD(user, cmd);
                                break;
                            case DELE:
                                uo.commandDELE(user, cmd);
                                break;
                            case DSIZ:
                                uo.commandDSIZ(user, cmd);
                                break;
                            case ENC:
                                uo.commandENC(user, cmd);
                                break;
                            case EPRT:
                                uo.commandEPRT(user, cmd);
                                break;
                            case EPSV:
                                uo.commandEPSV(user, cmd);
                                break;
                            case FEAT:
                                uo.commandFEAT(user, cmd);
                                break;
                            case HELP:
                                uo.commandHELP(user, cmd);
                                break;
                            case HOST:
                                uo.commandHOST(user, cmd);
                                break;
                            case LANG:
                                uo.commandLANG(user, cmd);
                                break;
                            case LIST:
                                uo.commandLIST(user, cmd);
                                break;
                            case LPRT:
                                uo.commandLPRT(user, cmd);
                                break;
                            case LPSV:
                                uo.commandLPSV(user, cmd);
                                break;
                            case MDTM:
                                uo.commandMDTM(user, cmd);
                                break;
                            case MFCT:
                                uo.commandMFCT(user, cmd);
                                break;
                            case MFF:
                                uo.commandMFF(user, cmd);
                                break;
                            case MFMT:
                                uo.commandMFMT(user, cmd);
                                break;
                            case MIC:
                                uo.commandMIC(user, cmd);
                                break;
                            case MKD:
                                uo.commandMKD(user, cmd);
                                break;
                            case MLSD:
                                uo.commandMLSD(user, cmd);
                                break;
                            case MLST:
                                uo.commandMLST(user, cmd);
                                break;
                            case MODE:
                                uo.commandMODE(user, cmd);
                                break;
                            case NLST:
                                uo.commandNLST(user, cmd);
                                break;
                            case NOOP:
                                uo.commandNOOP(user, cmd);
                                break;
                            case OPTS:
                                uo.commandOPTS(user, cmd);
                                break;
                            case PASS:
                                uo.commandPASS(user, cmd);
                                break;
                            case PASV:
                                uo.commandPASV(user, cmd);
                                break;
                            case PBSZ:
                                uo.commandPBSZ(user, cmd);
                                break;
                            case PORT:
                                uo.commandPORT(user, cmd);
                                break;
                            case PROT:
                                uo.commandPROT(user, cmd);
                                break;
                            case PWD:
                                uo.commandPWD(user, cmd);
                                break;
                            case QUIT:
                                uo.commandQUIT(user, cmd);
                                break;
                            case REIN:
                                uo.commandREIN(user, cmd);
                                break;
                            case REST:
                                uo.commandREST(user, cmd);
                                break;
                            case RETR:
                                uo.commandRETR(user, cmd);
                                break;
                            case RMD:
                                uo.commandRMD(user, cmd);
                                break;
                            case RMDA:
                                uo.commandRMDA(user, cmd);
                                break;
                            case RNFR:
                                uo.commandRNFR(user, cmd);
                                break;
                            case RNTO:
                                uo.commandRNTO(user, cmd);
                                break;
                            case SITE:
                                uo.commandSITE(user, cmd);
                                break;
                            case SIZE:
                                uo.commandSIZE(user, cmd);
                                break;
                            case SMNT:
                                uo.commandSMNT(user, cmd);
                                break;
                            case SPSV:
                                uo.commandSPSV(user, cmd);
                                break;
                            case STAT:
                                uo.commandSTAT(user, cmd);
                                break;
                            case STOR:
                                uo.commandSTOR(user, cmd);
                                break;
                            case STOU:
                                uo.commandSTOU(user, cmd);
                                break;
                            case STRU:
                                uo.commandSTRU(user, cmd);
                                break;
                            case SYST:
                                uo.commandSYST(user, cmd);
                                break;
                            case THMB:
                                uo.commandTHMB(user, cmd);
                                break;
                            case TYPE:
                                uo.commandTYPE(user, cmd);
                                break;
                            case USER:
                                uo.commandUSER(user, cmd);
                                break;
                            case XCUP:
                                uo.commandXCUP(user, cmd);
                                break;
                            case XMKD:
                                uo.commandXMKD(user, cmd);
                                break;
                            case XPWD:
                                uo.commandXPWD(user, cmd);
                                break;
                            case XRCP:
                                uo.commandXRCP(user, cmd);
                                break;
                            case XRMD:
                                uo.commandXRMD(user, cmd);
                                break;
                            case XRSQ:
                                uo.commandXRSQ(user, cmd);
                                break;
                            case XSEM:
                                uo.commandXSEM(user, cmd);
                                break;
                            case XSEN:
                                uo.commandXSEN(user, cmd);
                                break;
                            default:
                                throw new IllegalFTPCommandException(cmd.getCmd());
                            }
                            break;
                        default:
                            throw new IllegalUserState("Unknow user state!");
                        }
                    } catch (IllegalFTPCommandException e) {
                        log.debug("Unknow FTP Command \"{}\" !!", e.getMessage());
                        user.setReply(e.getMessage()+"\r");
                        user.setFinished(false);
                    } catch(IOException e){
                        log.debug("Exception thrown：{}",e.getMessage());
                        user.setReply(e.getMessage()+"\r");
                        user.setFinished(false);
                    }
                    finished = user.isFinished();
                }
                ctrlOutput.println(user.getReply());
                ctrlOutput.flush();
            }
            ctrlSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalUserState e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }

}