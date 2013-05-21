import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;

public class getBucketLogging{
	
	
	private static void basicGetBucketLogging() throws IOException
	{
		System.out.println("basic get bucket policy");
    	
		String bucketName="chttest";
		BucketLoggingConfiguration config = new BucketLoggingConfiguration();
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
	        config = s3.getBucketLoggingConfiguration(bucketName);  
	        System.out.println(config.getDestinationBucketName());
	        System.out.println(config.getLogFilePrefix());
	        System.out.println(config.isLoggingEnabled());
	        System.out.println(config.toString());
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
	
	
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		basicGetBucketLogging();
	}
		
}