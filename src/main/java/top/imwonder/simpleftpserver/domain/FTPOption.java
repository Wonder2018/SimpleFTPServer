package top.imwonder.simpleftpserver.domain;

public enum FTPOption {
    FTYPE_ASCII, // ASCII文件
    FTYPE_BIN, // 二进制文件
    FMODE_STREAM, // 流模式
    FMODE_BLOCK, // 块模式
    FMODE_COMPRESSED, // 压缩模式
    FTRANS_PASV,
    FTRANS_PORT,
    FSTRU_FILE, //
    FSTRU_PAGE//
}