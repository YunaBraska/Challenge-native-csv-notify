package com.crossengage.logic;

import com.crossengage.logic.gateway.MailGateway;
import com.crossengage.logic.gateway.SmsGateway;
import com.crossengage.model.Strategies;
import com.crossengage.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class NotificationService {

    private final MailGateway mailGateway;
    private final SmsGateway smsGateway;

    public NotificationService(final MailGateway mailGateway, final SmsGateway smsGateway) {
        this.mailGateway = mailGateway;
        this.smsGateway = smsGateway;
    }

    public Set<User> sendFromCsv(final File file) {
        try {
            return Files.lines(file.toPath())
                    .skip(1)
                    .map(this::transformLineToUser)
                    .filter(Objects::nonNull) //<= looks a bit ugly but better handling would explode this task
                    .filter(User::isActive)
                    .filter(user -> !send(user))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from CSV file", e);
        }
    }

    //Kind/Start of strategy pattern could be improved but will also explode the task
    private Boolean send(final User user) {
        boolean messageError = true;

        if (!mailGateway.send(user)) {
            messageError = false;
        }
        if (!smsGateway.send(user)) {
            messageError = false;
        }

        return messageError;
    }

    private User transformLineToUser(final String userLine) {
        try {
            String[] rows = userLine.split(",");
            return new User(
                    Integer.valueOf(rows[0].trim()),
                    Boolean.valueOf(rows[1].trim()),
                    Strategies.valueOf(rows[2].trim().toUpperCase()),
                    rows[3].trim(),
                    rows[4].trim()
            );
        } catch (IllegalArgumentException | NullPointerException | IndexOutOfBoundsException e) {
            //silent screaming error, this wont be hopefully not the production code :D
            System.err.println("Error while reading User [" + userLine + "]");
            return null;
        }

    }
}
