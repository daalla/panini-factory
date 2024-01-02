package dev.dallavalle.paninifactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        sendWhatsAppMessage();
    }

    private static void sendWhatsAppMessage() {
        try {
            String phoneNumberId = System.getenv("PHONE_NUMBER_ID");
            String accessToken = System.getenv("ACCESS_TOKEN");
            String recipientNumber = System.getenv("RECIPIENT_NUMBER");

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://graph.facebook.com/v13.0/%s/messages", phoneNumberId)))
                .header("Authorization", String.format("Bearer %s", accessToken))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"%s\", \"type\": \"text\", \"text\": { \"preview_url\": false, \"body\": \"This is an example of a text message\" } }", recipientNumber)))
                .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (URISyntaxException | IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
