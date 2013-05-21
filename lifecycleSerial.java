import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration.Rule;


public class lifecycleSerial{
	
	private static void basicLifecycle() throws IOException
	{	    	
		String bucketName="region";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule = new Rule();
        rule.setId("test_gg");
        rule.setPrefix("gg");
        rule.setStatus("Enabled");
        rule.setExpirationInDays(6);
        
        rules.add(rule);
		
		try
		{
			s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
			
			BucketLifecycleConfiguration result = s3.getBucketLifecycleConfiguration(bucketName);
			//System.out.println("GetBucketLifecycle Result:\n" + result.getRules().toString());
			
			s3.deleteBucketLifecycleConfiguration(bucketName);
			
			System.out.println("END");
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
		 * PutBucketlifecycle
		 * GetBucketlifecycle
		 * DeleteBucketLifecycle
		 */
		basicLifecycle(); 		
	}
		
}
