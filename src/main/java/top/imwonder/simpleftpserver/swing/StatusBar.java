package top.imwonder.simpleftpserver.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class StatusBar extends JPanel{

    private static final long serialVersionUID = 1L;

    private JLabel status;

    public StatusBar(){
        status = new JLabel("就绪");
        status.setFont(new Font("Microsoft",Font.PLAIN,12));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new LineBorder(Color.DARK_GRAY));
        this.add(status);
    }
    
}