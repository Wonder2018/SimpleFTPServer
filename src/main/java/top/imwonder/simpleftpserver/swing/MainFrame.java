package top.imwonder.simpleftpserver.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame{

    private static final long serialVersionUID = 1L;

    public MainFrame(){
        this.setTitle("Simple FTP Server");
        this.setJMenuBar(new MenuBar());
        Container cont = this.getContentPane();
        cont.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.add(new StatusBar(), BorderLayout.SOUTH);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(500,400));
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
}