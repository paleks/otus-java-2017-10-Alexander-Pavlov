package ru.otus.messageserver;

import ru.otus.messageserver.server.SocketMsgServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class MessageServerMain {

    public static void main(String[] args) throws Exception {
        new MessageServerMain().start();
    }

    private void start() throws Exception {

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=MessageServer");
        SocketMsgServer server = new SocketMsgServer();
        mbs.registerMBean(server, name);

        server.start();
    }
}
