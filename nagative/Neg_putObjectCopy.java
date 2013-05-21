package nagative;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.StorageClass;

public class Neg_putObjectCopy{
	
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
    
    private static void basicPutBucket(String bName , String fName) throws IOException
    {
    	//System.out.println("basic put bucket");
    	
		String bucketName = bName;
		String fileName = fName;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            /*
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            */
            if(bucketName=="chttest")
            {
            	System.out.println("Uploading a new object to S3 from a file\n");
            	s3.putObject(new PutObjectRequest(bucketName, fileName, createSampleFile()));
            }
            
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
	
    private static void basicCopyObject(String sbName , String sfName, String dbName , String dfName) throws IOException
    {
    	String sbucketName = sbName;
    	String dbucketName = dbName;
    	String sfileName = sfName;
    	String dfileName = dfName;
    	
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{            
            System.out.println("Copy object...\n");
            CopyObjectResult result = s3.copyObject(sbucketName, sfileName, dbucketName, dfileName);
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
    
    private static void GeneralCopyObject(String sbName , String sfName, String dbName , String dfName) throws IOException
    {
    	String sbucketName = sbName;
    	String dbucketName = dbName;
    	String sfileName = sfName;
    	String dfileName = dfName;
    	StorageClass storageClass = null;
    	CannedAccessControlList cannedAcl = null;
    	ObjectMetadata metadata = new ObjectMetadata() ;
		Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate());
    	
		Date date2 = new Date();
		date2.setYear(date2.getYear());
		date2.setMonth(date2.getMonth()+2);
		date2.setDate(date2.getDate());
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			metadata.addUserMetadata("x-amz-meta-company", "chttl");
			metadata.addUserMetadata("x-amz-meta-department", "cloud");
			metadata.setContentType("image/jpeg");
	            
            System.out.println("Copy object...\n");
            //with storage class
            //CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withStorageClass(storageClass.ReducedRedundancy));
            
            //with storageClass, acl, metadata, if-match, if-modify-since
            //CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withStorageClass("STANDARD").withCannedAccessControlList(cannedAcl.PublicRead).withNewObjectMetadata(metadata).withMatchingETagConstraint("692b09f0ffdcd397f6af4243a1259b1e").withModifiedSinceConstraint(date));
            
            //with if-non-match
            //CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withNonmatchingETagConstraint("692b09f0ffdcd397f6af4243a1259b1c"));
            
            //with if-non-modify-since
    		//CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withUnmodifiedSinceConstraint(date2));
    		
            //with version id
            CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withSourceVersionId("72b3f39f520c411db753039b7567d069"));
            
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
    
    
    private static void putObjectCopy_404_NoSuchBucket(String sbName , String sfName, String dbName , String dfName) throws IOException
    {
    	String sbucketName = sbName;
    	String dbucketName = dbName;
    	String sfileName = sfName;
    	String dfileName = dfName;
    	StorageClass storageClass = null;
    	CannedAccessControlList cannedAcl = null;
    	ObjectMetadata metadata = new ObjectMetadata() ;
		Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate());
    	
		Date date2 = new Date();
		date2.setYear(date2.getYear());
		date2.setMonth(date2.getMonth()+2);
		date2.setDate(date2.getDate());
		
		System.out.println("\nExpect 404 NoSuchBucket");
    	System.out.println("===================================================");
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			metadata.addUserMetadata("x-amz-meta-company", "chttl");
			metadata.addUserMetadata("x-amz-meta-department", "cloud");
			metadata.setContentType("image/jpeg");
	            
            System.out.println("Copy object...\n");
            CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withSourceVersionId("72b3f39f520c411db753039b7567d069"));
            
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
    
    private static void putObjectCopy_404_NoSuchVersion(String sbName , String sfName, String dbName , String dfName) throws IOException
    {
    	String sbucketName = sbName;
    	String dbucketName = dbName;
    	String sfileName = sfName;
    	String dfileName = dfName;
    	StorageClass storageClass = null;
    	CannedAccessControlList cannedAcl = null;
    	ObjectMetadata metadata = new ObjectMetadata() ;
		Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate());
    	
		Date date2 = new Date();
		date2.setYear(date2.getYear());
		date2.setMonth(date2.getMonth()+2);
		date2.setDate(date2.getDate());
		
		System.out.println("\nExpect 404 NoSuchVersion");
    	System.out.println("===================================================");
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			metadata.addUserMetadata("x-amz-meta-company", "chttl");
			metadata.addUserMetadata("x-amz-meta-department", "cloud");
			metadata.setContentType("image/jpeg");
	            
            System.out.println("Copy object...\n");
            CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withSourceVersionId("72b3f39f520c411db753039b7567d069"));
            
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
    
    private static void putObjectCopy_403_InvalidAccessKeyId(String sbName , String sfName, String dbName , String dfName) throws IOException
    {
    	String sbucketName = sbName;
    	String dbucketName = dbName;
    	String sfileName = sfName;
    	String dfileName = dfName;
    	StorageClass storageClass = null;
    	CannedAccessControlList cannedAcl = null;
    	ObjectMetadata metadata = new ObjectMetadata() ;
		Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate());
    	
		Date date2 = new Date();
		date2.setYear(date2.getYear());
		date2.setMonth(date2.getMonth()+2);
		date2.setDate(date2.getDate());
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		try
		{
			metadata.addUserMetadata("x-amz-meta-company", "chttl");
			metadata.addUserMetadata("x-amz-meta-department", "cloud");
			metadata.setContentType("image/jpeg");
	            
            System.out.println("Copy object...\n");
            CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withSourceVersionId("72b3f39f520c411db753039b7567d069"));
            
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
    
    private static void putObjectCopy_403_InvalidSecretKeyId(String sbName , String sfName, String dbName , String dfName) throws IOException
    {
    	String sbucketName = sbName;
    	String dbucketName = dbName;
    	String sfileName = sfName;
    	String dfileName = dfName;
    	StorageClass storageClass = null;
    	CannedAccessControlList cannedAcl = null;
    	ObjectMetadata metadata = new ObjectMetadata() ;
		Date date = new Date();
		date.setYear(date.getYear()-10);
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate());
    	
		Date date2 = new Date();
		date2.setYear(date2.getYear());
		date2.setMonth(date2.getMonth()+2);
		date2.setDate(date2.getDate());
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		try
		{
			metadata.addUserMetadata("x-amz-meta-company", "chttl");
			metadata.addUserMetadata("x-amz-meta-department", "cloud");
			metadata.setContentType("image/jpeg");
	            
            System.out.println("Copy object...\n");
            CopyObjectResult result = s3.copyObject(new CopyObjectRequest(sbucketName, sfileName, dbucketName, dfileName).withSourceVersionId("72b3f39f520c411db753039b7567d069"));
            
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
    	
    	String sbucketName = "chttest5";
    	String dbucketName = "chttest6";
    	String sfileName = "apple.txt";
    	String dfileName = "world.txt";
    	
    	//make sure you don't have the bucket, Or you will get NoSuchVersion
		putObjectCopy_404_NoSuchBucket(sbucketName,sfileName,dbucketName,dfileName); 
		
		basicPutBucket(sbucketName,sfileName);
		basicPutBucket(dbucketName,dfileName);
		putObjectCopy_404_NoSuchVersion(sbucketName,sfileName,dbucketName,dfileName);
		
		putObjectCopy_403_InvalidAccessKeyId(sbucketName,sfileName,dbucketName,dfileName);
		putObjectCopy_403_InvalidSecretKeyId(sbucketName,sfileName,dbucketName,dfileName);
	}
		
}