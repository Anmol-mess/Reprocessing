package com.springrest.servicerest.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.springrest.servicerest.model.DocumentRequest;
import com.springrest.servicerest.services.DocumentRequestServiceImpl;

@RestController
@RequestMapping("/documents")
public class DocumentController {
	
	@PostMapping("/process")
    public ResponseEntity<String> processDocument(@RequestBody DocumentRequest[] documentRequests) throws IOException, IOException {
        // Initialize the response string
        StringBuilder responseBuilder = new StringBuilder();
        System.out.println("Array--> " + Arrays.toString(documentRequests));
        // Process each document request
        for (DocumentRequest documentRequest : documentRequests) {
            // Extract the necessary data from the request
            String entityId = documentRequest.getEntityId();
            String documentType = documentRequest.getDocumentType();
            String locationUrl = documentRequest.getLocationUrl();
            System.out.println("entityId -- > " + entityId);
            System.out.println("documentType -- > "+ documentType);
            System.out.println("locationUrl -- > " + locationUrl);
           // DocumentRequestServiceImpl documentRequestServiceImpl = new DocumentRequestServiceImpl();
			// Append the values to the response string\
           // documentRequestServiceImpl.manipulateData(documentRequests);
            String response = entityId + documentType + locationUrl;
            responseBuilder.append(response);
        }

        // Return the response as a string
        String response = responseBuilder.toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
