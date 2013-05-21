package nagative;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;

public class Neg_completeMPU{
	
	private static void basicPutBucket() throws IOException
	{
		System.out.println("basic put bucket");
	    	
		String bucketName="chttest";
		String fileName="world.txt";
			
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
	
	private static void CompleteMPU_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("basic complete MPU");
		String bucketName="chttest";
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
		System.out.println("basic complete MPU");
		String bucketName="chttest";
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
		System.out.println("basic complete MPU");
		String bucketName="chttest";
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
	
	private static void Teardown() throws IOException
    {
    	System.out.println("Tear down..");
    	String bucketName="chttest";

		
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
		CompleteMPU_403_InvalidAccessKeyId();
		CompleteMPU_403_InvalidSecretKeyId();
		CompleteMPU_NoUploadId();
		Teardown();
	}
		
}