package com.crossengage;

import com.crossengage.model.Context;
import com.crossengage.model.User;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Application {

    //Pls look in the readme for more comments
    public static void main(String... paths) throws IOException {
        Context context = Context.getInstance();
        File file;
        Set<User> failedUsers;

        for (String path : paths) {
            file = new File(path);
            System.out.println("\n##### READ FILE [" + file.getName() + "] #####");

            failedUsers = context.getNotificationService().sendFromCsv(file);

            if (!failedUsers.isEmpty()) {
                System.err.println("Failed Users: " + failedUsers);
            }
        }
        System.out.println("Letterman is done - Have a nice day");
    }
}
