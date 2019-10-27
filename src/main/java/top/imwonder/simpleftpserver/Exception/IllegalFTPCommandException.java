package top.imwonder.simpleftpserver.Exception;
/*
 * @Author: Wonder2019 
 * @Date: 2019-10-27 11:25:34 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2019-10-27 11:25:55
 */
public class IllegalFTPCommandException extends Exception {

    private static final long serialVersionUID = 1L;

    public IllegalFTPCommandException(String msg){
        super(msg);
    }
}