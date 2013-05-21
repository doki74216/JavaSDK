package test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
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


public class S_Neg_lifecycleSerial{
	
	private static void basicLifecycle() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
       // s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);
        
        
        // ヘ玡礚猭 date 把计
        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        Date date = null;
     
        try {
			date  = sdf.parse("2014-12-31T00:00:00.000Z") ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

        System.out.println("date = " + date.toString());
        System.out.println("date = " + date.toGMTString());
        
        Rule rule2 = new Rule();
        rule2.setId("test_hh");
        rule2.setPrefix("hh");
        rule2.setStatus("Enabled");
        rule2.setExpirationDate(date); 
        rules.add(rule2);
        */
        rules.add(rule1);
          
		try
		{
			s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
			
			BucketLifecycleConfiguration result = s3.getBucketLifecycleConfiguration(bucketName);
			System.out.println("Rule 1");
			System.out.println("GetBucketLifecycle id(test_gg):" + result.getRules().get(0).getId());
			System.out.println("GetBucketLifecycle Prefix(gg):" + result.getRules().get(0).getPrefix());
			System.out.println("GetBucketLifecycle Status(Enabled):" + result.getRules().get(0).getStatus());
			System.out.println("GetBucketLifecycle ExpirationInDays(1):" + result.getRules().get(0).getExpirationInDays() );
			
			// ゲ斗 date 把计挡狦
			System.out.println("Rule 2");
			System.out.println("GetBucketLifecycle id(test_hh):" + result.getRules().get(1).getId());
			System.out.println("GetBucketLifecycle Prefix(hh):" + result.getRules().get(1).getPrefix());
			System.out.println("GetBucketLifecycle Status(Enabled):" + result.getRules().get(1).getStatus());
			System.out.println("GetBucketLifecycle ExpirationInDays(tomorrow):" + result.getRules().get(1).getExpirationDate().toString() );
			
			//System.out.println("GetBucketLifecycle Result:\n" + result.getRules().get(0).toString() );
			s3.deleteBucketLifecycleConfiguration(bucketName);
			
			s3.deleteBucket(bucketName);
			
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
	
   
	private static void PutBucketLifecycle_403_InvalidAccessKeyId() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
       // s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);

        rules.add(rule1);
          
        System.out.println();
		System.out.println("PutBucketLifecycle_403_InvalidAccessKeyId");
		System.out.println("Expect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
		try
		{
			s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
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
	
	private static void PutBucketLifecycle_403_InvalidSecretKeyId() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
       // s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);

        rules.add(rule1);
          
        System.out.println();
		System.out.println("PutBucketLifecycle_403_InvalidSecretKeyId");
		System.out.println("Expect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
		try
		{
			s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
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
	
	private static void PutBucketLifecycle_404_NoSuchBucket() throws IOException
	{	    	
		String bucketName="nosuchkey";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		//System.out.println("Creating source bucket " + bucketName + "\n");
        // s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);

        rules.add(rule1);
          
        System.out.println();
		System.out.println("PutBucketLifecycle_404_NoSuchBucket");
		System.out.println("Expect 404 NoSuchBucket");
    	System.out.println("===================================================");
		try
		{
			s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
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
	
	
	private static void PutBucketLifecycle_403_PrefixOverlapping() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
        s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);

        Rule rule2 = new Rule();
        rule2.setId("test_hh");
        rule2.setPrefix("gghh");
        rule2.setStatus("Enabled");
        rule2.setExpirationInDays(2);
        
        rules.add(rule1);
        rules.add(rule2);
          
        System.out.println();
		System.out.println("PutBucketLifecycle_400_PrefixOverlapping");
		System.out.println("Expect 400 InvalidRequest");
    	System.out.println("===================================================");
		try
		{
			s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
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
		System.out.println("delete source bucket " + bucketName );
        s3.deleteBucket(bucketName);
	 }
	
	
	
	private static void GetBucketLifecycle_403_InvalidAccessKeyId() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
  
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);
        
        rules.add(rule1);
          
        System.out.println();
		System.out.println("GetBucketLifecycle_403_InvalidAccessKeyId");
		System.out.println("Expect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
		try
		{
			//s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
			BucketLifecycleConfiguration result = s3.getBucketLifecycleConfiguration(bucketName);
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
	
	private static void GetBucketLifecycle_403_InvalidSecretKeyId() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		
		//System.out.println("Creating source bucket " + bucketName + "\n");
        //s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);
        
        rules.add(rule1);
       
        System.out.println();
		System.out.println("GetBucketLifecycle_403_InvalidSecretKeyId");
		System.out.println("Expect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
		try
		{
			//s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
			BucketLifecycleConfiguration result = s3.getBucketLifecycleConfiguration(bucketName);
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
	
	private static void DelBucketLifecycle_403_InvalidAccessKeyId() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		
		System.out.println("Creating source bucket " + bucketName + "\n");
  
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);
        
        rules.add(rule1);
          
        System.out.println();
		System.out.println("DelBucketLifecycle_403_InvalidAccessKeyId");
		System.out.println("Expect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
		try
		{
			s3.deleteBucketLifecycleConfiguration(bucketName);
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
	
	private static void DelBucketLifecycle_403_InvalidSecretKeyId() throws IOException
	{	    	
		String bucketName="chttest3";
		
		AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		
		//System.out.println("Creating source bucket " + bucketName + "\n");
        //s3.createBucket(bucketName);
        
        List<Rule> rules = new ArrayList<Rule>(); 
        
        Rule rule1 = new Rule();
        rule1.setId("test_gg");
        rule1.setPrefix("gg");
        rule1.setStatus("Enabled");
        rule1.setExpirationInDays(1);
        
        rules.add(rule1);
       
        System.out.println();
		System.out.println("DelBucketLifecycle_403_InvalidSecretKeyId");
		System.out.println("Expect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
		try
		{
			//s3.setBucketLifecycleConfiguration(bucketName, new BucketLifecycleConfiguration().withRules(rules));
			s3.deleteBucketLifecycleConfiguration(bucketName);
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
	
		//basicLifecycle(); 	
		
		//PutBucketLifecycle
		PutBucketLifecycle_404_NoSuchBucket();
		PutBucketLifecycle_403_InvalidAccessKeyId();
		PutBucketLifecycle_403_InvalidSecretKeyId();
		PutBucketLifecycle_403_PrefixOverlapping();
				
		//GetBucketLifecycle
		GetBucketLifecycle_403_InvalidAccessKeyId();
		GetBucketLifecycle_403_InvalidSecretKeyId();
		
		//DelBucketLifecycle
		DelBucketLifecycle_403_InvalidAccessKeyId();
		DelBucketLifecycle_403_InvalidSecretKeyId();
		
	}
		
}
