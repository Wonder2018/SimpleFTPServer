package top.imwonder.simpleftpserver.util;

import top.imwonder.simpleftpserver.Exception.IllegalFTPCommandException;
import top.imwonder.simpleftpserver.domain.Command;
import top.imwonder.simpleftpserver.domain.FTPCommand;

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
        cmd.setFc(getFc(cmd));
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

    public static FTPCommand getFc(Command cmd) throws IllegalFTPCommandException {
        switch (cmd.getCmd()) {
        case "ABOR":
            return FTPCommand.ABOR;
        case "ACCT":
            return FTPCommand.ACCT;
        case "ADAT":
            return FTPCommand.ADAT;
        case "ALLO":
            return FTPCommand.ALLO;
        case "APPE":
            return FTPCommand.APPE;
        case "AUTH":
            return FTPCommand.AUTH;
        case "AVBL":
            return FTPCommand.AVBL;
        case "CCC":
            return FTPCommand.CCC;
        case "CDUP":
            return FTPCommand.CDUP;
        case "CONF":
            return FTPCommand.CONF;
        case "CSID":
            return FTPCommand.CSID;
        case "CWD":
            return FTPCommand.CWD;
        case "DELE":
            return FTPCommand.DELE;
        case "DSIZ":
            return FTPCommand.DSIZ;
        case "ENC":
            return FTPCommand.ENC;
        case "EPRT":
            return FTPCommand.EPRT;
        case "EPSV":
            return FTPCommand.EPSV;
        case "FEAT":
            return FTPCommand.FEAT;
        case "HELP":
            return FTPCommand.HELP;
        case "HOST":
            return FTPCommand.HOST;
        case "LANG":
            return FTPCommand.LANG;
        case "LIST":
            return FTPCommand.LIST;
        case "LPRT":
            return FTPCommand.LPRT;
        case "LPSV":
            return FTPCommand.LPSV;
        case "MDTM":
            return FTPCommand.MDTM;
        case "MFCT":
            return FTPCommand.MFCT;
        case "MFF":
            return FTPCommand.MFF;
        case "MFMT":
            return FTPCommand.MFMT;
        case "MIC":
            return FTPCommand.MIC;
        case "MKD":
            return FTPCommand.MKD;
        case "MLSD":
            return FTPCommand.MLSD;
        case "MLST":
            return FTPCommand.MLST;
        case "MODE":
            return FTPCommand.MODE;
        case "NLST":
            return FTPCommand.NLST;
        case "NOOP":
            return FTPCommand.NOOP;
        case "OPTS":
            return FTPCommand.OPTS;
        case "PASS":
            return FTPCommand.PASS;
        case "PASV":
            return FTPCommand.PASV;
        case "PBSZ":
            return FTPCommand.PBSZ;
        case "PORT":
            return FTPCommand.PORT;
        case "PROT":
            return FTPCommand.PROT;
        case "PWD":
            return FTPCommand.PWD;
        case "QUIT":
            return FTPCommand.QUIT;
        case "REIN":
            return FTPCommand.REIN;
        case "REST":
            return FTPCommand.REST;
        case "RETR":
            return FTPCommand.RETR;
        case "RMD":
            return FTPCommand.RMD;
        case "RMDA":
            return FTPCommand.RMDA;
        case "RNFR":
            return FTPCommand.RNFR;
        case "RNTO":
            return FTPCommand.RNTO;
        case "SITE":
            return FTPCommand.SITE;
        case "SIZE":
            return FTPCommand.SIZE;
        case "SMNT":
            return FTPCommand.SMNT;
        case "SPSV":
            return FTPCommand.SPSV;
        case "STAT":
            return FTPCommand.STAT;
        case "STOR":
            return FTPCommand.STOR;
        case "STOU":
            return FTPCommand.STOU;
        case "STRU":
            return FTPCommand.STRU;
        case "SYST":
            return FTPCommand.SYST;
        case "THMB":
            return FTPCommand.THMB;
        case "TYPE":
            return FTPCommand.TYPE;
        case "USER":
            return FTPCommand.USER;
        case "XCUP":
            return FTPCommand.XCUP;
        case "XMKD":
            return FTPCommand.XMKD;
        case "XPWD":
            return FTPCommand.XPWD;
        case "XRCP":
            return FTPCommand.XRCP;
        case "XRMD":
            return FTPCommand.XRMD;
        case "XRSQ":
            return FTPCommand.XRSQ;
        case "XSEM":
            return FTPCommand.XSEM;
        case "XSEN":
            return FTPCommand.XSEN;
        default:
            throw new IllegalFTPCommandException(cmd.getCmd());
        }
    }
}