package test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import nagative.putBucket;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;

public class Neg_MPUSerialTesting{
	
	private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
    
 
	private static void basicPutBucket() throws IOException
	{
		System.out.println("basic put bucket");
	    	
		String bucketName="chttest3";
			
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
	        s3.createBucket(bucketName);
	            
	        System.out.println("Listing buckets");
	        for (Bucket bucket : s3.listBuckets()) 
	        {
	        	System.out.println(" - " + bucket.getName());
	        }
        	
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void basicInitialMPU() throws IOException
	{
		System.out.println("basic initial MPU");
		String bucketName="chttest3";
		String fileName="6G";
		ObjectMetadata meta = new ObjectMetadata();
		meta.setHeader("x-amz-color", "red");
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);
		
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);      
			System.out.println(initRequest.getUploadId());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void InitialMPU_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("InitialMPU_403_InvalidAccessKeyId");
		String bucketName="chttest3";
		String fileName="6G";
		ObjectMetadata meta = new ObjectMetadata();
		meta.setHeader("x-amz-color", "red");
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);      
			System.out.println(initRequest.getUploadId());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void InitialMPU_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("InitialMPU_403_InvalidSecretKeyId");
		String bucketName="chttest3";
		String fileName="6G";
		ObjectMetadata meta = new ObjectMetadata();
		meta.setHeader("x-amz-color", "red");
	
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
			InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);      
			System.out.println(initRequest.getUploadId());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	//InitialMPU**********************************************************************************************************************************
	
	private static void ListMPUs_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("ListMPUs_403_InvalidAccessKeyId");
		String bucketName="chttest3";
		int maxUploadsInt = 2;
		String delimiter = "/"; 
		String uploadIdMarker = "P3N0DC2XESBTJYVIX4MPPNEELP0G2OP6UWJ26B6AEJTK4A8VO6D9EXRB0Z";
		
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withUploadIdMarker(uploadIdMarker).withKeyMarker("ccchello.txt");
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			MultipartUploadListing result = s3.listMultipartUploads(request);
			//System.out.println(result.getBucketName());
			for(MultipartUpload s : result.getMultipartUploads())
			{
				System.out.println(s.getKey());
				System.out.println(s.getUploadId());
				//System.out.println(s.getStorageClass());
			}
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void ListMPUs_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("ListMPUs_403_InvalidSecretKeyId");
		String bucketName="chttest3";
		int maxUploadsInt = 2;
		String delimiter = "/"; 
		String uploadIdMarker = "P3N0DC2XESBTJYVIX4MPPNEELP0G2OP6UWJ26B6AEJTK4A8VO6D9EXRB0Z";
		
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withUploadIdMarker(uploadIdMarker).withKeyMarker("ccchello.txt");
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
			MultipartUploadListing result = s3.listMultipartUploads(request);
			//System.out.println(result.getBucketName());
			for(MultipartUpload s : result.getMultipartUploads())
			{
				System.out.println(s.getKey());
				System.out.println(s.getUploadId());
				//System.out.println(s.getStorageClass());
			}
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	//ListMPUs**********************************************************************************************************************************
	
	private static void UploadPart_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("UploadPart_403_InvalidAccessKeyId");
		String bucketName="chttest3";
		String fileName="world.txt";

		String uploadID = "OXB19RHG3LIIYB442BP7C2HBMV54BK22A30SINOU77EC6AVE1E4II5ASJ7";  //hello


		UploadPartRequest config = new UploadPartRequest();
		
		config.setBucketName(bucketName);
		config.setKey(fileName);
		config.setPartNumber(6); //part number
		config.setUploadId(uploadID);
		config.setFile(createSampleFile());
		//config.setFile(file);
		config.setPartSize(5242880); //content-length


		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		
		try
		{			
			UploadPartResult result = s3.uploadPart(config);
			System.out.println(result.getPartNumber());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
    
    private static void UploadPart_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("UploadPart_403_InvalidSecretKeyId");
		String bucketName="chttest3";
		String fileName="world.txt";

		
		String uploadID = "OXB19RHG3LIIYB442BP7C2HBMV54BK22A30SINOU77EC6AVE1E4II5ASJ7";  //hello

		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		//long fileOffset = 25;
		//String filePath = "D:/5M";
		//File file = new File(filePath);
		

		UploadPartRequest config = new UploadPartRequest();
		
		config.setBucketName(bucketName);
		config.setKey(fileName);
		config.setPartNumber(6); //part number
		config.setUploadId(uploadID);
		config.setFile(createSampleFile());
		//config.setFile(file);
		config.setPartSize(5242880); //content-length



		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		
		try
		{			
			UploadPartResult result = s3.uploadPart(config);
			System.out.println(result.getPartNumber());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
    private static void UploadPart_NoUploadId() throws IOException
	{
		System.out.println("UploadPart_NoUploadId");
		String bucketName="chttest3";
		String fileName="world.txt";

		//String md5Digest="aSsJ8P/c05f2r0JDoSWbHg==";


		//String md5Digest="aSsJ8P/c05f2r0JDoSWbHg==";
		String uploadID = "OXB19RHG3LIIYB442BP7C2HBMV54BK22A30SINOU77EC6AVE1E4II5ASJ7";  //hello

		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		//long fileOffset = 25;
		//String filePath = "D:/5M";
		//File file = new File(filePath);
		

		UploadPartRequest config = new UploadPartRequest();
		
		config.setBucketName(bucketName);
		config.setKey(fileName);
		config.setPartNumber(6); //part number
		config.setUploadId(uploadID);
		config.setFile(createSampleFile());
		//config.setFile(file);
		config.setPartSize(5242880); //content-length
		//config.setMd5Digest(md5Digest);
		//config.setLastPart(true);

	

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		
		try
		{			
			UploadPartResult result = s3.uploadPart(config);
			System.out.println(result.getPartNumber());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
    //Upload Parts**********************************************************************************************************************************
	
        
    private static void ListParts_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("ListParts_403_InvalidAccessKeyId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withMaxParts(3);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withPartNumberMarker(5);
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				System.out.println(s.getPartNumber());
				System.out.println(s.getLastModified());
				System.out.println(s.getSize());
				System.out.println(s.getETag());
			}
			//System.out.println(result.getParts());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void ListParts_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("ListParts_403_InvalidSecretKeyId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withMaxParts(3);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withPartNumberMarker(5);
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				System.out.println(s.getPartNumber());
				System.out.println(s.getLastModified());
				System.out.println(s.getSize());
				System.out.println(s.getETag());
			}
			//System.out.println(result.getParts());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void ListParts_NoUploadId() throws IOException
	{
		System.out.println("ListParts_NoUploadId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withMaxParts(3);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withPartNumberMarker(5);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				System.out.println(s.getPartNumber());
				System.out.println(s.getLastModified());
				System.out.println(s.getSize());
				System.out.println(s.getETag());
			}
			//System.out.println(result.getParts());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	//List Parts**********************************************************************************************************************************
	
	private static void UploadPartCopy_403_InvalidAccessKeyId() throws IOException
    {
		System.out.println("UploadPartCopy_403_InvalidAccessKeyId");
		String sbucketName = "chttest3";
    	String dbucketName = "chttest4";
    	String sfileName = "world.txt";
    	String dfileName = "hello.txt";
    	String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
    	long firstByte = 1;
    	long lastByte = 322122547 ;
    	List<String> list = new ArrayList<String>(); //etag
    	list.add("692b09f0ffdcd397f6af4243a1259b1a");
        Date date = new Date();
        date.setYear(date.getYear());
        date.setMonth(date.getMonth()+2);
        date.setDate(date.getDate());        
    	
        System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
            System.out.println("basic Upload Part Copy...\n");
			CopyPartRequest request = new CopyPartRequest();
			request.setDestinationBucketName(dbucketName);
			request.setDestinationKey(dfileName);
			request.setUploadId(uploadID);
			request.setPartNumber(8);
			request.setSourceBucketName(sbucketName);
			request.setSourceKey(sfileName);
			request.setFirstByte(firstByte);
			request.setLastByte(lastByte);
			//request.setMatchingETagConstraints(list);
			//request.setNonmatchingETagConstraints(list);
			//request.setModifiedSinceConstraint(date);
			//request.setUnmodifiedSinceConstraint(date);
			//request.setRequestCredentials(credentials);
			
			CopyPartResult result = s3.copyPart(request);
			System.out.println(result.getPartNumber());
			System.out.println(result.getETag());
			System.out.println(result.getLastModifiedDate());
		}
		catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
    
    private static void UploadPartCopy_403_InvalidSecretKeyId() throws IOException
    {
    	System.out.println("UploadPartCopy_403_InvalidSecretKeyId");
    	String sbucketName = "chttest3";
    	String dbucketName = "chttest4";
    	String sfileName = "world.txt";
    	String dfileName = "hello.txt";
    	String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
    	long firstByte = 1;
    	long lastByte = 322122547 ;
    	List<String> list = new ArrayList<String>(); //etag
    	list.add("692b09f0ffdcd397f6af4243a1259b1a");
        Date date = new Date();
        date.setYear(date.getYear());
        date.setMonth(date.getMonth()+2);
        date.setDate(date.getDate());        
    	
        System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
            System.out.println("basic Upload Part Copy...\n");
			CopyPartRequest request = new CopyPartRequest();
			request.setDestinationBucketName(dbucketName);
			request.setDestinationKey(dfileName);
			request.setUploadId(uploadID);
			request.setPartNumber(8);
			request.setSourceBucketName(sbucketName);
			request.setSourceKey(sfileName);
			request.setFirstByte(firstByte);
			request.setLastByte(lastByte);
			//request.setMatchingETagConstraints(list);
			//request.setNonmatchingETagConstraints(list);
			//request.setModifiedSinceConstraint(date);
			//request.setUnmodifiedSinceConstraint(date);
			//request.setRequestCredentials(credentials);
			
			CopyPartResult result = s3.copyPart(request);
			System.out.println(result.getPartNumber());
			System.out.println(result.getETag());
			System.out.println(result.getLastModifiedDate());
		}
		catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
    //PartCopy**********************************************************************************************************************************
	
	
    private static void CompleteMPU_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("CompleteMPU_403_InvalidAccessKeyId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		List<PartETag> list = new ArrayList<PartETag>(); //etag
		list.add(new PartETag (1,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (2,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (3,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (4,"22577911e88af39f79409e6de8eed4d9"));
		list.add(new PartETag (5,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (6,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (7,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (8,"692b09f0ffdcd397f6af4243a1259b1e"));
		
		CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest(bucketName,fileName,uploadID,list);
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			CompleteMultipartUploadResult result = s3.completeMultipartUpload(request);
			System.out.println(result.getBucketName());
			System.out.println(result.getETag());
			System.out.println(result.getKey());
			System.out.println(result.getLocation());
			//System.out.println(result.getParts());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void CompleteMPU_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("CompleteMPU_403_InvalidSecretKeyId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		List<PartETag> list = new ArrayList<PartETag>(); //etag
		list.add(new PartETag (1,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (2,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (3,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (4,"22577911e88af39f79409e6de8eed4d9"));
		list.add(new PartETag (5,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (6,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (7,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (8,"692b09f0ffdcd397f6af4243a1259b1e"));
		
		CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest(bucketName,fileName,uploadID,list);
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
			CompleteMultipartUploadResult result = s3.completeMultipartUpload(request);
			System.out.println(result.getBucketName());
			System.out.println(result.getETag());
			System.out.println(result.getKey());
			System.out.println(result.getLocation());
			//System.out.println(result.getParts());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void CompleteMPU_NoUploadId() throws IOException
	{
		System.out.println("CompleteMPU_NoUploadId()");
		String bucketName="chttest3";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		List<PartETag> list = new ArrayList<PartETag>(); //etag
		list.add(new PartETag (1,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (2,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (3,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (4,"22577911e88af39f79409e6de8eed4d9"));
		list.add(new PartETag (5,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (6,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (7,"692b09f0ffdcd397f6af4243a1259b1e"));
		list.add(new PartETag (8,"692b09f0ffdcd397f6af4243a1259b1e"));
		
		CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest(bucketName,fileName,uploadID,list);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			CompleteMultipartUploadResult result = s3.completeMultipartUpload(request);
			System.out.println(result.getBucketName());
			System.out.println(result.getETag());
			System.out.println(result.getKey());
			System.out.println(result.getLocation());
			//System.out.println(result.getParts());
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	//CompleteMPU**********************************************************************************************************************************
	
	
	private static void AbortMPU_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("AbortMPU_403_InvalidAccessKeyId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		//String uploadID = "LE5JS2K6C208JU7ZX1QD2TVRWXOWWF4VNG7LE7TFIX5SYNG4HLOGW9CLAD"; //hello
		String uploadID = "LM17F04FYDEROA6770ZURRUS3FN8XSSQY0SXJGDCDBW1ELEUEXRBE6EXF6";
		
		AbortMultipartUploadRequest request = new AbortMultipartUploadRequest(bucketName,fileName,uploadID);
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			s3.abortMultipartUpload(request);
			
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void AbortMPU_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("AbortMPU_403_InvalidSecretKeyId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		//String uploadID = "LE5JS2K6C208JU7ZX1QD2TVRWXOWWF4VNG7LE7TFIX5SYNG4HLOGW9CLAD"; //hello
		String uploadID = "LM17F04FYDEROA6770ZURRUS3FN8XSSQY0SXJGDCDBW1ELEUEXRBE6EXF6";
		
		AbortMultipartUploadRequest request = new AbortMultipartUploadRequest(bucketName,fileName,uploadID);
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
			s3.abortMultipartUpload(request);
			
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	
	private static void AbortMPU_NoUploadId() throws IOException
	{
		System.out.println("AbortMPU_NoUploadId");
		String bucketName="chttest3";
		String fileName="hello.txt";
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		//String uploadID = "LE5JS2K6C208JU7ZX1QD2TVRWXOWWF4VNG7LE7TFIX5SYNG4HLOGW9CLAD"; //hello
		String uploadID = "LM17F04FYDEROA6770ZURRUS3FN8XSSQY0SXJGDCDBW1ELEUEXRBE6EXF6";
		
		AbortMultipartUploadRequest request = new AbortMultipartUploadRequest(bucketName,fileName,uploadID);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			s3.abortMultipartUpload(request);
			
	        System.out.println();
	    }
		catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	        System.out.println("Error Message:    " + ase.getMessage());
	        System.out.println("HTTP Status Code: " + ase.getStatusCode());
	        System.out.println("AWS Error Code:   " + ase.getErrorCode());
	        System.out.println("Error Type:       " + ase.getErrorType());
	        System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
	//AbortMPU**********************************************************************************************************************************
	
    
    private static void Teardown() throws IOException
    {
    	System.out.println("Tear down..");
    	String bucketName="chttest3";

		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{			
			s3.deleteBucket(bucketName);
		}
		catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
    
	
    
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
	    basicPutBucket();
		//Initial MPU
  		InitialMPU_403_InvalidAccessKeyId();
		InitialMPU_403_InvalidSecretKeyId();
		
		//List MPUs
		ListMPUs_403_InvalidAccessKeyId();
	    ListMPUs_403_InvalidSecretKeyId();
		
	    //Upload Parts
	    UploadPart_403_InvalidAccessKeyId();
		UploadPart_403_InvalidSecretKeyId();
		UploadPart_NoUploadId();
	    
	    //List Parts
	    ListParts_403_InvalidAccessKeyId();
		ListParts_403_InvalidSecretKeyId();
		ListParts_NoUploadId();
	    
	    //PartCopy
	    UploadPartCopy_403_InvalidAccessKeyId();
		UploadPartCopy_403_InvalidSecretKeyId();
	    
	    //CompleteMPU
	    CompleteMPU_403_InvalidAccessKeyId();
		CompleteMPU_403_InvalidSecretKeyId();
		CompleteMPU_NoUploadId();
	    
	    //AbortMPU
	    AbortMPU_403_InvalidAccessKeyId();
		AbortMPU_403_InvalidSecretKeyId();
		AbortMPU_NoUploadId();
	    
	    Teardown();
		System.out.println("Neg_MPUSerialTest Over");

		
	}
		
}