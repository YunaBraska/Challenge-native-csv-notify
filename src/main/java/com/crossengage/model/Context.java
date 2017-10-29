package com.crossengage.model;

import com.crossengage.logic.NotificationService;
import com.crossengage.logic.gateway.MailGateway;
import com.crossengage.logic.gateway.SmsGateway;

public class Context {

    private static Context instance = null;
    private final MailGateway mailGateway;
    private final SmsGateway smsGateway;
    private final NotificationService notificationService;

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private Context() {
        mailGateway = new MailGateway();
        smsGateway = new SmsGateway();
        notificationService = new NotificationService(mailGateway, smsGateway);
    }

    public MailGateway getMailGateway() {
        return mailGateway;
    }

    public SmsGateway getSmsGateway() {
        return smsGateway;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    @Override
    public String toString() {
        return "Context{" +
                "mailGateway=" + mailGateway +
                ", smsGateway=" + smsGateway +
                ", notificationService=" + notificationService +
                '}';
    }
}
