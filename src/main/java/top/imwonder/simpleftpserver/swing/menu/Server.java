package top.imwonder.simpleftpserver.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Server extends JMenu{

    private static final long serialVersionUID = 1L;
    
    public Server(){
        this.setText("服务器");
        this.add(new JMenuItem("启动"));
        this.add(new JMenuItem("重启"));
        this.add(new JMenuItem("停止"));
    }
}