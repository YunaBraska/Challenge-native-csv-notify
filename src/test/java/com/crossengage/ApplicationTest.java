package com.crossengage;

import com.crossengage.model.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ApplicationTest {

    private PrintStream out;
    private PrintStream err;
    private ByteArrayOutputStream outConsole; //workaround as spy, argument capture and things are not present
    private ByteArrayOutputStream errConsole; //workaround as spy, argument capture and things are not present

    /**
     * Functional tests and love is everything you need :D
     */

    @Before
    public void setUp() {
        out = System.out;
        err = System.err;
        errConsole = new ByteArrayOutputStream();
        outConsole = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errConsole));
        System.setOut(new PrintStream(outConsole));
    }

    @After
    public void tearDown() {
        System.setErr(out);
        System.setErr(err);
    }

    @Test
    public void smokeTest() throws Exception {
        Context context = Context.getInstance();
        assertThat(context, is(notNullValue()));
        assertThat(context.getSmsGateway(), is(notNullValue()));
        assertThat(context.getMailGateway(), is(notNullValue()));
        assertThat(context.getNotificationService(), is(notNullValue()));
    }

    @Test
    public void runShouldBeSuccessful() throws Exception {
        Application.main(getPath("/test_user_data.csv"));
        assertThat(outConsole.toString(), containsString("Mail was send to [1]"));
        assertThat(outConsole.toString(), containsString("Mail was send to [4]"));
        assertThat(outConsole.toString(), containsString("Mail was send to [5]"));
        isSend(3, 1, 0);

    }

    @Test
    public void runWithoutParameterShouldBeSuccessful() throws Exception {
        Application.main();
        isSend(0, 0, 0);
    }

    @Test
    public void runWithBrokenCsvShouldErrorLogFailedRow() throws Exception {
        Application.main(getPath("/test_user_data_broken.csv"));
        assertThat(errConsole.toString(), is(equalTo("Error while reading User [2,true,none,+999999999998]\nError while reading User []\n")));
        isSend(3, 1, 0);
    }

    @Test
    public void runInvalidMailAddressShouldReturnFailedUsers() throws Exception {
        Application.main(getPath("/test_user_data_invalid.csv"));
        assertThat(errConsole.toString(), containsString("mail='com'"));
        assertThat(errConsole.toString(), containsString("mail='test3mail.com'"));
        assertThat(errConsole.toString(), containsString("mail='test4@mail.com'"));
        isSend(3, 4, 3);
    }

    private String getPath(String fileName) {
        return getClass().getResource(fileName).getFile();
    }


    private void isSend(final int mail, final int sms, final int exceptions) {
        assertThat(outConsole.toString().split("Mail was send to").length, is(mail + 1));
        assertThat(outConsole.toString().split("Sms was send to").length, is(sms + 1));
        assertThat(errConsole.toString().split("User\\{id=").length, is(exceptions + 1));
    }
}