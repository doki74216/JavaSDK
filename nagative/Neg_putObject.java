package nagative;
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
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
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

public class Neg_putObject{
	
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
    	
		String bucketName="chttest2";
		String fileName="hello.txt";

		
		//AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));

		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
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
            new PutObjectRequest(bucketName, fileName, null, null);
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
		System.out.println("just dance");
    }
	
    private static void PutObject_404_NoSuchBucket() throws IOException
    {
    	System.out.println("\nExpect 404 NoSuchBucket");
    	System.out.println("===================================================");
    	
		String bucketName="chttest5";
		String fileName="hello.txt";

		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
			//System.out.println("Creating bucket " + bucketName + "\n");
           // s3.createBucket(bucketName);
            
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println("Uploading a new object to S3("+bucketName+") from a file\n");
            //s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());
            new PutObjectRequest(bucketName, fileName, null, null);
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
    
    private static void PutObject_403_InvalidAccessKeyId() throws IOException
    {
    	System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	
		String bucketName="chttest2";
		String fileName="hello.txt";

		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());
            new PutObjectRequest(bucketName, fileName, null, null);
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
    private static void PutObject_403_InvalidSecretKeyId() throws IOException
    {
    	//System.out.println("basic put bucket");
    	System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	
		String bucketName="chttest2";
		String fileName="hello.txt";

		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		try
		{
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, createSampleFile());
            new PutObjectRequest(bucketName, fileName, null, null);
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
    
    
    public static void main(String args[]) throws IOException
	{
		PutObject_404_NoSuchBucket();	// make sure you don't have the bucket
		PutObject_403_InvalidAccessKeyId();
		PutObject_403_InvalidSecretKeyId();
	}
		
}