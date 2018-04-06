package ru.otus.dbserver;

import ru.otus.dbserver.config.Configuration;
import ru.otus.dbserver.entity.UserDataSet;
import ru.otus.dbserver.service.ClientSocketMsgWorker;
import ru.otus.dbserver.service.DBService;
import ru.otus.dbserver.service.SocketMsgWorker;
import ru.otus.dbserver.util.DBHelper;

import java.io.IOException;
import java.sql.SQLException;

public class DBServerMain {
    private static final int USERS_AMOUNT = 100;

    private static final String HOST = "localhost";
    private static final int PORT = 5051;

    public static void main(String[] args) throws Exception {
        new DBServerMain().start();
    }

    private void start() throws Exception {
        Configuration configuration = new Configuration();
        configuration.addClass(UserDataSet.class);

        DBService dbService = DBHelper.getDBServiceInstance(configuration);

        Thread dbUsage = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(2000);

                    // DBaseService usage
                    long id = 0;
                    for (int i = 0; i < USERS_AMOUNT; i++) {
                        id = dbService.save(new UserDataSet("Ivan", 2));
                    }
                    for (long i = id; i > id - USERS_AMOUNT; i--) {
                        dbService.load(i, UserDataSet.class);
                    }
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        dbUsage.start();

        SocketMsgWorker client = new ClientSocketMsgWorker(HOST, PORT, dbService);
        client.init();

        dbService.close();
    }
}