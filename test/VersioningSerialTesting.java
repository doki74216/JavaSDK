package test;
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
import com.amazonaws.services.s3.model.BucketPolicy;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.VersionListing;


public class VersioningSerialTesting{

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
	
		
    private static void basicPutBucketVersioning() throws IOException
    {
    	String bucketName="source";	
		
    	AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		       
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
    	BucketVersioningConfiguration config = new BucketVersioningConfiguration();
    	
		
		try
		{
			System.out.println("basic enabled bucket versioning");
			config.setStatus("Enabled");
			SetBucketVersioningConfigurationRequest version = new SetBucketVersioningConfigurationRequest(bucketName,config);
			s3.setBucketVersioningConfiguration(version);
			config = s3.getBucketVersioningConfiguration(bucketName);
		    System.out.println("versioning status:"+config.getStatus());			     
	        
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
 
    private static void basicDisableBucketVersioning() throws IOException
    {
    	String bucketName="source";	
		
    	AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
       
    	BucketVersioningConfiguration config = new BucketVersioningConfiguration();
    	
		
		try
		{
			System.out.println("basic disabled bucket versioning");
			config.setStatus("Suspended");
			SetBucketVersioningConfigurationRequest version = new SetBucketVersioningConfigurationRequest(bucketName,config);
			s3.setBucketVersioningConfiguration(version);
			config = s3.getBucketVersioningConfiguration(bucketName);
		    System.out.println("versioning status:"+config.getStatus());	
		    
		    VersionListing clear = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
			for(S3VersionSummary s : clear.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getVersionId());
				s3.deleteVersion(s.getBucketName(), s.getKey(), s.getVersionId());
			}
	        
			//teardown
			 System.out.println("Deleting bucket " + bucketName + "\n");
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
    
    
    private static void basicGetBucketObjectVersioning() throws IOException
    {
    	String bucketName="source";	
    	String fileName="photos/2006/January/sample.jpg";
    	
    	AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		       
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
        BucketVersioningConfiguration config = new BucketVersioningConfiguration();
        
		try
		{
			System.out.println("basic enabled bucket versioning");
			config.setStatus("Enabled");
			SetBucketVersioningConfigurationRequest version = new SetBucketVersioningConfigurationRequest(bucketName,config);
			s3.setBucketVersioningConfiguration(version);
			config = s3.getBucketVersioningConfiguration(bucketName);
		    System.out.println("versioning status:"+config.getStatus());			
			
			System.out.println("Uploading a new object to S3 from a file\n");
			s3.putObject(bucketName, fileName, createSampleFile());
			
			System.out.println("basic get bucket object versioning");			
			VersionListing result = s3.listVersions(bucketName, fileName);
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("FileName:"+s.getKey());
				System.out.println("ETag:"+s.getETag());
				System.out.println("FileSize:"+s.getSize());
				System.out.println("VersionId:"+s.getVersionId());
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
    
 
	private static void pGetBucketObjectVersions() throws IOException
	{
		String bucketName="source";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			//list all verisons
			System.out.println("get all bucket versions");
			VersionListing result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("FileName:"+s.getKey());
				System.out.println("ETag:"+s.getETag());
				System.out.println("FileSize:"+s.getSize());
				System.out.println("VersionId:"+s.getVersionId());
				System.out.println();
			}
			
			//max-key
			System.out.println("get bucket versions MAX-KEY=2");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withMaxResults(2));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("FileName:"+s.getKey());
				System.out.println("ETag:"+s.getETag());
				System.out.println("FileSize:"+s.getSize());
				System.out.println("VersionId:"+s.getVersionId());
				System.out.println();
			}
			
			//prefix
			System.out.println("get bucket versions PREFIX='w'");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withPrefix("w"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("FileName:"+s.getKey());
				System.out.println("ETag:"+s.getETag());
				System.out.println("FileSize:"+s.getSize());
				System.out.println("VersionId:"+s.getVersionId());
				System.out.println();
			}
			
			//key marker
			System.out.println("get bucket versions KEY-MARKER");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withKeyMarker("hello.txt"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("FileName:"+s.getKey());
				System.out.println("ETag:"+s.getETag());
				System.out.println("FileSize:"+s.getSize());
				System.out.println("VersionId:"+s.getVersionId());
				System.out.println();
			}
			
			//delimiter & prefix
			System.out.println("get bucket versions prefix & delimiter");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withDelimiter("/").withPrefix("photos"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("FileName:"+s.getKey());
				System.out.println("ETag:"+s.getETag());
				System.out.println("FileSize:"+s.getSize());
				System.out.println("VersionId:"+s.getVersionId());
				System.out.println();
			}
			
			
			//version id marker & key marker
			/*System.out.println("get bucket versions Version-ID-MArker & key-marker");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withKeyMarker("hello.txt").withVersionIdMarker("fb5c2d5ddd8c47dbabfaf7f540d42d34"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}*/
						
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
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
				
			System.out.println("Deleting bucket " + bucketName + "\n");
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
	
	private static void vBasicDeleteObject() throws IOException
	{		
		String bucketName="source";	
    	String fileName="photos/2006/January/sample.jpg";
		String vid = "c6a49d69aa724bb7a7bbbf57821a8ac8"; 
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			
            System.out.println("get all bucket versions");
			VersionListing result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println("BucketName:"+s.getBucketName());
				System.out.println("VersionId:"+s.getVersionId());
				String devid = s.getVersionId();
				
				System.out.println("Deleting object " + fileName +" with vid \n");
		        s3.deleteVersion(bucketName, fileName,devid);
				
				System.out.println();
			}
			
			
		/*	System.out.println("Deleting object " + fileName +" with vid \n");
	        s3.deleteVersion(bucketName, fileName,vid);*/

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
		 * test 1. PutBucketVersioning
		 *      2. GetBucketVersioning
		 */
		basicPutBucketVersioning();
		
		/*
		 * test 1. DisableBucketVersioning
		 *      2. GetBucketVersioning
		 */
		basicDisableBucketVersioning();
		
		/*
		 * test 1. GetBucketObjectVersioning 
		 */
		basicGetBucketObjectVersioning();
				
		/*
		 * test 1. GetBucketObjectVersioning-parameters 
		 */
		pGetBucketObjectVersions();
     	
		vBasicDeleteObject();
     	BasicDeleteBucket();
     	
     	System.out.println("VersioningSerialTest Over");
		
	}
		
}
