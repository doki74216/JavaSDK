import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;
import com.amazonaws.services.s3.model.StorageClass;

public class initialMPU{

	private static void basicPutBucket() throws IOException
	{
		System.out.println("basic put bucket");
	    	
		String bucketName="chttest";
		String fileName="world.txt";
			
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
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
		String bucketName="chttest";
		String fileName="6G";
		ObjectMetadata meta = new ObjectMetadata();
		meta.setHeader("x-amz-color", "red");
		//CannedAccessControlList acl = null;
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName,meta);
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName).withCannedACL(acl.AuthenticatedRead);
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName).withStorageClass(StorageClass.Standard);
		
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
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
	
	private static void aclInitialMPU() throws IOException
	{
		System.out.println("basic initial MPU");
		String bucketName="chttest";
		String fileName="hello.txt";
		ObjectMetadata meta = new ObjectMetadata();
		meta.setHeader("x-amz-color", "red");
		AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
		acl.setOwner(owner);
        acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
        acl.grantPermission(new CanonicalGrantee("canonicalidhrchu"), Permission.FullControl);		
		
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName,meta);
		InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName).withAccessControlList(acl);
        //InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName).withObjectMetadata(meta);
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName);
		//InitiateMultipartUploadRequest config = new InitiateMultipartUploadRequest(bucketName,fileName).withAccessControlList(acl);

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
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
	
	
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		basicPutBucket();
		basicInitialMPU();
		//aclInitialMPU();
	}
		
}