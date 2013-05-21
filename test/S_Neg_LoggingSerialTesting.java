package test;
import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;

public class S_Neg_LoggingSerialTesting{
	
	
	
	private static void PutBucketLogging_404_NoSuchBucket() throws IOException
	{
		String bucketName="NoSuchBucket1";
		String bucketName2="NoSuchBucket2";
		BucketLoggingConfiguration config = new BucketLoggingConfiguration();
		
		System.out.println();
		System.out.println("PutBucketLogging_404_NoSuchBucket");
		System.out.println("Expect 404 NoSuchBucket");
    	System.out.println("===================================================");
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			config.setDestinationBucketName(bucketName2);
			config.setLogFilePrefix("mybucket-log-");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
	        s3.setBucketLoggingConfiguration(request);           
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
	
	private static void PutBucketLogging_403_InvalidAccessKeyId() throws IOException
	{
		String bucketName="chttest2";
		String bucketName2="region";
		BucketLoggingConfiguration config = new BucketLoggingConfiguration();
		
		System.out.println();
		System.out.println("PutBucketLogging_403_InvalidAccessKeyId");
		System.out.println("Expect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			config.setDestinationBucketName(bucketName2);
			config.setLogFilePrefix("mybucket-log-");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
	        s3.setBucketLoggingConfiguration(request);           
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
	
	private static void PutBucketLogging_403_InvalidSecretKeyId() throws IOException
	{
		
    	
		String bucketName="chttest2";
		String bucketName2="region";
		BucketLoggingConfiguration config = new BucketLoggingConfiguration();
		
		System.out.println();
		System.out.println("PutBucketLogging_403_InvalidSecretKeyId");
		System.out.println("Expect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		try
		{
			config.setDestinationBucketName(bucketName2);
			config.setLogFilePrefix("mybucket-log-");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
	        s3.setBucketLoggingConfiguration(request);           
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
	
	private static void GetBucketLogging_403_InvalidAccessKeyId() throws IOException
	{
		
    	
		String bucketName="chttest5";
		BucketLoggingConfiguration config = new BucketLoggingConfiguration();
		
		System.out.println();
		System.out.println("GetBucketLogging_403_InvalidAccessKeyId");
		System.out.println("Expect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
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
	
	private static void GetBucketLogging_403_InvalidSecretKeyId() throws IOException
	{
		
    	
		String bucketName="chttest";
		BucketLoggingConfiguration config = new BucketLoggingConfiguration();
		
		System.out.println();
		System.out.println("GetBucketLogging_403_InvalidSecretKeyId");
		System.out.println("Expect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
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
		//PutBucketLogging
		PutBucketLogging_404_NoSuchBucket();
		PutBucketLogging_403_InvalidAccessKeyId();
		PutBucketLogging_403_InvalidSecretKeyId();
		
		//GetBucketLogging
		GetBucketLogging_403_InvalidAccessKeyId();
		GetBucketLogging_403_InvalidSecretKeyId();
	}
		
}