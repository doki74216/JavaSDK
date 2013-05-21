import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;


public class BucketLoggingSerialTesting{
	
	private static void basicPutBucketandACL() throws IOException
	{	    	
		String bucketName="source";
		String bucketName2="target";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
		
		System.out.println("Creating target bucket " + bucketName2 + "\n");
	    s3.createBucket(bucketName2);
		AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
			
		
		try
		{
			acl.setOwner(owner);
			acl.grantPermission(GroupGrantee.LogDelivery, Permission.ReadAcp);
			acl.grantPermission(GroupGrantee.LogDelivery, Permission.Write);
			acl.grantPermission(new CanonicalGrantee("canonicalidhrchu"), Permission.FullControl);
			s3.setBucketAcl(bucketName2, acl);     
			
			
			
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
	
    private static void basicPutBucketLogging() throws IOException
    {
    	String bucketName="source";
    	String bucketName2="target";		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		       
        BucketLoggingConfiguration config = new BucketLoggingConfiguration();
        config.setDestinationBucketName(bucketName2);
		config.setLogFilePrefix("chttest-log-");
		
		
		try
		{
			System.out.println("basic put bucket logging");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
			s3.setBucketLoggingConfiguration(request);
	        System.out.println();			
	        
			System.out.println("basic get bucket logging");
			config = s3.getBucketLoggingConfiguration(bucketName);  
	        /*System.out.println(config.getDestinationBucketName());
	        System.out.println(config.getLogFilePrefix());
	        System.out.println(config.isLoggingEnabled());*/
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
 
    
    private static void basicDisableBucketLogging() throws IOException
    {
    	String bucketName="source";	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		       
        BucketLoggingConfiguration config = new BucketLoggingConfiguration();
        config.setDestinationBucketName(null);
        config.setLogFilePrefix(null);
		
		try
		{
			System.out.println("basic disable bucket logging");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
			s3.setBucketLoggingConfiguration(request);
	        System.out.println();			
	        
			System.out.println("basic get bucket logging");
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
    
    private static void BasicDeleteBucket() throws IOException
	{
		System.out.println("basic get bucket");
		
    	String bucketName="source";	
    	String bucketName2="target";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Deleting source bucket " + bucketName + "\n");
	        s3.deleteBucket(bucketName);
	        
	        System.out.println("Deleting target bucket " + bucketName2 + "\n");
	        s3.deleteBucket(bucketName2);

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
    
    
    private static void NativePutBucketLogging() throws IOException
    {
    	String bucketName="source";
    	String bucketName2="target";		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		       
        BucketLoggingConfiguration config = new BucketLoggingConfiguration();
        config.setDestinationBucketName(bucketName2);
		//config.setLogFilePrefix("chttest-log-");
		
		
		try
		{
			System.out.println("basic put bucket logging");
			SetBucketLoggingConfigurationRequest request = new SetBucketLoggingConfigurationRequest(bucketName,config);
			s3.setBucketLoggingConfiguration(request);
	        System.out.println();			
	        
			System.out.println("basic get bucket logging");
			config = s3.getBucketLoggingConfiguration(bucketName);  
	        /*System.out.println(config.getDestinationBucketName());
	        System.out.println(config.getLogFilePrefix());
	        System.out.println(config.isLoggingEnabled());*/
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
		
		/* 
		 * set target bucket acl
		 */
		basicPutBucketandACL(); 
		
		/*
		 * test 1. PutBucketLogging 
		 *      2. GetBucketLogging  
		 */
		basicPutBucketLogging();
		
		/*
		 * test 1. DisableBucketLogging
		 * 		2. GetBucketLogging
		 */
		basicDisableBucketLogging();
		
				
		NativePutBucketLogging();
		
		//teardown
		BasicDeleteBucket();
		
		System.out.println("BucketLoggingSerialTest Over");
		
	}
		
}
