import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class uploadPartCopy{
	
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        //writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
    
    private static void basicPutBucket(String bName , String fName) throws IOException
    {
    	System.out.println("basic put bucket");
    	
		String bucketName = bName;
		String fileName = fName;
		
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
            
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));

            
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
	
    private static void basicUploadPartCopy() throws IOException
    {
    	String sbucketName = "region";
    	String dbucketName = "chttest";
    	String sfileName = "world.txt";
    	String dfileName = "hello.txt";
    	String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
    	long firstByte = 1;
    	long lastByte = 322122547 ;
    	List<String> list = new ArrayList<String>(); //etag
    	list.add("692b09f0ffdcd397f6af4243a1259b1a");
        Date date = new Date();
        date.setYear(date.getYear());
        date.setMonth(date.getMonth()+2);
        date.setDate(date.getDate());        
    	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
            System.out.println("basic Upload Part Copy...\n");
			CopyPartRequest request = new CopyPartRequest();
			request.setDestinationBucketName(dbucketName);
			request.setDestinationKey(dfileName);
			request.setUploadId(uploadID);
			request.setPartNumber(8);
			request.setSourceBucketName(sbucketName);
			request.setSourceKey(sfileName);
			request.setFirstByte(firstByte);
			request.setLastByte(lastByte);
			//request.setMatchingETagConstraints(list);
			//request.setNonmatchingETagConstraints(list);
			//request.setModifiedSinceConstraint(date);
			//request.setUnmodifiedSinceConstraint(date);
			//request.setRequestCredentials(credentials);
			
			CopyPartResult result = s3.copyPart(request);
			System.out.println(result.getPartNumber());
			System.out.println(result.getETag());
			System.out.println(result.getLastModifiedDate());
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
    	//String dbucketName = "region";
    	//String dfileName = "world.txt";
    	
		System.out.println("hello world");
		//basicPutBucket(dbucketName,dfileName);
		basicUploadPartCopy();
	}
		
}