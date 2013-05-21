import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.StorageClass;


public class ObjectSerialTesting{
	
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
    
    private static InputStream ReadFile() throws IOException {
    	File file = File.createTempFile("aws-java-sdk-", ".txt");
        //file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
       // writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();
        
        FileInputStream fin = new FileInputStream(file);
        return fin;
    }
    
    private static InputStream ReadFile1() throws IOException {
    	File file = File.createTempFile("aws-java-sdk-", ".txt");
        //file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        //writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();
        
        FileInputStream fin = new FileInputStream(file);
        return fin;
    }
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
    
    private static void basicPutObject() throws IOException
    {
    	String bucketName="chttest";	
    	String fileName="hello.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
	        s3.createBucket(bucketName);
	        
			System.out.println("basic put object");
			PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());
            request.withProgressListener(new ProgressListener() {
    			public void progressChanged(ProgressEvent event) {
    				System.out.println("Transferred bytes: " + 
    						event.getBytesTransfered());
    			}
    		});
            s3.putObject(request);
		
            
            System.out.println("basic get object");
            GetObjectRequest getrequest = new GetObjectRequest(bucketName,fileName);
            getrequest.withProgressListener(new ProgressListener() 
            {
    			public void progressChanged(ProgressEvent event) {
    				System.out.println("Transferred bytes: " + event.getBytesTransfered());
    				System.out.println("Event Code: " + event.getEventCode());
    			}
    		});
            S3Object object = s3.getObject(getrequest);
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            System.out.println("ETag: "  + object.getObjectMetadata().getETag());
            System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
            System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
            displayTextInputStream(object.getObjectContent());
            System.out.println();	
            
            
            //teardown
            System.out.println("Deleting object " + fileName + "\n");
	        s3.deleteObject(bucketName, fileName);
	        
	        System.out.println("Deleting bucket " + bucketName + "\n");
	        s3.deleteBucket(bucketName);
	        
	        System.out.println("DONE");
	                			
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
 
    
	private static void mBasicPutObjectandhead() throws IOException
    {
   	
		String bucketName="chttest1";
		String fileName="hello.txt";
		CannedAccessControlList cannedAcl = null;
		StorageClass storageClass = null;
		ObjectMetadata metadata = new ObjectMetadata() ;
		Map<String, String> map = new HashMap<String, String>();
			
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
		try
		{
	    	System.out.println("put object metadata");
						
	    	map.put("x-amz-meta-month", "september");
	    	metadata.setUserMetadata(map);
	    	metadata.addUserMetadata("x-amz-meta-flower", "lily");
            metadata.addUserMetadata("x-amz-meta-color", "pink");
            metadata.setContentType("text/plain");
            metadata.setContentLength(108);
            metadata.setContentEncoding("UTF-8");
            metadata.setContentDisposition("attachment; filename=\"default.txt\"");
            metadata.setCacheControl("no-cache");
            metadata.setContentMD5("aSsJ8P/c05f2r0JDoSWbHg=="); //hello.txt
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, ReadFile(),metadata).withInputStream(ReadFile1()).withCannedAcl(cannedAcl.PublicRead).withStorageClass("STANDARD").withMetadata(metadata));
            
            System.out.println("head object metadata");
            ObjectMetadata object = s3.getObjectMetadata(bucketName,fileName);
            System.out.println("Content-Length: "  + object.getContentLength());
            System.out.println("raw-metadata: "  + object.getRawMetadata());
            System.out.println("user-metadata: "  + object.getUserMetadata());
            System.out.println("MD5: "  + object.getContentMD5());
            System.out.println("ETag: "  + object.getETag());          
            System.out.println();
            
          //teardown
            System.out.println("Deleting object " + fileName + "\n");
	        s3.deleteObject(bucketName, fileName);
	        
	        System.out.println("Deleting bucket " + bucketName + "\n");
	        s3.deleteBucket(bucketName);
            
	        System.out.println("DONE");
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
    
    private static void BasicCopyObject() throws IOException
    {
    	String sbucketName = "chttest1";
    	String dbucketName = "chttest2";
    	String sfileName = "hello.txt";
    	String dfileName = "hello.txt";
    	StorageClass storageClass = null;
    	CannedAccessControlList cannedAcl = null;
    	ObjectMetadata metadata = new ObjectMetadata() ;
		Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate());
    	
		Date date2 = new Date();
		date2.setYear(date2.getYear());
		date2.setMonth(date2.getMonth()+2);
		date2.setDate(date2.getDate());
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating source bucket(source) " + sbucketName + "\n");
        s3.createBucket(sbucketName);
        
        System.out.println("Creating source bucket(dest) " + dbucketName + "\n");
        s3.createBucket(dbucketName);
		
		s3.putObject(sbucketName, sfileName, createSampleFile());
		
		try
		{
			metadata.addUserMetadata("x-amz-meta-company", "chttl");
			metadata.addUserMetadata("x-amz-meta-department", "cloud");
			metadata.setContentType("image/jpeg");
	            
                       
            //with storageClass, acl, metadata, if-match, if-modify-since
            System.out.println("Copy object with storage class, acl, metadata, if-match, if-modify-since ...\n");
            CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withStorageClass("STANDARD").withCannedAccessControlList(cannedAcl.PublicRead).withNewObjectMetadata(metadata).withMatchingETagConstraint("24156eb0ff2ed1056106fb2fc4d0ed85").withModifiedSinceConstraint(date));            
            System.out.println(result.getETag());
            System.out.println(result.getLastModifiedDate());
            
            //with if-non-match
            System.out.println("Copy object with if-non-match ...\n");
            CopyObjectResult result1 = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withNonmatchingETagConstraint("692b09f0ffdcd397f6af4243a1259b1c"));            
            System.out.println(result1.getETag());
            System.out.println(result1.getLastModifiedDate());
            
            //with if-non-modify-since
            System.out.println("Copy object with if-non-modify-since ...\n");
    		CopyObjectResult result2 = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withUnmodifiedSinceConstraint(date2));    		           
            System.out.println(result2.getETag());
            System.out.println(result2.getLastModifiedDate());
            
            //teardown
            System.out.println("Deleting object(source) " + sfileName + "\n");
	        s3.deleteObject(sbucketName, sfileName);
	        
	        System.out.println("Deleting bucket(source) " + sbucketName + "\n");
	        s3.deleteBucket(sbucketName);
            
            System.out.println("Deleting object(dest) " + dfileName + "\n");
	        s3.deleteObject(dbucketName, dfileName);
	        
	        System.out.println("Deleting bucket(dest) " + dbucketName + "\n");
	        s3.deleteBucket(dbucketName);
	        
	        System.out.println("DONE");
	        
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
    
    private static void BasicDeleteObject() throws IOException
	{		

    	//String bucketName="source";	
    	//String fileName="hi.txt";

    	String bucketName="chttest1";	
    	String bucketName2="chttest2";	
    	String fileName="hello.txt";

		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Deleting object(source) " + fileName + "\n");
	        s3.deleteObject(bucketName, fileName);
	        
			System.out.println("Deleting object(dest) " + fileName + "\n");
	        s3.deleteObject(bucketName2, fileName);

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
		System.out.println("basic delete bucket");
		
    	String bucketName="chttest1";	
    	String bucketName2="chttest2";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Deleting bucket(source) " + bucketName + "\n");
	        s3.deleteBucket(bucketName);
			System.out.println("Deleting bucket(dest) " + bucketName2 + "\n");
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
    
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		
		/* 
		 * test 1. Put Object 
		 *      2. Get Object
		 *      3. Delete Object
		 */
		//basicPutObject();	
		
		/*
		 * test 1. Put Object + Metadata
		 *      2. Head Object
		 */
		//mBasicPutObjectandhead();
		
		/*
		 * test 1. Copy Object 
		 */
		BasicCopyObject();
		
		//BasicDeleteObject();
		//BasicDeleteBucket();
		
		System.out.println("ObjectSerialTest Over");
	}
		
}
