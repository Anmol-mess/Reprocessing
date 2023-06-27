package com.springrest.servicerest.services;

import java.io.IOException;
import java.net.MalformedURLException;

import com.springrest.servicerest.model.DocumentRequest;

public interface DocumentRequestDao {
	
	
	
	public  void manipulateData(DocumentRequest[] DocumentRequest) throws MalformedURLException, IOException;
	
}
