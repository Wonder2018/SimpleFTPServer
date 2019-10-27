package top.imwonder.simpleftpserver.domain;

public enum FTPState {
    FS_WAIT_LOGIN, // 需要用户名
    FS_WAIT_PASS, // 需要密码
    FS_LOGIN, // 成功登录
}