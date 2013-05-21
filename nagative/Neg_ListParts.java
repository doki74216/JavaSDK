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
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;

public class Neg_ListParts{
	
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
	
	private static void ListParts_403_InvalidAccessKeyId() throws IOException
	{
		System.out.println("basic Upload Part");
		String bucketName="chttest";
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
		System.out.println("basic Upload Part");
		String bucketName="chttest";
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
		System.out.println("basic Upload Part");
		String bucketName="chttest";
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
		ListParts_403_InvalidAccessKeyId();
		ListParts_403_InvalidSecretKeyId();
		ListParts_NoUploadId();
		Teardown();
	}
		
}