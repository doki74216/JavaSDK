package nagative;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;

public class Neg_getObject{
    
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
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
    
    private static void mBasicPutObject() throws IOException
    {
    	System.out.println("basic put bucket");
    	
		String bucketName="chttest";
		String fileName="hello.txt";
		ObjectMetadata metadata = new ObjectMetadata() ;
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("../AwsCredentials.properties")));
		try
		{
			System.out.println("Creating bucket " + bucketName + "\n");
            s3.createBucket(bucketName);
            
            System.out.println("Listing buckets");
            for (Bucket bucket : s3.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();
            
            metadata.addUserMetadata("x-amz-meta-flower", "lily");
            metadata.addUserMetadata("x-amz-meta-color", "pink");
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, fileName, ReadFile(),metadata));
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
	
    private static void BasicGetObject() throws IOException
    {    	
    		String bucketName="chttest";
    		String fileName="hello.txt";
    		
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
    		try
    		{                
                System.out.println("Getting Object");
                S3Object object = s3.getObject(bucketName,fileName);
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
   
    private static void fGetObject() throws IOException
    {
		String bucketName="chttest";
		String fileName="hello.txt";
		
	   	File file = new File(fileName);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{                
            System.out.println("Getting Object");
            GetObjectRequest request = new GetObjectRequest(bucketName,fileName);
            ObjectMetadata metadata = s3.getObject(request,file);

          
            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write(metadata.getContentType());
            writer.write("\r\n");
            writer.write(metadata.getETag());
            writer.close();
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
    
    private static void headObject() throws IOException
    {
		String bucketName="chttest";
		String fileName="hello.txt";
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
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
    
    private static void vGetObject() throws IOException
    {
		String bucketName="chttest";
		String fileName="hello.txt";
		String vid = "a5a65e48a4654348969408f75d8fc52c"; 

		StringBuffer buffer = new StringBuffer(); 
		String line="";
				
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{                
            System.out.println("Getting Object with vid");
            GetObjectRequest request = new GetObjectRequest(bucketName,fileName,vid);
            S3Object object = s3.getObject(request);

            System.out.println(object.getKey());
            
            InputStream a = object.getObjectContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(a));
            while ((line = in.readLine()) != null)
            { 
                buffer.append(line); 
            } 
            System.out.println( buffer.toString());
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
    
    private static void vHeadObject() throws IOException
    {
		String bucketName="chttest";
		String fileName="hello.txt";
		String vid = "a5a65e48a4654348969408f75d8fc52c"; 

		StringBuffer buffer = new StringBuffer(); 
		String line="";
				
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{                
            System.out.println("head Object with vid");
            GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName,fileName,vid);
            ObjectMetadata object = s3.getObjectMetadata(request);

            System.out.println(object.getContentLength());
            System.out.println(object.getETag());
            System.out.println(object.getVersionId());
            System.out.println(object.getLastModified());       
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
    

    private static void pGetObject() throws IOException
    {    	
    		String bucketName="chttest";
    		String fileName="hello.txt";
    		ResponseHeaderOverrides parameters = new ResponseHeaderOverrides();
    		
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
    		try
    		{
    			parameters.setCacheControl("private");
    			parameters.setContentDisposition("attachment; filename=\"lalala.txt\"");
    			parameters.setContentEncoding("ZIP");
    			parameters.setContentLanguage("zw-TW");
    			parameters.setContentType("image/jpeg");
    			parameters.setExpires("0");
    			
                System.out.println("Getting Object");
                S3Object object = s3.getObject(new GetObjectRequest(bucketName,fileName).withResponseHeaders(parameters));
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getCacheControl());
                System.out.println("Content-Encoding: "  + object.getObjectMetadata().getContentEncoding());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getContentDisposition());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                System.out.println("Object Content: \n");
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
    
    private static void hGetObject() throws IOException
    {    	
    		String bucketName="chttest";
    		String fileName="hello.txt";
    		Date date = new Date();
    		date.setYear(date.getYear()-10);
    		date.setMonth(date.getMonth()+1);
    		date.setDate(date.getDate());
    		
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
    		try
    		{    			
                System.out.println("Getting Object");
                S3Object object = s3.getObject(new GetObjectRequest(bucketName,fileName).withMatchingETagConstraint("692b09f0ffdcd397f6af4243a1259b1e").withRange(5, 10).withModifiedSinceConstraint(date));
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getCacheControl());
                System.out.println("Content-Encoding: "  + object.getObjectMetadata().getContentEncoding());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getContentDisposition());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                System.out.println("Object Content: \n");
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
    
    private static void hGetObject2() throws IOException
    {    	
    		String bucketName="chttest";
    		String fileName="hello.txt";
    		
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
    		try
    		{    			
                System.out.println("Getting Object");
                S3Object object = s3.getObject(new GetObjectRequest(bucketName,fileName).withNonmatchingETagConstraint("692b09f0ffdcd397f6af4243a1259c1e"));
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getCacheControl());
                System.out.println("Content-Encoding: "  + object.getObjectMetadata().getContentEncoding());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getContentDisposition());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                System.out.println("Object Content: \n");
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
    
    private static void hGetObject3() throws IOException
    {    	
    		String bucketName="chttest";
    		String fileName="hello.txt";
    		Date date = new Date();
    		date.setYear(date.getYear());
    		date.setMonth(date.getMonth()+2);
    		date.setDate(date.getDate());
    		
    		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
    		try
    		{    			
                System.out.println("Getting Object");
                S3Object object = s3.getObject(new GetObjectRequest(bucketName,fileName).withUnmodifiedSinceConstraint(date));
                System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getCacheControl());
                System.out.println("Content-Encoding: "  + object.getObjectMetadata().getContentEncoding());
                System.out.println("Cache-Control: "  + object.getObjectMetadata().getContentDisposition());
                System.out.println("ETag: "  + object.getObjectMetadata().getETag());
                System.out.println("user-metadata: "  + object.getObjectMetadata().getUserMetadata());
                System.out.println("raw-metadata: "  + object.getObjectMetadata().getRawMetadata());
                System.out.println("Object Content: \n");
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
    
    private static void PLGetObject() throws IOException
    {    	
    		String bucketName="chttest2";
    		String fileName="hello.txt";
    		
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
    public static void main(String args[]) throws IOException
	{
		System.out.println("hello world");
		//PLGetObject(); //with progress listener
		GetObject_404_NoSuchBucket();	//make sure you don't have the bucket(chttest5)
		GetObject_404_NoSuchKey();	//make sure you have the bucket(chttest2) & don't have the key
		GetObject_403_InvalidAccessKeyId();
		GetObject_403_InvalidSecretKeyId();
	}
		
}