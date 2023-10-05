package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Bree", "Reichardt", (byte) 58);
        userService.saveUser("Deborah", "Newsome", (byte) 31);
        userService.saveUser("Felicity", "Smith", (byte) 28);
        userService.saveUser("Abigail", "Freihofer", (byte) 45);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.close();
    }
}
