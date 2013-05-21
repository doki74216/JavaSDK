package nagative;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;

public class Neg_headObject{
    
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
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
    
    private static void headObject() throws IOException
    {
		String bucketName="chttest2";
		String fileName="hello.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{                
            System.out.println("Head Object");
            ObjectMetadata object = s3.getObjectMetadata(bucketName,fileName);
            System.out.println("Content-Length: "  + object.getContentLength());
            System.out.println("raw-metadata: "  + object.getRawMetadata());
            System.out.println("user-metadata: "  + object.getUserMetadata());
            System.out.println("MD5: "  + object.getContentMD5());
            System.out.println("ETag: "  + object.getETag());          
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
    
    private static void vHeadObject() throws IOException
    {
		String bucketName="chttest";
		String fileName="hello.txt";
		String vid = "a5a65e48a4654348969408f75d8fc52c"; 

		StringBuffer buffer = new StringBuffer(); 
		String line="";
				
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{                
            System.out.println("head Object with vid");
            GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName,fileName,vid);
            ObjectMetadata object = s3.getObjectMetadata(request);

            System.out.println(object.getContentLength());
            System.out.println(object.getETag());
            System.out.println(object.getVersionId());
            System.out.println(object.getLastModified());       
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
   
    private static void headObject_404_NotFound() throws IOException
    {
		String bucketName="chttest5";
		String fileName="NoSuchKey.txt";
		System.out.println("\nExpect 404 Not Found");
    	System.out.println("===================================================");
    	//AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{                
            System.out.println("Head Object");
            ObjectMetadata object = s3.getObjectMetadata(bucketName,fileName);
            System.out.println("Content-Length: "  + object.getContentLength());
            System.out.println("raw-metadata: "  + object.getRawMetadata());
            System.out.println("user-metadata: "  + object.getUserMetadata());
            System.out.println("MD5: "  + object.getContentMD5());
            System.out.println("ETag: "  + object.getETag());          
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
    
    private static void HeadObject_403_InvalidAccessKeyId() throws IOException
    {
		String bucketName="chttest2";
		String fileName="hello.txt";
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		try
		{                
            System.out.println("Head Object");
            ObjectMetadata object = s3.getObjectMetadata(bucketName,fileName);
            System.out.println("Content-Length: "  + object.getContentLength());
            System.out.println("raw-metadata: "  + object.getRawMetadata());
            System.out.println("user-metadata: "  + object.getUserMetadata());
            System.out.println("MD5: "  + object.getContentMD5());
            System.out.println("ETag: "  + object.getETag());          
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
    
    private static void HeadObject_403_InvalidSecretKeyId() throws IOException
    {
		String bucketName="chttest2";
		String fileName="hello.txt";

    	System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		try
		{                
            System.out.println("Head Object");
            ObjectMetadata object = s3.getObjectMetadata(bucketName,fileName);
            System.out.println("Content-Length: "  + object.getContentLength());
            System.out.println("raw-metadata: "  + object.getRawMetadata());
            System.out.println("user-metadata: "  + object.getUserMetadata());
            System.out.println("MD5: "  + object.getContentMD5());
            System.out.println("ETag: "  + object.getETag());          
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
    	headObject_404_NotFound();	//make sure you don't have the bucket or key
    	HeadObject_403_InvalidAccessKeyId();
    	HeadObject_403_InvalidSecretKeyId();
	}
		
}