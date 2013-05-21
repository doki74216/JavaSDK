package nagative;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
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
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.StorageClass;

public class S_Neg_ObjectSerialTesting{
	
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
    
    private static void DelObject_403_InvalidAccessKeyId() throws IOException
	{		
		String bucketName = "chttest2";
		String fileName="hello.txt";
		
		System.out.println("\nExpect 403 InvalidAccessKeyId");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    	
		try
		{
			System.out.println("Deleting object " + fileName + "\n");
	        s3.deleteObject(bucketName, fileName);

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
	
	private static void DelObject_403_InvalidSecretKeyId() throws IOException
	{		
		String bucketName = "chttest2";
		String fileName="hello.txt";
		
		System.out.println("\nExpect 403 SignatureDoesNotMatch");
    	System.out.println("===================================================");
    	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
		try
		{
			System.out.println("Deleting object " + fileName + "\n");
	        s3.deleteObject(bucketName, fileName);

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
	
	
	private static void GetObject_404_NoSuchBucket() throws IOException
    {    	
    		String bucketName="chttest5";
    		String fileName="nosuchkey.txt";
    		System.out.println("\nExpect 404 NoSuchBucket");
        	System.out.println("===================================================");
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
    		try
    		{                
                System.out.println("Getting Object");
                GetObjectRequest request = new GetObjectRequest(bucketName,fileName);
                request.withProgressListener(new ProgressListener() 
                {
        			public void progressChanged(ProgressEvent event) {
        				System.out.println("Transferred bytes: " + event.getBytesTransfered());
        				System.out.println("Event Code: " + event.getEventCode());
        			}
        		});
                S3Object object = s3.getObject(request);
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                displayTextInputStream(object.getObjectContent());
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
    
    private static void GetObject_404_NoSuchKey() throws IOException
    {    	
    		String bucketName="chttest2";
    		String fileName="nosuchkey.txt";
    		System.out.println("\nExpect 404 NoSuchKey");
        	System.out.println("===================================================");
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
    		try
    		{                
                System.out.println("Getting Object");
                GetObjectRequest request = new GetObjectRequest(bucketName,fileName);
                request.withProgressListener(new ProgressListener() 
                {
        			public void progressChanged(ProgressEvent event) {
        				System.out.println("Transferred bytes: " + event.getBytesTransfered());
        				System.out.println("Event Code: " + event.getEventCode());
        			}
        		});
                S3Object object = s3.getObject(request);
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                displayTextInputStream(object.getObjectContent());
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
    
    private static void GetObject_403_InvalidAccessKeyId() throws IOException
    {    	
    		String bucketName="chttest2";
    		String fileName="hello.txt";
    		
        	System.out.println("\nExpect 403 InvalidAccessKeyId");
        	System.out.println("===================================================");
        	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("Aedc98059ceb7f848d819e3da1400ab00", "8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    		try
    		{                
                System.out.println("Getting Object");
                GetObjectRequest request = new GetObjectRequest(bucketName,fileName);
                request.withProgressListener(new ProgressListener() 
                {
        			public void progressChanged(ProgressEvent event) {
        				System.out.println("Transferred bytes: " + event.getBytesTransfered());
        				System.out.println("Event Code: " + event.getEventCode());
        			}
        		});
                S3Object object = s3.getObject(request);
                
                //S3Object object = s3.getObject(bucketName,fileName);
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                displayTextInputStream(object.getObjectContent());
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
    
    private static void GetObject_403_InvalidSecretKeyId() throws IOException
    {    	
    		String bucketName="chttest2";
    		String fileName="hello.txt";
    		
    		System.out.println("\nExpect 403 SignatureDoesNotMatch");
        	System.out.println("===================================================");
        	AmazonS3  s3 = new AmazonS3Client((AWSCredentials) new BasicAWSCredentials("edc98059ceb7f848d819e3da1400ab00", "A8ca94ece8b03b8f44210ef31d0e8e41eae6cd554bf48557581fdd47685dbe799"));
    		try
    		{                
                System.out.println("Getting Object");
                GetObjectRequest request = new GetObjectRequest(bucketName,fileName);
                request.withProgressListener(new ProgressListener() 
                {
        			public void progressChanged(ProgressEvent event) {
        				System.out.println("Transferred bytes: " + event.getBytesTransfered());
        				System.out.println("Event Code: " + event.getEventCode());
        			}
        		});
                S3Object object = s3.getObject(request);
                
                //S3Object object = s3.getObject(bucketName,fileName);
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                displayTextInputStream(object.getObjectContent());
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
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
    
    public static void main(String args[]) throws IOException
	{
    	
    	//PutObject
		PutObject_404_NoSuchBucket();	// make sure you don't have the bucket
		PutObject_403_InvalidAccessKeyId();
		PutObject_403_InvalidSecretKeyId();
		
		//DeleteObject
		DelObject_403_InvalidAccessKeyId();
		DelObject_403_InvalidSecretKeyId();
		
		//GetObject
		GetObject_404_NoSuchBucket();	//make sure you don't have the bucket(chttest5)
		GetObject_404_NoSuchKey();	//make sure you have the bucket(chttest2) & don't have the key
		GetObject_403_InvalidAccessKeyId();
		GetObject_403_InvalidSecretKeyId();
		
		//HeadObject
		headObject_404_NotFound();	//make sure you don't have the bucket or key
    	HeadObject_403_InvalidAccessKeyId();
    	HeadObject_403_InvalidSecretKeyId();
    	
    	
    	//PutObjectCopy
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