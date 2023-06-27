package com.springrest.servicerest.model;

import com.springrest.servicerest.entities.WorkflowTracker;

public class DocumentRequest {
	  
	private String entityId;
	    private String documentType;
	    private String locationUrl;

	    // Getters and setters

	    public String getEntityId() {
	        return entityId;
	    }

	    public void setEntityId(String entityId) {
	        this.entityId = entityId;
	    }

	    public String getDocumentType() {
	        return documentType;
	    }

	    public void setDocumentType(String documentType) {
	        this.documentType = documentType;
	    }

	    public String getLocationUrl() {
	        return locationUrl;
	    }

	    public void setLocationUrl(String locationUrl) {
	        this.locationUrl = locationUrl;
	    }
	   
	  
	    
}
