package com.springrest.servicerest.services;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.MessageFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.springrest.servicerest.Core.BasicProducer;
import com.springrest.servicerest.Core.GenericParser;
import com.springrest.servicerest.Core.Constants;
import com.springrest.servicerest.Core.CoreUtil;
import com.springrest.servicerest.Core.KeySchema;
import com.springrest.servicerest.entities.WorkflowTracker;
import com.springrest.servicerest.model.DocumentRequest;
import org.xml.sax.InputSource;
import com.springrest.servicerest.model.WorkflowTrackerDto;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;

import java.text.SimpleDateFormat;


@Service
public class DocumentRequestServiceImpl implements DocumentRequestDao	{
	
	@Autowired
	private BasicProducer basicProducer;
	
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private  String fileTimeStamp;
	
	private  String s3fileTimeStamp;
	
	private  AmazonS3 s3Client;
	
	private static   Logger log = LoggerFactory.getLogger(DocumentRequest.class);
	
	@Autowired
	GenericParser genericParser;
	
	
	
	
	public  void manipulateData(DocumentRequest[] DocumentRequest) throws MalformedURLException, IOException {
		
		 for (DocumentRequest documentRequest : DocumentRequest) {
	            // Extract the necessary data from the request
	            String entityId = documentRequest.getEntityId();
	            String documentType = documentRequest.getDocumentType();
	            String locationUrl = documentRequest.getLocationUrl();
	            
	            WorkflowTrackerDto dto = new WorkflowTrackerDto();
	            
	           dto.setEntityId(entityId); 
	       //  Step-1 --> url validation
	            
//	            URL url = new URL(locationUrl);
//	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//	            connection.setRequestMethod("POST");
	            
	            try {
	            		URL url = new URL(returnS3Url(locationUrl));
	            		
	            //  Step-2 -->   based on document type HL7v2 - Use terser to extract the MSH-7 and format it.
	         //based on document type CCD- Use Xpaht to extract the effective time and format it
	
	            		
	            		if(documentType == "HL7v2") {
	            			String message = loadDocument(url);
	            			
	            			if (!StringUtils.isEmpty(message)) {
	            				message = message.replaceAll("[\\x0b\\x1c]", "");
	            				message = fixPD1(message.trim());
	            			}
	            			
	            			PipeParser parser = genericParser.getContext().getPipeParser();
	            			Message v2MessageObj = parser.parse(message);
	            			Terser terser = new Terser(v2MessageObj);
	            			if (terser.get("/.MSH-7") != null) {
	            				String date1 = terser.get("/.MSH-7");
	            				try {
	            					fileTimeStamp = CoreUtil.getDateAndTimeForMessageGenarationField(date1);
		            				dto.setFileTimeStamp(fileTimeStamp);
		            			} catch (Exception e) {
		            				log.error(MessageFormat.format(">>>>> Failed to parse effective date for {0}", dto.getEntityId()));
		            				log.error(Constants.ERROR, e);
		            				}
	            				
	            			
	            			}
	            			
	            			
	            		}
	            		
	            		if(documentType == "CCD") {
	            			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            		      DocumentBuilder builder = factory.newDocumentBuilder();
	            		      Document ccdDocument = builder.parse(new InputSource(locationUrl));
	            			XPath xPath = XPathFactory.newInstance().newXPath();
	            			String effectiveTime = "//*[local-name()='//ClinicalDocument']/*[local-name()='effectiveTime']/@value";
	            			try {
	            				fileTimeStamp = CoreUtil.getDateAndTimeForMessageGenarationField(effectiveTime);
	            				dto.setFileTimeStamp(fileTimeStamp);
	            			} catch (Exception e) {
	            				log.error(MessageFormat.format(">>>>> Failed to parse effective date for {0}", dto.getEntityId()));
	            				log.error(Constants.ERROR, e);
	            				}
	            			
	            			
	            		}
	            		
	            	//  Step-3 --> getS3TimeStamp
	            		
	            		try {
	            			s3fileTimeStamp = CoreUtil.getDateAndTimeForMessageGenarationField(s3fileTimeStamp);
            				dto.setS3FileUploadedTimestamp(s3fileTimeStamp);
	            		}catch (Exception e) {
            				log.error(MessageFormat.format(">>>>> Failed to parse effective date for {0}", dto.getEntityId()));
            				log.error(Constants.ERROR, e);
            				}
	            		
	            		
	            }catch(Exception e) {
	            	System.out.println("The message is: "+ e.getMessage());
	            }finally {
	    			//	 workflowTrackerService.saveWrkFlwTrk(workflowTrackerDTO);
	    			WorkflowTracker workflowTracker = dto.convertDtoToEntity();
	    			String kafkaTopicPrefix = "KPI_TEST";
	    			basicProducer.runStringStringProducer(kafkaTopicPrefix + "." + "UDM_LANDING.WORKFLOW_TRACKER",
	    			new KeySchema(workflowTracker.getEntityId(), "struct", false, "benchmark-workflow-tracker-key")
	    					.toSchemaString(),
	    			workflowTracker.toSchemaString());
	    			

	    		}
	            	
	       
	            
	            
	       		 }
		
		}

	public  String returnS3Url(String s3Url) throws UnsupportedEncodingException {
		String finalUrl = "";
		try {
			AmazonS3URI amazonS3URI = new AmazonS3URI(s3Url);
			String s3BucketName = amazonS3URI.getBucket();

			// setting expiration time
			java.util.Date expiration = new java.util.Date();
			long expTimeMillis = expiration.getTime();
			expTimeMillis += 1160000;
			expiration.setTime(expTimeMillis);
			String fileName = s3Url.replace("https://" + s3BucketName + ".s3.amazonaws.com/", "");
			fileName = URLDecoder.decode(fileName, "UTF-8");

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(s3BucketName,
					fileName).withMethod(HttpMethod.GET).withExpiration(expiration);
			URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
			finalUrl = url.toString();
		} catch (AmazonServiceException e) {
			log.error(Constants.ERROR, e);
			throw e;
		} catch (SdkClientException e) {
			log.error(Constants.ERROR, e);
			throw e;
		}
		return finalUrl;
	}
	
	private  String loadDocument(URL url) throws FileNotFoundException, Exception {
		int count = 0;
		int maxTries = 3;
		while (true) {
			try (InputStreamReader in = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
				BufferedReader br = new BufferedReader(in);
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append("\r\n");
				}
				return sb.toString();
			} catch (Exception e) {
				Thread.currentThread().sleep(3000l);
				if (count >= maxTries)
					throw e;
				count++;
			}
		}

	}
	
	private  String fixPD1(String message) {
		int a = message.indexOf("\nPID|");
		int b = message.indexOf("\r", a);
		int c = message.indexOf("\nPD1|");
		int d = message.indexOf("\r", c);

		if (b + 1 != c && c > 0 && d > 0) {
			String newString = message.substring(0, b + 1) + message.substring(c, d + 1) + message.substring(b + 1, c)
					+ message.substring(d + 1 + 1);
			return newString;
		}
		return message;
	}

	public String s3TimeStamp(String bucketName, String output4){
        AmazonS3 s3Client = null;
        s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(new InstanceProfileCredentialsProvider(false))
        .withRegion(Regions.US_EAST_1).build();

        S3Object fullObject = s3Client.getObject(new GetObjectRequest(bucketName,output4.trim()));
        ObjectMetadata objectMetadata = fullObject.getObjectMetadata();
        Date lastModified = (Date)objectMetadata.getLastModified();
    
          

        return lastModified.toString();
    }
}