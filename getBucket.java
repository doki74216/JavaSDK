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
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class getBucket{

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
    
	private static void basicGetBucket() throws IOException
	{
		System.out.println("basic get bucket");
		
		String bucketName="source";
		String fileName="content.txt";
		String fileName2="context.txt";
		String fileName3="apple.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket" + bucketName + "\n");
	        s3.createBucket(bucketName);
	        
	         
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            
            System.out.println("Listing object from bucket\n");
            ObjectListing objectListing = s3.listObjects(bucketName);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
                System.out.println("Etag: "+objectSummary.getETag());
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
	
	private static void pBasicGetBucket() throws IOException
	{
		System.out.println("get bucket with prefix");
		
		String bucketName="chttest";
		String fileName="content.txt";
		String fileName2="context.txt";
		String fileName3="apple.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket" + bucketName + "\n");
	        s3.createBucket(bucketName);
	        
	         
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            
            System.out.println("Listing object from bucket\n");
            ObjectListing objectListing = s3.listObjects(bucketName,"con");
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
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
	
	
	private static void GenericGetBucket() throws IOException
	{
		System.out.println("generic get bucket");
		
		String bucketName="chttest";
		String fileName="apple.jpg";
		String fileName2="photos/2006/January/sample.jpg";
		String fileName3="photos/2006/February/sample2.jpg";
		String fileName4="asset.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket" + bucketName + "\n");
	        s3.createBucket(bucketName);
	        
	         
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName4, createSampleFile()));
            
            System.out.println("Listing object from bucket (prefix)\n"); //test prefix
            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
            .withBucketName(bucketName)
            .withPrefix("photos/"));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
	        System.out.println();
	        
            System.out.println("Listing object from bucket (delimeter)\n"); //test delimeter
            objectListing = s3.listObjects(new ListObjectsRequest()
            .withBucketName(bucketName)
            .withDelimiter("/"));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
	        System.out.println();
	        
            System.out.println("Listing object from bucket (delimeter & prefix)\n"); //test delimeter & prefix
            objectListing = s3.listObjects(new ListObjectsRequest()
            .withBucketName(bucketName)
            .withDelimiter("/")
            .withDelimiter("/"));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
	        System.out.println(objectListing.getCommonPrefixes());
	        System.out.println();
	        
            System.out.println("Listing object from bucket (max key)\n"); //test max key
            objectListing = s3.listObjects(new ListObjectsRequest()
            .withBucketName(bucketName)
            .withMaxKeys(2));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
            }
	        System.out.println();
	        
            System.out.println("Listing object from bucket (marker)\n"); //test marker
            objectListing = s3.listObjects(new ListObjectsRequest()
            .withBucketName(bucketName)
            .withMarker(fileName));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                                   "(size = " + objectSummary.getSize() + ")");
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
	
	public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		basicGetBucket();
		//pBasicGetBucket(); 
		//GenericGetBucket(); //with prefix
	}
}