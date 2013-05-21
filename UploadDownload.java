import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException; //
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


public class UploadDownload{
	
    private static File createSampleFile() throws IOException {
    	//String filePath = "D:\\Local Repo\\hicloud-sdk-java-testing\\s3+sdk+unitest\\pic.jpg";
    	String filePath = "/home/chttl470/virtuosoTest/CentOS_6.2_PV.xva";
    	//String filePath = "C:\\CentOS_6.2_PV.xva";
    	File file = new File(filePath);

        return file;
    }
    
    private static void displayTextInputStream(InputStream input) throws IOException {
        //BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        OutputStream out = new FileOutputStream(new File("/home/chttl470/virtuosoTest/Result_CentOS_6.2_PV.xva"));
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = input .read(bytes)) != -1) {
           /* String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);*/
        	out.write(bytes,0,read);
        }
        out.flush();
        out.close();
        System.out.println();
    }
	
	public static void Upload(String bucketName, String objectName) throws IOException
	{
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
			System.out.println("Upload File");
			PutObjectRequest request = new PutObjectRequest(bucketName, objectName, createSampleFile());
           /* request.withProgressListener(new ProgressListener() {
    			public void progressChanged(ProgressEvent event) {
    				//System.out.println("Transferred bytes: " + 
    						//event.getBytesTransfered());
    			}
    		});*/
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
	
	private static void BasicPutBucket(String bucketName) throws IOException
	    {
			AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
			try
			{
				System.out.println("Creating bucket " + bucketName + "\n");
	            s3.createBucket(bucketName);
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
	
	public static void Download(String bucketName, String objectName) throws IOException
	{
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
		     System.out.println("Download File");
		     GetObjectRequest getrequest = new GetObjectRequest(bucketName,objectName);
		    /* getrequest.withProgressListener(new ProgressListener() 
		     {
		    	 public void progressChanged(ProgressEvent event) 
		    	 {
		    		 //System.out.println("Transferred bytes: " + event.getBytesTransfered());
		    		 //System.out.println("Event Code: " + event.getEventCode());
		    	 }
		     });*/
		     
		     S3Object object = s3.getObject(getrequest);
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
	
	public static void TearDown(String bucketName, String objectName) throws IOException
	{
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		
		try
		{
		     System.out.println("TearDown");
		     s3.deleteObject(bucketName, objectName);
		     s3.deleteBucket(bucketName);
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
    	String bucketName="transport";
    	String objectName="Hello.txt";
    	
    	
		System.out.println("hello world");
		
		BasicPutBucket(bucketName);
		long StartTime = System.currentTimeMillis(); // 取出目前時間
		Upload(bucketName, objectName);
		long ProcessTime = System.currentTimeMillis() - StartTime; // 計算處理時間
		System.out.println("Upload Time: " + ProcessTime + " ms");
		
		StartTime = System.currentTimeMillis();
		Download(bucketName, objectName);
		ProcessTime = System.currentTimeMillis() - StartTime; // 計算處理時間
		System.out.println("Upload Time: " + ProcessTime + " ms");
			
		TearDown(bucketName, objectName);
	}
}
	