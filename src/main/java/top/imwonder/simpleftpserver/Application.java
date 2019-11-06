package top.imwonder.simpleftpserver;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.server.ServerCore;
import top.imwonder.simpleftpserver.swing.MainFrame;

/**
 * ┌─┐ ┌─┐ ┌─┐               ┌─┐          ┌───────┬───────┐ ┌─┐ ┌─────┐ 
 * │ │ │ │ │ │               │ │          │ ┌───┐ │ ┌───┐ ├─┘ │ │ ┌─┐ │ 
 * │ │ │ │ │ ├─────┬────┐┌───┘ ├─────┬───┐└─┘ ┌─┘ │ │ │ │ ├─┐ │ │ └─┘ │ 
 * │ └─┘ └─┘ │ ┌─┐ │ ┌─┐└┤ ┌─┐ │ │───┤ ┌─┘┌───┘ ┌─┤ │ │ │ │ │ │ └───┐ │
 * └─┐ ┌─┐ ┌─┤ └─┘ │ │ │ │ └─┘ │ │───┤ │  │ │ └───┤ └───┘ ├─┘ └─┬───┘ │
 *   └─┘ └─┘ └─────┴─┘ └─┴─────┴─────┴─┘  └───────┴───────┴─────┴─────┘ 
 *   Powered with ❤ by www.danni.love www.imwonder.top
 */
@Slf4j
public final class Application {
    private Application() {
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        // InputStream msyhIn = Application.class.getClassLoader().getResourceAsStream("fonts/msyh.ttc");
        // Font msyh;
        // try {
        //     msyh = Font.createFont(Font.TRUETYPE_FONT, msyhIn);
        //     initGlobalFont(msyh.deriveFont(15.0f));
        // } catch (IOException | FontFormatException e) {
        //     log.warn("Can not read Font File, Use System font!");
        // }
        // new MainFrame();
        log.info("listening port:{}", 2121);
        new ServerCore().start();
    }

    private static void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}
