package top.imwonder.simpleftpserver;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.simpleftpserver.Server.ServerCore;

/**
 * ┌─┐ ┌─┐ ┌─┐ ┌─┐ ┌───────┬───────┐ ┌─┐ ┌─────┐ │ │ │ │ │ │ │ │ │ ┌───┐ │ ┌───┐
 * ├─┘ │ │ ┌─┐ │ │ │ │ │ │ ├─────┬────┐┌───┘ ├─────┬───┐└─┘ ┌─┘ │ │ │ │ ├─┐ │ │
 * └─┘ │ │ └─┘ └─┘ │ ┌─┐ │ ┌─┐└┤ ┌─┐ │ │───┤ ┌─┘┌───┘ ┌─┤ │ │ │ │ │ │ └───┐ │
 * └─┐ ┌─┐ ┌─┤ └─┘ │ │ │ │ └─┘ │ │───┤ │ │ │ └───┤ └───┘ ├─┘ └─┬───┘ │ └─┘ └─┘
 * └─────┴─┘ └─┴─────┴─────┴─┘ └───────┴───────┴─────┴─────┘ Powered with ❤ by
 * www.danni.love www.imwonder.top
 */
@Slf4j
public final class Application {
    private Application() {
    }

    /**
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception {
        log.info("listening port:{}", 21);
        new ServerCore().start();
    }
}
