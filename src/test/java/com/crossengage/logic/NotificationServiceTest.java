package com.crossengage.logic;

import com.crossengage.model.Context;
import com.crossengage.model.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.none;

public class NotificationServiceTest {


    /**
     * Basically the ApplicationTest covers the same logic already
     * its just more comfortable for me to test here against objects instead strings/logs its also way mor faster and smaller
     * Tatata - So here is version number 0.0.7 :P
     */

    @Rule
    public final ExpectedException exception = none();

    private NotificationService notificationService;

    @Before
    public void setUp() {
        notificationService = Context.getInstance().getNotificationService();
    }

    @Test
    public void sendFromCsvShouldBeSuccessful() throws Exception {
        Set<User> failedUsers = notificationService.sendFromCsv(getFile("/test_user_data.csv"));
        assertThat(failedUsers.size(), is(0));
    }

    @Test //test covered already by main method as there is no chance to put null into it
    public void sendFromCsvWithNullableParameterShouldBeSuccessful() {
        exception.expect(NullPointerException.class);

        notificationService.sendFromCsv(null);
    }

    @Test
    public void sendFromCsvWithInvalidMailAddressShouldReturnFailedUsers() {
        Set<User> failedUsers = notificationService.sendFromCsv(getFile("/test_user_data_invalid.csv"));
        assertThat(failedUsers.size(), is(3));
        for (int id : new int[]{4, 2, 3}) {
            assertThat(failedUsers.stream().filter(user -> user.getId() == id).collect(Collectors.toList()).size(), is(1));
        }
    }

    private File getFile(String fileName) {
        return new File(getClass().getResource(fileName).getFile());
    }

}