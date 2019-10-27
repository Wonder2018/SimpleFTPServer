package top.imwonder.simpleftpserver.domain;

import lombok.Data;

@Data
public class Command{
    private String uuid;
    private String cmd;
    private String[] param;
    private FTPCommand fc;
    private String requestfile;
    private boolean isrest;
}