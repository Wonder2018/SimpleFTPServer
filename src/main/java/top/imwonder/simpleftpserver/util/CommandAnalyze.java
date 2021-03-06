package top.imwonder.simpleftpserver.util;

import top.imwonder.simpleftpserver.domain.Command;
import top.imwonder.simpleftpserver.domain.User;
import top.imwonder.simpleftpserver.exception.IllegalFTPCommandException;
import top.imwonder.simpleftpserver.exception.IllegalUserState;

/*
 * @Author: Wonder2019 
 * @Date: 2019-10-27 11:25:22 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2019-10-27 11:26:28
 */
public class CommandAnalyze {
    public static Command analize(String fullcmd) throws IllegalFTPCommandException {
        Command cmd = new Command();
        cmd.setCmd(getCommand(fullcmd));
        cmd.setParam(getParam(fullcmd));
        return cmd;
    }

    public static String getCommand(String fullcmd) {
        fullcmd = fullcmd.trim();
        int tag = fullcmd.indexOf(" ");
        if (tag > 0) {// 有参命令
            return fullcmd.substring(0, tag).trim().toUpperCase();
        }
        return fullcmd.toUpperCase();
    }

    public static String getParam(String fullcmd) {
        fullcmd = fullcmd.trim();
        int tag = fullcmd.indexOf(" ");
        if (tag > 0) {// 有参命令
            return fullcmd.substring(tag + 1).trim();
        }
        return "";
    }

    public static void execute(User user, Command cmd) throws IllegalFTPCommandException, IllegalUserState {
        switch (user.getState()) {
        case FS_WAIT_LOGIN:
            user.commandUSER(user, cmd);
            break;
        case FS_WAIT_PASS:
            user.commandPASS(user, cmd);
            break;
        case FS_LOGIN:
            switch (cmd.getCmd()) {
            case "ABOR":
                user.commandABOR(user);
                break;
            case "ACCT":
                user.commandACCT(user, cmd);
                break;
            case "ADAT":
                user.commandADAT(user, cmd);
                break;
            case "ALLO":
                user.commandALLO(user, cmd);
                break;
            case "APPE":
                user.commandAPPE(user, cmd);
                break;
            case "AUTH":
                user.commandAUTH(user, cmd);
                break;
            case "AVBL":
                user.commandAVBL(user, cmd);
                break;
            case "CCC":
                user.commandCCC(user, cmd);
                break;
            case "CDUP":
                user.commandCDUP(user);
                break;
            case "CONF":
                user.commandCONF(user, cmd);
                break;
            case "CSID":
                user.commandCSID(user, cmd);
                break;
            case "CWD":
                user.commandCWD(user, cmd);
                break;
            case "DELE":
                user.commandDELE(user, cmd);
                break;
            case "DSIZ":
                user.commandDSIZ(user, cmd);
                break;
            case "ENC":
                user.commandENC(user, cmd);
                break;
            case "EPRT":
                user.commandEPRT(user, cmd);
                break;
            case "EPSV":
                user.commandEPSV(user, cmd);
                break;
            case "FEAT":
                user.commandFEAT(user, cmd);
                break;
            case "HELP":
                user.commandHELP(user, cmd);
                break;
            case "HOST":
                user.commandHOST(user, cmd);
                break;
            case "LANG":
                user.commandLANG(user, cmd);
                break;
            case "LIST":
                user.commandLIST(user, cmd);
                break;
            case "LPRT":
                user.commandLPRT(user, cmd);
                break;
            case "LPSV":
                user.commandLPSV(user, cmd);
                break;
            case "MDTM":
                user.commandMDTM(user, cmd);
                break;
            case "MFCT":
                user.commandMFCT(user, cmd);
                break;
            case "MFF":
                user.commandMFF(user, cmd);
                break;
            case "MFMT":
                user.commandMFMT(user, cmd);
                break;
            case "MIC":
                user.commandMIC(user, cmd);
                break;
            case "MKD":
                user.commandMKD(user, cmd);
                break;
            case "MLSD":
                user.commandMLSD(user, cmd);
                break;
            case "MLST":
                user.commandMLST(user, cmd);
                break;
            case "MODE":
                user.commandMODE(user, cmd);
                break;
            case "NLST":
                user.commandNLST(user, cmd);
                break;
            case "NOOP":
                user.commandNOOP(user);
                break;
            case "OPTS":
                user.commandOPTS(user, cmd);
                break;
            case "PASS":
                user.commandPASS(user, cmd);
                break;
            case "PASV":
                user.commandPASV(user);
                break;
            case "PBSZ":
                user.commandPBSZ(user, cmd);
                break;
            case "PORT":
                user.commandPORT(user, cmd);
                break;
            case "PROT":
                user.commandPROT(user, cmd);
                break;
            case "PWD":
                user.commandPWD(user);
                break;
            case "QUIT":
                user.commandQUIT(user);
                break;
            case "REIN":
                user.commandREIN(user, cmd);
                break;
            case "REST":
                user.commandREST(user, cmd);
                break;
            case "RETR":
                user.commandRETR(user, cmd);
                break;
            case "RMD":
                user.commandRMD(user, cmd);
                break;
            case "RMDA":
                user.commandRMDA(user, cmd);
                break;
            case "RNFR":
                user.commandRNFR(user, cmd);
                break;
            case "RNTO":
                user.commandRNTO(user, cmd);
                break;
            case "SITE":
                user.commandSITE(user, cmd);
                break;
            case "SIZE":
                user.commandSIZE(user, cmd);
                break;
            case "SMNT":
                user.commandSMNT(user, cmd);
                break;
            case "SPSV":
                user.commandSPSV(user, cmd);
                break;
            case "STAT":
                user.commandSTAT(user, cmd);
                break;
            case "STOR":
                user.commandSTOR(user, cmd);
                break;
            case "STOU":
                user.commandSTOU(user, cmd);
                break;
            case "STRU":
                user.commandSTRU(user, cmd);
                break;
            case "SYST":
                user.commandSYST(user);
                break;
            case "THMB":
                user.commandTHMB(user, cmd);
                break;
            case "TYPE":
                user.commandTYPE(user, cmd);
                break;
            case "USER":
                user.commandUSER(user, cmd);
                break;
            case "XCUP":
                user.commandXCUP(user, cmd);
                break;
            case "XMKD":
                user.commandXMKD(user, cmd);
                break;
            case "XPWD":
                user.commandXPWD(user, cmd);
                break;
            case "XRCP":
                user.commandXRCP(user, cmd);
                break;
            case "XRMD":
                user.commandXRMD(user, cmd);
                break;
            case "XRSQ":
                user.commandXRSQ(user, cmd);
                break;
            case "XSEM":
                user.commandXSEM(user, cmd);
                break;
            case "XSEN":
                user.commandXSEN(user, cmd);
                break;
            default:
                throw new IllegalFTPCommandException(cmd.getCmd());
            }
            break;
        default:
            throw new IllegalUserState("Unknow user state!");
        }
    }
}