import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.Permission;

public class getBucketACL{
	
   
    private static void basicPutBucketACL() throws IOException
    {
    	System.out.println("basic put bucket ACL");
    	
		String bucketName="chttest";
		AccessControlList acl = new AccessControlList();
		Owner owner = new Owner();
		owner.setDisplayName("hrchu");
		owner.setId("canonicalidhrchu");
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            acl.setOwner(owner);
            acl.grantPermission(new CanonicalGrantee("canonicalidannyren"), Permission.ReadAcp);
            acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            acl.grantPermission(new EmailAddressGrantee("Admin321@test.com"), Permission.WriteAcp);
            
            s3.setBucketAcl(bucketName, acl);          
            
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
    
    private static void basicGetBucketACL() throws IOException
    {
       	System.out.println("basic get bucket ACL");
    	
    		String bucketName="chttest";
 
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
    		try
    		{
    			AccessControlList bucketAcl = s3.getBucketAcl(bucketName);
    			System.out.println(bucketAcl.getGrants());
                
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
		//basicPutBucketACL();
		basicGetBucketACL();

	}
		
}