package com.appopay.visa.service;

import com.appopay.visa.model.TwillioRequestDTO;
import com.appopay.visa.model.TwillioResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Service
public class TwillioService {

    private static final Logger logger = LoggerFactory.getLogger(TwillioService.class);
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TwillioResponseDTO sendSMs(String authToken, TwillioRequestDTO twillioRequest) {
        TwillioResponseDTO twillioResponse = null;
        try {
            // Twilio credentials

//            String credentials = twillioRequest.getAccountSid() + ":" + authToken;
            String basicAuth = "Basic " + authToken;



//
//            // Message details
//            String body = "Hello, this is a test message!";
//            String from = "14243534350";
//            String to = "923222851644";

            // Form the request body
            RequestBody requestBody = new FormBody.Builder()
                    .add("Body", twillioRequest.getBody())
                    .add("From", twillioRequest.getFrom())
                    .add("To", twillioRequest.getTo())
                    .build();

            // Build the request
            Request request = new Request.Builder()
                    .url("https://api.twilio.com/2010-04-01/Accounts/" + twillioRequest.getAccountSid() + "/Messages.json")
                    .post(requestBody)
                    .addHeader("Authorization", basicAuth)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
            // Execute the request
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected HTTP status code: " + response.code());
                }
                twillioResponse = objectMapper.readValue(response.body().string(), TwillioResponseDTO.class);
                System.out.println("resp.getAccount_sid()");
                System.out.println(twillioResponse.getAccount_sid());
                System.out.println("Message sent successfully.");

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return twillioResponse;
    }

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    public String sendSMS(String to, String from, String body) {
        String url = "https://api.twilio.com/2010-04-01/Accounts/" + accountSid + "/Messages.json";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(accountSid, authToken);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("To", to);
        map.add("From", from);
        map.add("Body", body);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }


}
