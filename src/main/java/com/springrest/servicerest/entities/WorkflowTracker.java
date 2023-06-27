package com.springrest.servicerest.entities;


import java.io.Serializable;

import com.springrest.servicerest.Core.CoreUtil;




public class WorkflowTracker implements Serializable 
{

	
	private String entityId;
	
	
	private String s3FileUploadedTimestamp;

	
	
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
				"\"type\":\"struct\","+
				"\"fields\":["+
					"{"+
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
						"],"+
					"\"optional\":false,"+
					"\"name\":\"KPI_PROD_CCD_213.UDM_LANDING.WORKFLOW_TRACKER.Value\""+
						"},"+
						"\"payload\":{"+
						"\"ENTITY_ID\":" + CoreUtil.getQuotedString(this.entityId) + ","+
						"\"S3_UPLOADED_TIMESTAMP\":" + CoreUtil.getQuotedString(this.s3FileUploadedTimestamp) + ","+
						"\"FILE_TIMESTAMP\":" + CoreUtil.getQuotedString(this.fileTimeStamp)+ ","+
						"\"__table\":\"WORKFLOW_TRACKER\","+
						"\"__deleted\":\"false\""+
						"}"+
						"}";
	
	}
}	