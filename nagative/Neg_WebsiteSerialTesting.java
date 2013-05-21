package nagative;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class Neg_WebsiteSerialTesting{
	
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
    
 
    private static void BucketWebsite_403_InvalidAccessKeyId() throws IOException
    {
    	System.out.println("basic create bucket InvalidAccessKeyId");
    	
		String bucketName="region";
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

    	BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("indexTest.html");
		config.setErrorDocument("404Test.html");
		
		try
		{
			System.out.println("basic put bucket website");    	
			
	        s3.setBucketWebsiteConfiguration(bucketName, config);           
	        System.out.println();
	        
	        System.out.println("basic get bucket website"); 
	        BucketWebsiteConfiguration result = null;
	        result = s3.getBucketWebsiteConfiguration(bucketName);   
	        if(!result.getErrorDocument().equalsIgnoreCase("404Test.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite ErrorDocument Error\n");
	        }
	        if(!result.getIndexDocumentSuffix().equalsIgnoreCase("indexTest.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite IndexDocument Error\n");
	        }
	       // System.out.println(result.getErrorDocument());
	       // System.out.println(result.getIndexDocumentSuffix());
	        System.out.println();
	        
	        System.out.println("basic delete bucket website"); 
	        s3.deleteBucketWebsiteConfiguration(bucketName); 
	        
			System.out.println("Tear down..");
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
    
    private static void BucketWebsite_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("basic create bucket InvalidSecretKeyId");
    	
		String bucketName="region";
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
    	BucketWebsiteConfiguration config = new BucketWebsiteConfiguration("indexTest.html");
		config.setErrorDocument("404Test.html");
		
		try
		{
			System.out.println("basic put bucket website");    	
			
	        s3.setBucketWebsiteConfiguration(bucketName, config);           
	        System.out.println();
	        
	        System.out.println("basic get bucket website"); 
	        BucketWebsiteConfiguration result = null;
	        result = s3.getBucketWebsiteConfiguration(bucketName);   
	        if(!result.getErrorDocument().equalsIgnoreCase("404Test.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite ErrorDocument Error\n");
	        }
	        if(!result.getIndexDocumentSuffix().equalsIgnoreCase("indexTest.html"))
	        {
	        	System.out.println("ERROR!!!\n GetBucketWebsite IndexDocument Error\n");
	        }
	       // System.out.println(result.getErrorDocument());
	       // System.out.println(result.getIndexDocumentSuffix());
	        System.out.println();
	        
	        System.out.println("basic delete bucket website"); 
	        s3.deleteBucketWebsiteConfiguration(bucketName); 
	        
			System.out.println("Tear down..");
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
    
   
    
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		

		/*
		 * test InvalidAccessKeyId
		 */
		BucketWebsite_403_InvalidAccessKeyId();
		
		/*
		 * test InvalidSecretKeyId
		 */
		BucketWebsite_403_InvalidSecretKeyId();
		
		
		System.out.println("Neg_WebsiteSerialTest Over");

		
	}
		
}