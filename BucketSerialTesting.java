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
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class BucketSerialTesting{
	
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
    
    private static void RegionPutBucket() throws IOException
    {
    	System.out.println("put bucket with region..\n");
    	
		String bucketName="chutest"; //bucket with region(region)
		//String bucketNameS="sregion"; //bucket with region(string)		
		String region="EU";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName,region);
            
		/*	System.out.println("Creating bucket " + bucketNameS + "\n");
            s3.createBucket(bucketNameS,region);*/
            
            int count=0;
	        System.out.println("Listing buckets");
	        for (Bucket bucket : s3.listBuckets()) {
	            System.out.println(" - " + bucket.getName());
	          /*  if(bucket.getName().equalsIgnoreCase(bucketNameS))
	            {
	            	count++;
	            }*/
	            if(bucket.getName().equalsIgnoreCase(bucketName))
	            {
	            	count++;
	            }
	        }
	        if(count!=2){System.out.println("ERROR!!!\n GetService Error\n");}
	        
	        System.out.println();
	        
			System.out.println("Tear down..");
	        s3.deleteBucket(bucketName);
	      //  s3.deleteBucket(bucketNameS);
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
 
    private static void ACLPutBucket() throws IOException
    {
    	System.out.println("put bucket with canned acl");
    	
		String bucketName="chttest";
		String bucketName2="region";
		String fileName="content.txt";
		String fileName2="context.txt";
		String fileName3="apple.txt";
		CannedAccessControlList acl = null;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			//System.out.println("Creating bucket...\n");
            s3.createBucket(new CreateBucketRequest(bucketName).withCannedAcl(acl.PublicRead));
            Bucket bucket = s3.createBucket(bucketName2);
            
            if(!bucket.getName().equalsIgnoreCase(bucketName2))
            {
            	System.out.println("ERROR!!!\n Normal PutBucket Error\n");
            }

            String temp="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ]";            
            AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
            Set<Grant> grants = bucketAcl.getGrants();
			//System.out.println(bucketAcl.getGrants());
			if(grants.toString().indexOf(temp)==-1)
			{
				System.out.println("ERROR!!!\n PutBucket Canned ACL Error\n");
			}


            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            
            int count=0;
            System.out.println("Listing object from bucket\n");
            ObjectListing objectListing = s3.listObjects(bucketName);
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                //System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
                if(objectSummary.getKey().equalsIgnoreCase(fileName))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName2))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName3))
                {count++;}
            }
            if(count!=3){System.out.println("ERROR!!!\n Normal GetBucket Error\n");}

			//System.out.println("Tear down..");
	        s3.deleteObject(bucketName, fileName);
	        s3.deleteObject(bucketName, fileName2);
	        s3.deleteObject(bucketName, fileName3);
	        s3.deleteBucket(bucketName);
	        s3.deleteBucket(bucketName2);
	        
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
    
    private static void ParameterPutBucket() throws IOException
    {
		System.out.println("Get bucket with Parameters");
		
		String bucketName="chttest";
		String fileName="apple.jpg";
		String fileName2="photos/2006/January/sample.jpg";
		String fileName3="photos/2006/February/sample2.jpg";
		String fileName4="asset.txt";
		int count=0;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
	        s3.createBucket(bucketName);
	                 
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName3, createSampleFile()));
            s3.putObject(new PutObjectRequest(bucketName, fileName4, createSampleFile()));
            
            
            System.out.println("Listing object from bucket (prefix)\n"); //test prefix
            count=0;
            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("photos/"));                      
 
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                //System.out.println(" - " + objectSummary.getKey() + "  " +"(size = " + objectSummary.getSize() + ")");
                if(objectSummary.getKey().equalsIgnoreCase(fileName2))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName3))
                {count++;}
            }
            if(count!=2){System.out.println("ERROR!!!\n GetBucket with Prefix Error\n");}
	        System.out.println();
	        
	        count=0; //prefix mothod 2
	        ObjectListing objectListing2 = s3.listObjects(bucketName,"photos/");
            for (S3ObjectSummary objectSummary : objectListing2.getObjectSummaries()) {
                //System.out.println(" - " + objectSummary.getKey() + "  " +"(size = " + objectSummary.getSize() + ")");
                if(objectSummary.getKey().equalsIgnoreCase(fileName2))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName3))
                {count++;}
            }
            if(count!=2){System.out.println("ERROR!!!\n GetBucket with Prefix -2 Error\n");}
	        System.out.println();
	        
	        
            System.out.println("Listing object from bucket (delimeter)\n"); //test delimeter
            count=0;
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withDelimiter("/"));  
            
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
                if(objectSummary.getKey().equalsIgnoreCase(fileName))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName4))
                {count++;}
            }
            if(count!=2){System.out.println("ERROR!!!\n GetBucket with Delimeter Error\n");}
	        System.out.println();
	        
	        
            System.out.println("Listing object from bucket (delimeter & prefix)\n"); //test delimeter & prefix
            count=0;
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withDelimiter("/").withPrefix("photos/2006/"));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                //System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
                count++;
            }
            if(count!=0){System.out.println("ERROR!!!\n GetBucket with Delimeter & Prefix Error\n");}
	        //System.out.println(objectListing.getCommonPrefixes());
	        if(objectListing.getCommonPrefixes().get(0).equalsIgnoreCase("photos/2006/January/"))
	        {count++;}
	        if(objectListing.getCommonPrefixes().get(1).equalsIgnoreCase("photos/2006/February/"))
	        {count++;}
	        if(count!=2){System.out.println("ERROR!!!\n GetBucket with Delimeter & Prefix -2 Error\n");}
	        System.out.println();
	        
	        
	        
            System.out.println("Listing object from bucket (max key)\n"); //test max key
            count=0;
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withMaxKeys(2));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                //System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
                count++;
            }
            if(count!=2){System.out.println("ERROR!!!\n GetBucket with MaxKey Error\n");}
	        System.out.println();
	        
            System.out.println("Listing object from bucket (marker)\n"); //test marker
            count=0;
            objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withMarker(fileName));           
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                //System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
                if(objectSummary.getKey().equalsIgnoreCase(fileName2))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName3))
                {count++;}
                if(objectSummary.getKey().equalsIgnoreCase(fileName4))
                {count++;}
            }
            if(count!=3){System.out.println("ERROR!!!\n GetBucket with Marker Error\n");}
	        System.out.println();
            
			//System.out.println("Tear down..");
	        s3.deleteObject(bucketName, fileName);
	        s3.deleteObject(bucketName, fileName2);
	        s3.deleteObject(bucketName, fileName3);
	        s3.deleteObject(bucketName, fileName4);
	        s3.deleteBucket(bucketName);
	        
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
		 * test 1. bucket with region(type Region & String) 
		 *      2. Get Service
		 *      3. Delete Bucket
		 */
		RegionPutBucket(); 
		
		/*
		 * test 1. bucket with Canned ACL & normal put bucket
		 *      2. Normal GetBucket
		 */
		//ACLPutBucket();
		
		/*
		 * test 1. GetBucket with parameters
		 */
		//ParameterPutBucket();
		
		System.out.println("BucketSerialTest Over");

		
	}
		
}
