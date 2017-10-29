package com.crossengage.logic.gateway;

import com.crossengage.model.Strategies;
import com.crossengage.model.User;

import static com.crossengage.model.Strategies.ALL;
import static com.crossengage.model.Strategies.PHONE;

public class SmsGateway {

    public boolean send(final User user) {
        if (!isApplicable(user.getContactBy())) {
            return true;
        }

        if (isValidPhoneNumber(user.getPhone())) {
            System.out.println("Sms was send to [" + user.getId() + "]");
            return true;
        }
        return false;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        //the csv file confused me, so i did not a proper validation here
        return phoneNumber.startsWith("+");
    }

    private boolean isApplicable(Strategies strategy) {
        return (strategy == PHONE || strategy == ALL);
    }
}
