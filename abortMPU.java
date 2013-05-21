import java.io.IOException; //
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;

public class abortMPU{
	
	private static void basicAbortMPU() throws IOException
	{
		System.out.println("basic abort MPU");
		String bucketName="chttest";
		String fileName="hello.txt";
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		//String uploadID = "LE5JS2K6C208JU7ZX1QD2TVRWXOWWF4VNG7LE7TFIX5SYNG4HLOGW9CLAD"; //hello
		String uploadID = "LM17F04FYDEROA6770ZURRUS3FN8XSSQY0SXJGDCDBW1ELEUEXRBE6EXF6";
		
		AbortMultipartUploadRequest request = new AbortMultipartUploadRequest(bucketName,fileName,uploadID);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			s3.abortMultipartUpload(request);
			
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
		//basicAbortMPU();
	}
		
}