package top.imwonder.simpleftpserver.Exception;
/*
 * @Author: Wonder2019 
 * @Date: 2019-10-28 20:43:59 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2019-10-28 20:43:59 
 */
public class IllegalFTPState extends Exception {

    private static final long serialVersionUID = 1L;

    public IllegalFTPState(String msg) {
        super(msg);
    }
}