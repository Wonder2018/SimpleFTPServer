package top.imwonder.simpleftpserver.domain;

import lombok.Getter;

public enum FTPState {
    FS_WAIT_LOGIN(220, "需要用户名"), FS_WAIT_PASS(331, "需要密码"), FS_LOGIN(230, "成功登录");

    @Getter
    private int code;

    @Getter
    private String description;

    FTPState(int code, String description) {
        this.code = code;
        this.description = description;
    }
}