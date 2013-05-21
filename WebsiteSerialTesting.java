import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;



public class WebsiteSerialTesting{

	private static void basicPutBucketWebsite() throws IOException
	{
		String bucketName="chttest2";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
		BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("indexTest.html");
		config.setErrorDocument("404Test.html");
		
		try
		{
			System.out.println("basic put bucket website");    	
			
	        s3.setBucketWebsiteConfiguration(bucketName, config);           
	        System.out.println();
	        
	        System.out.println("basic get bucket website"); 
	        BucketWebsiteConfiguration result = null;
	        result = s3.getBucketWebsiteConfiguration(bucketName);   
	        if(!result.getErrorDocument().equalsIgnoreCase("404Test.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite ErrorDocument Error\n");
	        }
	        if(!result.getIndexDocumentSuffix().equalsIgnoreCase("indexTest.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite IndexDocument Error\n");
	        }
	       // System.out.println(result.getErrorDocument());
	       // System.out.println(result.getIndexDocumentSuffix());
	        System.out.println();
	        
	        System.out.println("basic delete bucket website"); 
	        s3.deleteBucketWebsiteConfiguration(bucketName); 
	        
			System.out.println("Tear down..");
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
	
	private static void PutBucketWebsite() throws IOException
	{
		String bucketName="chttest2";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
		BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("indexTest.html");
		//config.setErrorDocument("404Test.html");
		
		try
		{
			System.out.println("basic put bucket website");    	
			
	        s3.setBucketWebsiteConfiguration(bucketName, config);           
	        System.out.println();
	        
	        System.out.println("basic get bucket website"); 
	        BucketWebsiteConfiguration result = null;
	        result = s3.getBucketWebsiteConfiguration(bucketName);   
	        if(!result.getIndexDocumentSuffix().equalsIgnoreCase("indexTest.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite IndexDocument Error\n");
	        }
	       // System.out.println(result.getErrorDocument());
	       // System.out.println(result.getIndexDocumentSuffix());
	        System.out.println();
	        
	        System.out.println("basic delete bucket website"); 
	        s3.deleteBucketWebsiteConfiguration(bucketName); 
	        
			System.out.println("Tear down..");
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
		
		/* 
		 * test 1. PutBucketWebsite 
		 *      2. GetBucketWebsite
		 *      3. DeleteBucketWebsite
		 */
		basicPutBucketWebsite();
		
		/* 
		 * test 1. PutBucketWebsite without Optional xml field
		 *      2. GetBucketWebsite
		 *      3. DeleteBucketWebsite
		 */
		//PutBucketWebsite();
		
		System.out.println("WebsiteSerialTest Over");
		
	}
		
}
