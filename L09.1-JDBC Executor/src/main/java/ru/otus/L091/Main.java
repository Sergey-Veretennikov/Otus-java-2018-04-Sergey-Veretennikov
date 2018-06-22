package ru.otus.L091;

import ru.otus.L091.base.DBService;
import ru.otus.L091.base.UserDataSet;
import ru.otus.L091.connection.DBServiceConnection;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceConnection()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTables();
//            dbService.addUsersDataSet(new UserDataSet("qwert", 10),
//                    new UserDataSet("asdf", 20),
//                    new UserDataSet("zxcv",50));
//            dbService.save(new UserDataSet("qwert", 10));

            System.out.println(dbService.load(2, UserDataSet.class).toString());

//            dbService.deleteTables();
        }
    }
}
