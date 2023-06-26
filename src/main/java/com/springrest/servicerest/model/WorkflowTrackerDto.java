package com.springrest.servicerest.model;

import java.io.Serializable;
import java.util.Date;

import com.springrest.servicerest.entities.WorkflowTracker;




public class WorkflowTrackerDto implements Serializable {
	
	
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


		public WorkflowTracker convertDtoToEntity() {
			WorkflowTracker entity = new WorkflowTracker();
			entity.setEntityId(entityId);
			entity.setS3FileUploadedTimestamp(s3FileUploadedTimestamp);
			entity.setFileTimeStamp(fileTimeStamp);
			return entity;
		}
}
