package top.imwonder.simpleftpserver.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Document extends JMenu{

    private static final long serialVersionUID = 1L;

    public Document(){
        this.setText("文件");
        this.add(new JMenuItem("载入配置"));
        this.add(new JMenuItem("另存配置"));
        this.add(new JMenuItem("载入用户"));
        this.add(new JMenuItem("另存用户"));
        this.add(new JMenuItem("设置"));
    }
    
}