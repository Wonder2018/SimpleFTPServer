package top.imwonder.simpleftpserver.swing;

import javax.swing.JMenuBar;

import top.imwonder.simpleftpserver.swing.menu.Document;
import top.imwonder.simpleftpserver.swing.menu.Help;
import top.imwonder.simpleftpserver.swing.menu.Server;

public class MenuBar extends JMenuBar{

    private static final long serialVersionUID = 1L;

    public MenuBar(){
        this.add(new Document());
        this.add(new Server());
        this.add(new Help());
    }
    
}