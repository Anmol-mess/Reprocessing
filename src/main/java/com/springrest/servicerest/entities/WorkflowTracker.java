package com.springrest.servicerest.entities;


import java.io.Serializable;

import com.springrest.servicerest.Core.CoreUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;


@Entity
@Table(name = "WORKFLOW_TRACKER")
@NamedQuery(name = "WorkflowTracker.findAll", query = "SELECT w FROM WorkflowTracker w")
public class WorkflowTracker implements Serializable 
{

	@Id
	@Column(name = "ENTITY_ID")
	private String entityId;
	
	@Column(name = "S3_UPLOADED_TIMESTAMP")
	private String s3FileUploadedTimestamp;

	
	@Column(name = "FILE_TIMESTAMP")
	private String fileTimeStamp;
	
	
	public String getEntityId() {
		return entityId;
	}




	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}




	public String getS3FileUploadedTimestamp() {
		return s3FileUploadedTimestamp;
	}




	public void setS3FileUploadedTimestamp(String s3FileUploadedTimestamp) {
		this.s3FileUploadedTimestamp = s3FileUploadedTimestamp;
	}




	public String getFileTimeStamp() {
		return fileTimeStamp;
	}




	public void setFileTimeStamp(String fileTimeStamp) {
		this.fileTimeStamp = fileTimeStamp;
	}


	public String toSchemaString() {
		return "{"+
				"\"schema\":{"+
						"\"type\":\"string\","+
						"\"optional\":false,"+
						"\"field\":\"ENTITY_ID\""+
						"},"+
						"{"+
						"\"type\":\"string\","+
						"\"optional\":true,"+
						"\"field\":\"S3_UPLOADED_TIMESTAMP\""+
						"},"+
						"{"+
						"\"type\":\"string\","+
						"\"optional\":true,"+
						"\"field\":\"FILE_TIMESTAMP\""+
						"},"+
	"\"payload\":{"+
	"\"ENTITY_ID\":" + CoreUtil.getQuotedString(this.entityId) + ","+
	"\"S3_UPLOADED_TIMESTAMP\":" + CoreUtil.getQuotedString(this.s3FileUploadedTimestamp) + ","+
	"\"FILE_TIMESTAMP\":" + CoreUtil.getQuotedString(this.fileTimeStamp)+ ","+
	"}"+
	"}";
	
	}
}	