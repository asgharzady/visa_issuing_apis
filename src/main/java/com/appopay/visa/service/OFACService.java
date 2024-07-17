package com.appopay.visa.service;

import com.appopay.visa.model.ApiResponseDTO;
import com.appopay.visa.model.OfacRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Service
public class OFACService {


    public boolean isApproved(String name) {
        if (getMatches(name) > 0)
            return false;
        else {
            return true;
        }
    }

    public Integer getMatches(String name) {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OfacRequestDTO ofacRequestDTO = new OfacRequestDTO();
            ofacRequestDTO.setApiKey("9724e637-bbac-4446-abdc-85736aa14344");
            ofacRequestDTO.setMinScore(95);
            ofacRequestDTO.setSources(List.of("SDN"));
            ofacRequestDTO.setTypes(List.of("person"));

            OfacRequestDTO.CaseDTO caseDTO = new OfacRequestDTO.CaseDTO();
            caseDTO.setName(name);
            caseDTO.setType("individual");
            ofacRequestDTO.setCases(List.of(caseDTO));
            // Convert OfacRequestDTO instance to JSON string
            String jsonString = objectMapper.writeValueAsString(ofacRequestDTO);

            RequestBody body = RequestBody.create(
                    jsonString,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url("https://api.ofac-api.com/v4/screen")
                    .post(body)
                    .build();


            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP status code: " + response.code());
            }

            // Parse JSON response into ApiResponse object
            ApiResponseDTO apiResponse = objectMapper.readValue(response.body().string(), ApiResponseDTO.class);

            System.out.println("match count");
            Integer matchCount = apiResponse.getResults().get(0).getMatchCount();
            System.out.println(matchCount);
            return matchCount;

        } catch (Exception e) {
            System.out.println("Exception");
        }

        return 0;
    }


}
