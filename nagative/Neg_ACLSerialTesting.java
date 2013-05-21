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
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class Neg_ACLSerialTesting{
	
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
    
 
    private static void PutBucketACL_403_InvalidAccessKeyId() throws IOException
    {
    	System.out.println("basic ACL InvalidAccessKeyId");
    	
    	String bucketName="chttest2";
		CannedAccessControlList aclCanned = null;
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));

		
		AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            acl.setOwner(owner);
            acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
            
            System.out.println("put bucket ACL");
            s3.setBucketAcl(bucketName, acl);    
            
            String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
            String temp2="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
            String temp3="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
            
			AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
			Set<Grant> grants = bucketAcl.getGrants();
			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1)
			{
				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
			}
			//System.out.println(bucketAcl.getGrants());
			
			System.out.println("put bucket ACL with Canned ACL");
			s3.setBucketAcl(bucketName, aclCanned.PublicRead);
			
			AccessControlList CannedAcl = s3.getBucketAcl(bucketName);
			Set<Grant> result = CannedAcl.getGrants();
			
            temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
            temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers"; 
			if(result.toString().indexOf(temp)==-1 || result.toString().indexOf(temp2)==-1)
			{
				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
			}
			//System.out.println(CannedAcl.getGrants());
			
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
    
    private static void PutBucketACL_403_InvalidSecretKeyId() throws IOException
	{
		System.out.println("ACL InvalidSecretKeyId");
    	
		String bucketName="chttest2";
		CannedAccessControlList aclCanned = null;
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
    	AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            acl.setOwner(owner);
            acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
            
            System.out.println("put bucket ACL");
            s3.setBucketAcl(bucketName, acl);    
            
        /*    String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
            String temp2="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
            String temp3="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
            
			AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
			Set<Grant> grants = bucketAcl.getGrants();
			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1)
			{
				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
			}
			//System.out.println(bucketAcl.getGrants());
			
			System.out.println("put bucket ACL with Canned ACL");
			s3.setBucketAcl(bucketName, aclCanned.PublicRead);
			
			AccessControlList CannedAcl = s3.getBucketAcl(bucketName);
			Set<Grant> result = CannedAcl.getGrants();
			
            temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
            temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers"; 
			if(result.toString().indexOf(temp)==-1 || result.toString().indexOf(temp2)==-1)
			{
				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
			}
			//System.out.println(CannedAcl.getGrants());*/
			
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
    
    private static void PutBucketACL_NOowner() throws IOException
	{
		String bucketName="chttest2";
		CannedAccessControlList aclCanned = null;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
        
		AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
           // acl.setOwner(owner);
            acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
            
            System.out.println("put bucket ACL");
            s3.setBucketAcl(bucketName, acl);    
            
         /*   String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
            String temp2="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
            String temp3="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
            
			AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
			Set<Grant> grants = bucketAcl.getGrants();
			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1)
			{
				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
			}
			//System.out.println(bucketAcl.getGrants());
			
			System.out.println("put bucket ACL with Canned ACL");
			s3.setBucketAcl(bucketName, aclCanned.PublicRead);
			
			AccessControlList CannedAcl = s3.getBucketAcl(bucketName);
			Set<Grant> result = CannedAcl.getGrants();
			
            temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
            temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers"; 
			if(result.toString().indexOf(temp)==-1 || result.toString().indexOf(temp2)==-1)
			{
				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
			}
			//System.out.println(CannedAcl.getGrants());
			
			System.out.println("Tear down..");
	        s3.deleteBucket(bucketName);*/
            
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
    
    private static void PutBucketACL_InvalidCanonicalId() throws IOException
 	{
 		String bucketName="chttest2";
 		CannedAccessControlList aclCanned = null;
 		
 		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
         
 		AccessControlList acl = new AccessControlList();
 		Owner owner = new Owner();
 		owner.setDisplayName("hrchu");
 		owner.setId("canonicalidhrchu");
 		
 		try
 		{
 			System.out.println("Creating bucket " + bucketName + "\n");
             s3.createBucket(bucketName);
             
             acl.setOwner(owner);
             acl.grantPermission(new CanonicalGrantee("canonicalidannyrenerror"), Permission.ReadAcp);
             acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
             acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
             
             System.out.println("put bucket ACL");
             s3.setBucketAcl(bucketName, acl);    
             
         /*    String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
             String temp2="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
             String temp3="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
             
 			AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
 			Set<Grant> grants = bucketAcl.getGrants();
 			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1)
 			{
 				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
 			}
 			//System.out.println(bucketAcl.getGrants());
 			
 			System.out.println("put bucket ACL with Canned ACL");
 			s3.setBucketAcl(bucketName, aclCanned.PublicRead);
 			
 			AccessControlList CannedAcl = s3.getBucketAcl(bucketName);
 			Set<Grant> result = CannedAcl.getGrants();
 			
             temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
             temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers"; 
 			if(result.toString().indexOf(temp)==-1 || result.toString().indexOf(temp2)==-1)
 			{
 				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
 			}
 			//System.out.println(CannedAcl.getGrants());
 			
 			System.out.println("Tear down..");
 	        s3.deleteBucket(bucketName);*/
             
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
    
    private static void PutBucketACL_InvalidEmail() throws IOException
 	{
 		String bucketName="chttest2";
 		CannedAccessControlList aclCanned = null;
 		
 		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
         
 		AccessControlList acl = new AccessControlList();
 		Owner owner = new Owner();
 		owner.setDisplayName("hrchu");
 		owner.setId("canonicalidhrchu");
 		
 		try
 		{
 			System.out.println("Creating bucket " + bucketName + "\n");
             s3.createBucket(bucketName);
             
             acl.setOwner(owner);
             acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
             acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
             acl.grantPermission(new EmailAddressGrantee("Admin321error@test.com"), Permission.WriteAcp);
             
             System.out.println("put bucket ACL");
             s3.setBucketAcl(bucketName, acl);    
             
         /*    String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
             String temp2="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
             String temp3="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
             
 			AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
 			Set<Grant> grants = bucketAcl.getGrants();
 			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1)
 			{
 				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
 			}
 			//System.out.println(bucketAcl.getGrants());
 			
 			System.out.println("put bucket ACL with Canned ACL");
 			s3.setBucketAcl(bucketName, aclCanned.PublicRead);
 			
 			AccessControlList CannedAcl = s3.getBucketAcl(bucketName);
 			Set<Grant> result = CannedAcl.getGrants();
 			
             temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
             temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers"; 
 			if(result.toString().indexOf(temp)==-1 || result.toString().indexOf(temp2)==-1)
 			{
 				System.out.println("ERROR!!!\n GetBucket ACL Error\n");
 			}
 			//System.out.println(CannedAcl.getGrants());
 			
 			System.out.println("Tear down..");
 	        s3.deleteBucket(bucketName);*/
             
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
    
    private static void Teardown() throws IOException
    {
    	System.out.println("Tear down..");
    	String bucketName="chttest2";

		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{			
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
		PutBucketACL_403_InvalidAccessKeyId();
		
		/*
		 * test InvalidSecretKeyId
		 */
		PutBucketACL_403_InvalidSecretKeyId();
		
		/*
		 * test doesn't have owner
		 */
		PutBucketACL_NOowner();
		Teardown();
		
		/*
		 * test acl invalid canonicalid
		 */
		PutBucketACL_InvalidCanonicalId();
		Teardown();
		
		/*
		 * test acl invalid email address
		 */
		PutBucketACL_InvalidEmail();
		Teardown();
		
		System.out.println("Neg_ACLSerialTest Over");

		
	}
		
}