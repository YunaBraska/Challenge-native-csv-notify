package com.crossengage.logic.gateway;

import com.crossengage.model.Strategies;
import com.crossengage.model.User;

import java.util.regex.Pattern;

import static com.crossengage.model.Strategies.ALL;
import static com.crossengage.model.Strategies.EMAIL;

public class MailGateway {

    private static Pattern RFC_5322 = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public boolean send(final User user) {
        if (!isApplicable(user.getContactBy())) {
            return true;
        }

        if (isValidMailAddress(user.getMail())) {
            System.out.println("Mail was send to [" + user.getId() + "]");
            return true;
        }
        return false;
    }

    private boolean isValidMailAddress(final String mail) {
        //would be nicer with javamail library like "try{new InternetAddress(email)...."
        return RFC_5322.matcher(mail).matches();
    }

    //would be better to call this from outside
    private boolean isApplicable(final Strategies strategy) {
        return (strategy == EMAIL || strategy == ALL);
    }
}
