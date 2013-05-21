import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;

public class putObject{
	
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
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
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
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();
        
        FileInputStream fin = new FileInputStream(file);
        return fin;
    }
    
    private static void basicPutObject() throws IOException
    {
    	System.out.println("basic put bucket");
    	
		String bucketName="chttest";
		String fileName="hello.txt";

		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			//System.out.println("Creating bucket " + bucketName + "\n");
           // s3.createBucket(bucketName);
            
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            
            System.out.println("Uploading a new object to S3 from a file\n");
            //s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());
            request.withProgressListener(new ProgressListener() {
    			public void progressChanged(ProgressEvent event) {
    				System.out.println("Transferred bytes: " + 
    						event.getBytesTransfered());
    			}
    		});
            s3.putObject(request);

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
	
 
    private static void mBasicPutObject() throws IOException
    {
    	System.out.println("basic put bucket");
    	
		String bucketName="chttest";
		String fileName="hello.txt";
		ObjectMetadata metadata = new ObjectMetadata() ;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            
            metadata.addUserMetadata("x-amz-meta-flower", "lily");
            metadata.addUserMetadata("x-amz-meta-color", "pink");
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, ReadFile(),metadata));
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
	
    private static void GeneralPutObject() throws IOException
    {
    	System.out.println("general put bucket");
    	
		String bucketName="chttest";
		String fileName="hello.txt";
		String fileName2="world.txt";
		ObjectMetadata metadata = new ObjectMetadata() ;
		CannedAccessControlList cannedAcl = null;
		StorageClass storageClass = null;
		Map<String, String> map = new HashMap<String, String>();
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			//System.out.println("Creating bucket " + bucketName + "\n");
           // s3.createBucket(bucketName);
            
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            
            map.put("x-amz-meta-month", "september");
            metadata.addUserMetadata("x-amz-meta-flower", "lily");
            metadata.addUserMetadata("x-amz-meta-color", "pink");
            metadata.setUserMetadata(map);
            metadata.setContentType("text/plain");
            metadata.setContentLength(108);
            metadata.setContentEncoding("UTF-8");
            metadata.setContentDisposition("attachment; filename=\"default.txt\"");
            metadata.setCacheControl("no-cache");
            metadata.setContentMD5("aSsJ8P/c05f2r0JDoSWbHg=="); //hello.txt
            		
            System.out.println("Uploading a new object to S3 from a file\n");
           // s3.putObject(new PutObjectRequest(bucketName, fileName, ReadFile(),metadata).withInputStream(ReadFile1()).withCannedAcl(cannedAcl.PublicRead).withStorageClass("STANDARD").withMetadata(metadata));
            s3.putObject(new PutObjectRequest(bucketName, fileName2, ReadFile(),metadata).withFile(createSampleFile()).withCannedAcl(cannedAcl.PublicReadWrite).withStorageClass(storageClass.ReducedRedundancy).withMetadata(metadata));
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
		basicPutObject(); //basic put object + put object with progress listener
		//mBasicPutObject();
		//GeneralPutObject();
	}
		
}