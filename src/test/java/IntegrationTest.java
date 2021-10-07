import org.Bootcamp.alexander.bankapi.MainApp;
import org.Bootcamp.alexander.bankapi.db.DBInitializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IntegrationTest {
    @BeforeAll
    public static void setUp() throws IOException {
        DBInitializer.init();
        MainApp.ServerStarter.start();
    }

    @Test
    public void issueCardTest() throws IOException {
//        DBInitializer.init();
//        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/card/order");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;");
        String jsonString = "{\n" +
                "  \"number\": \"42024305346286324586\",\n" +
                "  \"month\": \"07\",\n" +
                "  \"year\": \"2028\",\n" +
                "  \"code\": \"353\",\n" +
                "  \"balance\": 0,\n" +
                "  \"accountId\": 1\n" +
                "}";
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String actual;

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            actual = response.toString();
        }

        String expected = "Card added.";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void viewCardsTest() throws IOException {
//        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/card/view");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        String actual;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            actual = content.toString();
        }

        String expected = "[ {  \"id\" : 1,  \"number\" : \"42024305346286324576\"} ]";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeDepositTest() throws IOException {
//        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/account/pay");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;");
        String jsonString = "{\n" +
                "  \"number\": \"40804810200003497183\",\n" +
                "  \"sum\" : 100\n" +
                "}";
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String actual;

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            actual = response.toString();
        }

        String expected = "Balance replenished.";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkBalanceTest() throws IOException {
//        Main.ServerStarter.start();

        final URL url = new URL("http://localhost:8080/account/balance?number=40804810200003497183");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        String actual;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            actual = content.toString();
        }

        String expected = "0";
        Assert.assertEquals(expected, actual);
    }
}
