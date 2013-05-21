package nagative;

import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;


public class Neg_ListMPUs{
	
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
	
	private static void basicListMPUs() throws IOException
	{
		System.out.println("basic list MPUs");
		String bucketName="chttest";
		int maxUploadsInt = 2;
		String delimiter = "/"; 
		String uploadIdMarker = "P3N0DC2XESBTJYVIX4MPPNEELP0G2OP6UWJ26B6AEJTK4A8VO6D9EXRB0Z";
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withMaxUploads(maxUploadsInt);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withDelimiter(delimiter);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withKeyMarker("sample");
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withPrefix("photos");
		ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withUploadIdMarker(uploadIdMarker).withKeyMarker("ccchello.txt");
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
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
	
	private static void basicListMPUs_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("basic list MPUs");
		String bucketName="chttest";
		int maxUploadsInt = 2;
		String delimiter = "/"; 
		String uploadIdMarker = "P3N0DC2XESBTJYVIX4MPPNEELP0G2OP6UWJ26B6AEJTK4A8VO6D9EXRB0Z";
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withMaxUploads(maxUploadsInt);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withDelimiter(delimiter);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withKeyMarker("sample");
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withPrefix("photos");
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
	
	private static void basicListMPUs_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("basic list MPUs");
		String bucketName="chttest";
		int maxUploadsInt = 2;
		String delimiter = "/"; 
		String uploadIdMarker = "P3N0DC2XESBTJYVIX4MPPNEELP0G2OP6UWJ26B6AEJTK4A8VO6D9EXRB0Z";
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withMaxUploads(maxUploadsInt);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withDelimiter(delimiter);
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withKeyMarker("sample");
		//ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName).withPrefix("photos");
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
		basicListMPUs_403_InvalidAccessKeyId();
		basicListMPUs_403_InvalidSecretKeyId();
		Teardown();
		basicListMPUs();
	}
		
}