package top.imwonder.simpleftpserver.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Help extends JMenu{

    private static final long serialVersionUID = 1L;

    public Help(){
        this.setText("帮助");
        JMenuItem helpDoc =new JMenuItem("帮助文档");
        JMenuItem chkUpdate =new JMenuItem("检查更新");
        JMenuItem about =new JMenuItem("关于");
        this.add(helpDoc);
        this.add(chkUpdate);
        this.add(about);
    }
    
}