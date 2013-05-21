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
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;


public class PolicySerialTesting{
	
		
    private static void basicPutBucketPolicy() throws IOException
    {
    	String bucketName="source";	
		
    	AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		       
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
		String policy = "{\"Version\": \"2008-10-17\",\"Id\": \"Policy1355909850780\",\"Statement\": [{\"Sid\": \"Stmt1355909849219\",\"Effect\": \"Allow\",\"Principal\": {\"AWS\": \"*\"},\"Action\": \"s3:GetObject\",\"Resource\": \"arn:aws:s3:::source/*\"}]}";
		
		try
		{
			System.out.println("basic put bucket policy");
			s3.setBucketPolicy(bucketName, policy);           
	        System.out.println();
	        
	        System.out.println("basic get bucket policy");
	        BucketPolicy getpolicy = s3.getBucketPolicy(bucketName);           
	        System.out.println(getpolicy.getPolicyText());
	        System.out.println();
	        
	        System.out.println("basic delete bucket policy");
	        s3.deleteBucketPolicy(bucketName);
	        BucketPolicy depolicy = s3.getBucketPolicy(bucketName);           
	        System.out.println(depolicy.getPolicyText());
	        System.out.println();
	        
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
 
   
    
    
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		
		/*
		 * test 1. PutBucketPolicy 
		 *      2. GetBucketPolicy
		 *      3. DeleteBucketPolicy
		 */
		basicPutBucketPolicy();
		
		System.out.println("PolicySerialTest Over");
		
		
	}
		
}
