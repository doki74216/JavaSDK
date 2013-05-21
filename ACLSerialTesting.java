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
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.VersionListing;



public class ACLSerialTesting{

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
    
	private static void basicPutBucketACL() throws IOException
	{
		String bucketName="chttest2";
		CannedAccessControlList aclCanned = null;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
        
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
	
	private static void vPutObjectACL() throws IOException
	{
		String bucketName="chttest2";
		String fileName="hello.txt";
		AccessControlList acl = new AccessControlList();
		CannedAccessControlList Cannedacl = null;
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");

		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
        
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            System.out.println("basic put bucket versioning");
    		BucketVersioningConfiguration config = new BucketVersioningConfiguration();
    		config.setStatus("Enabled");
    		SetBucketVersioningConfigurationRequest version = new SetBucketVersioningConfigurationRequest(bucketName,config);
    		s3.setBucketVersioningConfiguration(version); 
    		
        	System.out.println("Uploading a new object to S3 from a file\n");
        	PutObjectResult object = s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            String vid = object.getVersionId();
  
    		acl.setOwner(owner);
            acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
            acl.grantPermission(new CanonicalGrantee("canonicalidhrchu"), Permission.FullControl);
            System.out.println("Put object ACL...\n");
            s3.setObjectAcl(bucketName, fileName, vid, acl);  
            
            System.out.println("Get object ACL...\n");
 			AccessControlList objectAcl = s3.getObjectAcl(bucketName, fileName,vid);
 			//System.out.println(objectAcl.getGrants());   
            String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
            String temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
            String temp3="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
            String temp4="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
             
 			Set<Grant> grants = objectAcl.getGrants();
 			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1 || grants.toString().indexOf(temp4)==-1)
 			{
 				System.out.println("ERROR!!!\n GetObject ACL Error with vid\n");
 			}
            
        	System.out.println("Put object ACL with canned acl + version id \n");
            s3.setObjectAcl(bucketName, fileName, vid, Cannedacl.PublicReadWrite);
            
            System.out.println("Get object ACL...\n");
  			AccessControlList result = s3.getObjectAcl(bucketName, fileName,vid);
  			//System.out.println(result.getGrants());   
            temp="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=WRITE"; 
            temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
            temp3="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
              
  			Set<Grant> Cannedgrants = result.getGrants();
  			if(Cannedgrants.toString().indexOf(temp)==-1 || Cannedgrants.toString().indexOf(temp2)==-1 || Cannedgrants.toString().indexOf(temp3)==-1)
  			{
  				System.out.println("ERROR!!!\n GetObject with Canned ACL + vid Error \n");
  			}
    		
    		System.out.println("Tear down..");
    		VersionListing clear = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
			for(S3VersionSummary s : clear.getVersionSummaries())
			{
				System.out.println(s.getBucketName());
				System.out.println(s.getKey());
				System.out.println(s.getVersionId());
				s3.deleteVersion(s.getBucketName(), s.getKey(), s.getVersionId());
			}
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

	
	private static void basicPutObjectACL() throws IOException
	{
		String bucketName="chttest2";
		String fileName="hello.txt";
		AccessControlList acl = new AccessControlList();
		CannedAccessControlList Cannedacl = null;
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
		
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            
            acl.setOwner(owner);
            acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
            acl.grantPermission(new CanonicalGrantee("canonicalidhrchu"), Permission.FullControl);
            System.out.println("Put object ACL...\n");
            s3.setObjectAcl(bucketName, fileName, acl);   
            
            System.out.println("Get object ACL...\n");
			AccessControlList objectAcl = s3.getObjectAcl(bucketName, fileName);
			//System.out.println(objectAcl.getGrants());   
            String temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
            String temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
            String temp3="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@531b3243, permission=WRITE_ACP"; 
            String temp4="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fd3816f4, permission=READ_ACP"; 
            
			Set<Grant> grants = objectAcl.getGrants();
			if(grants.toString().indexOf(temp)==-1 || grants.toString().indexOf(temp2)==-1 || grants.toString().indexOf(temp3)==-1 || grants.toString().indexOf(temp4)==-1)
			{
				System.out.println("ERROR!!!\n GetObject ACL Error\n");
			}
            
        	System.out.println("Put object ACL with canned acl...\n");
            s3.setObjectAcl(bucketName, fileName, Cannedacl.PublicReadWrite);
            System.out.println("Get object ACL...\n");
  			AccessControlList result = s3.getObjectAcl(bucketName, fileName);
  			//System.out.println(result.getGrants());   
            temp="grantee=com.amazonaws.services.s3.model.CanonicalGrantee@fc206bb7, permission=FULL_CONTROL"; 
            temp2="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=WRITE"; 
            temp3="grantee=GroupGrantee [http://acs.amazonaws.com/groups/global/AllUsers], permission=READ"; 
              
  			Set<Grant> Cannedgrants = result.getGrants();
  			if(Cannedgrants.toString().indexOf(temp)==-1 || Cannedgrants.toString().indexOf(temp2)==-1 || Cannedgrants.toString().indexOf(temp3)==-1)
  			{
  				System.out.println("ERROR!!!\n GetObject with Canned ACLError\n");
  			}
            
			
			System.out.println("Tear down..");
			s3.deleteObject(bucketName, fileName);
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
		 * test 1. PutBucketACL with user defined ACL 
		 *      2. PutBucketACL with canned ACL 
		 *      3. GetBucketACL
		 */
		basicPutBucketACL();
		
		/* 
		 * test 1. PutObjectACL with user defined ACL 
		 *      2. PutObjectACL with canned ACL 
		 *      3. GetObjectACL
		 */
		basicPutObjectACL();
		
		/* 
		 * test 1. PutObjectACL with user defined ACL with vid
		 *      2. PutObjectACL with canned ACL with vid
		 *      3. GetObjectACL with vid
		 */
		vPutObjectACL();
		
		System.out.println("ACLSerialTest Over");
		
	}
		
}
