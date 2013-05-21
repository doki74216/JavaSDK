import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;

public class getBucketVersions{
		
	private static void basicGetBucketVersions() throws IOException
	{
		System.out.println("basic get bucket versions");
    	
		String bucketName="chttest";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			VersionListing result = s3.listVersions(bucketName, "hello");
			System.out.println(result.getBucketName());
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
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

	private static void pGetBucketVersions() throws IOException
	{
		System.out.println("basic get bucket versions");
    	
		String bucketName="chttest";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			//list all verisons
			VersionListing result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}
			
			//max-key
			System.out.println("get bucket versions MAX-KEY=2");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withMaxResults(2));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}
			
			//prefix
			System.out.println("get bucket versions PREFIX='b'");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withPrefix("b"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}
			
			//key marker
			System.out.println("get bucket versions KEY-MARKER");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withKeyMarker("banana.txt"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}
			
			//delimiter & prefix
			System.out.println("get bucket versions prefix & delimiter");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withDelimiter("/").withPrefix("photos/2006/"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}
			
			//version id marker & key marker
			System.out.println("get bucket versions Version-ID-MArker & key-marker");
			result = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName).withKeyMarker("banana.txt").withVersionIdMarker("e9bcda6ba47e4de78d225e7714de3a22"));
			for(S3VersionSummary s : result.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getETag());
				System.out.println(s.getSize());
				System.out.println(s.getVersionId());
				System.out.println();
			}
			
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
		basicGetBucketVersions();
	}
		
}