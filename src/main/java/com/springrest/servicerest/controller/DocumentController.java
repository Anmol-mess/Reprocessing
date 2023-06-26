package com.springrest.servicerest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.springrest.servicerest.model.DocumentRequest;

@RestController
@RequestMapping("/documents")
public class DocumentController {
	
	@PostMapping("/process")
    public ResponseEntity<String> processDocument(@RequestBody DocumentRequest[] documentRequests) {
        // Initialize the response string
        StringBuilder responseBuilder = new StringBuilder();

        // Process each document request
        for (DocumentRequest documentRequest : documentRequests) {
            // Extract the necessary data from the request
            String entityId = documentRequest.getEntityId();
            String documentType = documentRequest.getDocumentType();
            String locationUrl = documentRequest.getLocationUrl();

            // Append the values to the response string
            String response = entityId + documentType + locationUrl;
            responseBuilder.append(response);
        }

        // Return the response as a string
        String response = responseBuilder.toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
