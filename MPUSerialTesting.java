import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.model.VersionListing;
import com.amazonaws.services.s3.transfer.TransferManager;


public class MPUSerialTesting{

    private static File createSampleFile() throws IOException {
    /*    File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();*/
    	//String filePath = "D:\\Local Repo\\hicloud-sdk-java-testing\\s3+sdk+unitest\\pic.jpg";
    	String filePath = "D:\\workspace\\Local repo\\s3-sdk-unitest\\s3+sdk+unitest\\pic.jpg";
    	//String filePath = "/mnt/hgfs/file/pic.jpg";
    	File file = new File(filePath);

        return file;
    }
    
	private static void basicUploadPart(String bucketName, String fileName, String uploadID ,int partNumber) throws IOException
	{
		//System.out.println("basic Upload Part");

		UploadPartRequest config = new UploadPartRequest();
		config.setBucketName(bucketName);
		config.setKey(fileName);
		config.setPartNumber(partNumber); //part number
		config.setUploadId(uploadID);
		config.setFile(createSampleFile());
		config.setPartSize(6270544);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{			
			UploadPartResult result = s3.uploadPart(config);
			//System.out.println("partNumber: " + result.getPartNumber());
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
		System.gc();
	}
    
	private static int basicListParts(String bucketName, String fileName, String uploadID) throws IOException
	{
		//System.out.println("basic Upload Part");
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID);
		int count=0;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				count++;
				//System.out.println("partNumber: " + s.getPartNumber());
				//System.out.println("count: " + count);
			}
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
		System.gc();
		return count;
	}
	
	private static int MaxListParts(String bucketName, String fileName, String uploadID, int MaxPart) throws IOException
	{
		System.out.println("MaxListParts");
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withMaxParts(MaxPart);
		int count=0;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				count++;
				//System.out.println("partNumber: " + s.getPartNumber());
				//System.out.println("count: " + count);
			}
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
		System.gc();
		return count;
	}
	
	private static int MarkerListParts(String bucketName, String fileName, String uploadID, int Marker) throws IOException
	{
		System.out.println("Marker ListParts");
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withPartNumberMarker(Marker);
		int count=0;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				count++;
				//System.out.println("partNumber: " + s.getPartNumber());
				//System.out.println("count: " + count);
			}
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
		System.gc();
		return count;
	}

	private static void basicCompleteMPU(String bucketName, String fileName, String uploadID) throws IOException
	{
		//System.out.println("basic complete MPU");
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID);
		List<PartETag> list = new ArrayList<PartETag>(); //etag
		int counter=0;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			//list part
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				counter++;
				list.add(new PartETag (counter,s.getETag()));
				//System.out.println("partSize: " + s.getSize());
				//System.out.println("count: " + count);
			}
			
			CompleteMultipartUploadRequest config = new CompleteMultipartUploadRequest(bucketName,fileName,uploadID,list);
			CompleteMultipartUploadResult object = s3.completeMultipartUpload(config);
			/*System.out.println(object.getBucketName());
			System.out.println(object.getETag());
			System.out.println(object.getKey());
			System.out.println(object.getLocation());
	        System.out.println();*/
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
	
	private static void AbortMPU() throws IOException
	{
		String bucketName="chttest2";
		String fileName="hello.txt";

		ObjectMetadata meta = new ObjectMetadata();
		CannedAccessControlList acl = null;
		meta.setHeader("x-amz-color", "red");
		
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName,meta).withCannedACL(acl.AuthenticatedRead).withStorageClass(StorageClass.Standard);
	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            System.out.println("basic initial MPU");
            InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);      
			String UploadID = initRequest.getUploadId();
			
			System.out.println("Upload Part -1");
			basicUploadPart(bucketName,fileName,UploadID, 1);
			
			System.out.println("Upload Part -2");
			basicUploadPart(bucketName,fileName,UploadID, 2);
		
			int count = basicListParts(bucketName,fileName,UploadID);
			if(count!=2)
			{
				System.out.println("ERROR!!!\n Upload Part Error \n");
			}

			System.out.println("basic abort MPU");
			AbortMultipartUploadRequest abort = new AbortMultipartUploadRequest(bucketName,fileName,UploadID);
			s3.abortMultipartUpload(abort);
			
			System.out.println("Tear down..");
			s3.deleteObject(bucketName, fileName);
	        s3.deleteBucket(bucketName);
	        System.out.println("DONE");

            
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
	
	private static void CompleteMPU() throws IOException
	{
		String bucketName="chttest2";
		String fileName="hello.txt";

		ObjectMetadata meta = new ObjectMetadata();
		CannedAccessControlList Cannedacl = null;
		meta.setHeader("x-amz-color", "red");
		
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName,meta).withCannedACL(Cannedacl.AuthenticatedRead).withObjectMetadata(meta);

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            System.out.println("basic initial MPU");
            InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);      
			String UploadID = initRequest.getUploadId();
			
			System.out.println("Upload Part -1");
			basicUploadPart(bucketName,fileName,UploadID, 1);
			
			System.out.println("Upload Part -2");
			basicUploadPart(bucketName,fileName,UploadID, 2);
			
			System.out.println("Upload Part -3");
			basicUploadPart(bucketName,fileName,UploadID, 3);
		
			int count = MaxListParts(bucketName,fileName,UploadID,2); //maxPart
			if(count!=2)
			{
				System.out.println("ERROR!!!\n List Part with MaxPart Error \n");
			}
			
			count = MarkerListParts(bucketName,fileName,UploadID,2); //partNumberMarker
			if(count!=1)
			{
				System.out.println("ERROR!!!\n List Part with PartNumberMarker Error \n");
			}

			System.out.println("basic Complete MPU");
			basicCompleteMPU(bucketName,fileName,UploadID);
			
			System.out.println("Tear down..");
			s3.deleteObject(bucketName, fileName);
	        s3.deleteBucket(bucketName);
	        System.out.println("DONE");

            
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
	
	private static void basicUploadPartCopy(String sbucketName,String dbucketName,String sfileName, String dfileName, String uploadID,int count) throws IOException //with If-Match & If-Modify-Since
	{
		long firstByte = 1;
	    long lastByte = 6270544;
	    List<String> list = new ArrayList<String>(); //etag
	    list.add("339bbff383f4a3a129037a2029f62bf5");
	    Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate()); 
	    
	    //System.out.println("Date: \n" + date.getYear()+"/"+date.getMonth());
	    	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("basic Upload Part Copy...\n");
			CopyPartRequest request = new CopyPartRequest();
			request.setDestinationBucketName(dbucketName);
			request.setDestinationKey(dfileName);
			request.setUploadId(uploadID);
			request.setPartNumber(count);
			request.setSourceBucketName(sbucketName);
			request.setSourceKey(sfileName);
			request.setMatchingETagConstraints(list);
			request.setModifiedSinceConstraint(date);
				
			CopyPartResult result = s3.copyPart(request);
			/*System.out.println(result.getPartNumber());
			System.out.println(result.getETag());
			System.out.println(result.getLastModifiedDate());*/
		}
		catch (AmazonServiceException ase) 
		{
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
	
	private static void NonUploadPartCopy(String sbucketName,String dbucketName,String sfileName, String dfileName, String uploadID,int count) throws IOException //with If-Non-Match & If-Nonmodify-Since
	{
		long firstByte = 1;
	    long lastByte = 6270544;
	    List<String> list = new ArrayList<String>(); //etag
	    list.add("339bbff383f4a3a129037a2029f62bf4"); //wrong etag
	    Date date = new Date();
		date.setYear(date.getYear());
		date.setMonth(date.getMonth()+2);
		date.setDate(date.getDate());
	    
	    //System.out.println("Date: \n" + date.getYear()+"/"+date.getMonth());
	    	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("basic Upload Part Copy...\n");
			CopyPartRequest request = new CopyPartRequest();
			request.setDestinationBucketName(dbucketName);
			request.setDestinationKey(dfileName);
			request.setUploadId(uploadID);
			request.setPartNumber(count);
			request.setSourceBucketName(sbucketName);
			request.setSourceKey(sfileName);
			request.setNonmatchingETagConstraints(list);
			request.setUnmodifiedSinceConstraint(date);
				
			CopyPartResult result = s3.copyPart(request);
			/*System.out.println(result.getPartNumber());
			System.out.println(result.getETag());
			System.out.println(result.getLastModifiedDate());*/
		}
		catch (AmazonServiceException ase) 
		{
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
	
	private static void CopyPart() throws IOException
	{
		String bucketName="chttest2";
		String SbucketName="sregion"; //source bucket
		String fileName="hello.txt";
		String SfileName="world.txt"; //source file
		ObjectMetadata metadata = new ObjectMetadata() ;
		metadata.setContentLength(6270544);
		
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Creating bucket"+ "\n");
            s3.createBucket(bucketName);
            s3.createBucket(SbucketName);
            
            System.out.println("Put source Object"+ "\n");
            PutObjectResult object = s3.putObject(new PutObjectRequest(SbucketName, SfileName, createSampleFile()).withFile(createSampleFile()).withMetadata(metadata));
            System.out.println(object.getETag());
            
            System.out.println("basic initial MPU");
            InitiateMultipartUploadResult initRequest = s3.initiateMultipartUpload(config);      
			String UploadID = initRequest.getUploadId();
			
			basicUploadPartCopy(SbucketName,bucketName,SfileName,fileName,UploadID,1); //with If-Match & If-Modify-Since
			NonUploadPartCopy(SbucketName,bucketName,SfileName,fileName,UploadID,2); //with If-Non-Match & If-Nonmodify-Since
			
			AbortMultipartUploadRequest abort = new AbortMultipartUploadRequest(bucketName,fileName,UploadID);
			s3.abortMultipartUpload(abort);
			
			System.out.println("Tear down..");
			s3.deleteObject(bucketName, fileName);
			s3.deleteObject(SbucketName, SfileName);
	        s3.deleteBucket(SbucketName);
	        s3.deleteBucket(bucketName);
	        System.out.println("DONE");

            
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
	
	private static int PrefixListMPUs(String bucketName, String Prefix, String Delimiter) throws IOException //Prefix & Delimeter
	{
		System.out.println("basic list MPUs");
		int count=0;

		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withPrefix(Prefix).withDelimiter(Delimiter);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			MultipartUploadListing result = s3.listMultipartUploads(request);
			for(MultipartUpload s : result.getMultipartUploads())
			{
				count++;
				//System.out.println("count: "+ count);
				//System.out.println(s.getKey());
				//System.out.println(s.getUploadId());
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
		return count;
	}
	
	private static int MaxListMPUs(String bucketName, String KeyMarker, int MaxUpload) throws IOException //MaxUpload & KeyMarker
	{
		System.out.println("list MPUs with MaxUpload & KeyMarker");
		int count=0;
		
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withMaxUploads(MaxUpload).withKeyMarker(KeyMarker);
	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			MultipartUploadListing result = s3.listMultipartUploads(request);
			for(MultipartUpload s : result.getMultipartUploads())
			{
				count++;
				//System.out.println("count: "+ count);
				//System.out.println(s.getKey());
				//System.out.println(s.getUploadId());
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
		return count;
	}
	
	private static int MarkerListMPUs(String bucketName, String IDMarker, String fileName) throws IOException //Upload ID Marker
	{
		System.out.println("list MPUs with Upload ID Marker");
		int count=0;
		
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withKeyMarker(fileName).withUploadIdMarker(IDMarker);
	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			MultipartUploadListing result = s3.listMultipartUploads(request);
			for(MultipartUpload s : result.getMultipartUploads())
			{
				count++;
				System.out.println("count: "+ count);
				System.out.println(s.getKey());
				System.out.println(s.getUploadId());
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
		return count;
	}
	
	private static void ListMPUs() throws IOException
	{
		String bucketName="chttestg";
		String fileName1="photos/2006/January/sample.jpg";
		String fileName2="photos/2006/February/sample.jpg"; 
		String fileName3="photos/2006/March/sample.jpg"; 
		String fileName4="videos/2006/March/sample.wmv"; 
		String fileName5="sample.jpg"; 

		int count=0;
		
		InitiateMultipartUploadRequest config1 = new InitiateMultipartUploadRequest(bucketName,fileName1);
		InitiateMultipartUploadRequest config2 = new InitiateMultipartUploadRequest(bucketName,fileName2);
		InitiateMultipartUploadRequest config3 = new InitiateMultipartUploadRequest(bucketName,fileName3);
		InitiateMultipartUploadRequest config4 = new InitiateMultipartUploadRequest(bucketName,fileName4);
		InitiateMultipartUploadRequest config5 = new InitiateMultipartUploadRequest(bucketName,fileName5);

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Creating bucket"+ "\n");
            s3.createBucket(bucketName);  
            
            System.out.println("basic initial MPU");
            InitiateMultipartUploadResult initRequest1 = s3.initiateMultipartUpload(config1);     
            InitiateMultipartUploadResult initRequest2 = s3.initiateMultipartUpload(config2);
            InitiateMultipartUploadResult initRequest3 = s3.initiateMultipartUpload(config3);   
            InitiateMultipartUploadResult initRequest4 = s3.initiateMultipartUpload(config4);   
            InitiateMultipartUploadResult initRequest5 = s3.initiateMultipartUpload(config5);   
			String UploadID1 = initRequest1.getUploadId();
			String UploadID2 = initRequest2.getUploadId();
			String UploadID3 = initRequest3.getUploadId();
			String UploadID4 = initRequest4.getUploadId();
			String UploadID5 = initRequest5.getUploadId();
			
		/*	count= PrefixListMPUs(bucketName, "/", "photos/2006/"); //Delimiter & prefix
			if(count!=0)
			{
				System.out.println("ERROR!!!\n List MPUs (Delimiter + Prefix) Error \n");
			}
			
			count = MaxListMPUs(bucketName, "photos/2006/January/sample.jpg", 2); //KeyMarker & MaxUpload
			if(count!=2)
			{
				System.out.println("ERROR!!!\n List MPUs (KeyMarker + MaxUpload) Error \n");
			}*/
			
			System.out.println("fileName: " + fileName1 + "& upload id: " + UploadID1);
			count = MarkerListMPUs(bucketName, UploadID1,fileName1); //Upload ID Marker + Key Marker
			System.out.println("counter: "+ count);
			if(count!=3)
			{
				System.out.println("ERROR!!!\n List MPUs (Upload ID Marker + Key Marker) Error \n");
				//System.out.println("ERROR!!!\n Wait To FIX!! \n");
			}
			
			System.out.println("Tear down..");
			AbortMultipartUploadRequest abort1 = new AbortMultipartUploadRequest(bucketName,fileName1,UploadID1);
			AbortMultipartUploadRequest abort2 = new AbortMultipartUploadRequest(bucketName,fileName2,UploadID2);
			AbortMultipartUploadRequest abort3 = new AbortMultipartUploadRequest(bucketName,fileName3,UploadID3);
			AbortMultipartUploadRequest abort4 = new AbortMultipartUploadRequest(bucketName,fileName4,UploadID4);
			AbortMultipartUploadRequest abort5 = new AbortMultipartUploadRequest(bucketName,fileName5,UploadID5);
			s3.abortMultipartUpload(abort1);
			s3.abortMultipartUpload(abort2);
			s3.abortMultipartUpload(abort3);
			s3.abortMultipartUpload(abort4);
			s3.abortMultipartUpload(abort5);			
			s3.deleteObject(bucketName, fileName1);
			s3.deleteObject(bucketName, fileName2);
			s3.deleteObject(bucketName, fileName3);
			s3.deleteObject(bucketName, fileName4);
			s3.deleteObject(bucketName, fileName5);
	        s3.deleteBucket(bucketName);
	        System.out.println("DONE");

            
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
		
		/* 
		 * test 1. PutBucket
		 *      2. Initial MPU with metadata + CannedACL + StorageClass
		 *      3. UploadParts 
		 *      4. basic ListParts
		 */
		AbortMPU();
		
		/* 
		 * test 1. PutBucket
		 *      2. Initial MPU with metadata + CannedACL + StorageClass + ACL
		 *      3. UploadParts 
		 *      4. basic ListParts + Parameters: maxPart & partNumberMarker
		 *      5. Complete MPU 
		 */
		//CompleteMPU();
		
		/* 
		 * test 1. PutBucket
		 *      2. Initial MPU
		 *      3. UploadPartCopy & parameters
		 *      4. Abort MPU
		 */
		//CopyPart();
		
		/* 
		 * test 1. PutBucket
		 *      2. Initial MPU
		 *      3. List MPUs & parameters
		 *      4. Abort MPU
		 */

		//ListMPUs(); 

		System.gc(); //grabage collection
		
		System.out.println("MPUSerialTest Over");
	}
		
}
