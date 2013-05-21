import java.io.IOException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PartSummary;

public class ListParts{
	
	private static void basicListParts() throws IOException
	{
		System.out.println("basic Upload Part");
		String bucketName="chttest";
		String fileName="hello.txt";
		String uploadID = "535K6QPZ6AKYUM9YI63O5BOV1VP6DBYR4D082XTVFKTIPV2ZI5OQ19EJ8M"; //hello
		//String uploadID = "XHGTFV4F5XTEAC5O8N3LK12TIY3DSY7OFPXIWTHRMNTE7A3WB5M8N2U5AN"; //hi
		
		ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withMaxParts(3);
		//ListPartsRequest request = new ListPartsRequest(bucketName,fileName,uploadID).withPartNumberMarker(5);
		
		AmazonS3 s3 = new AmazonS3Client(new PropertiesCredentials(putBucket.class.getResourceAsStream("AwsCredentials.properties")));
		try
		{
			PartListing result = s3.listParts(request);
			for(PartSummary s : result.getParts())
			{
				System.out.println(s.getPartNumber());
				System.out.println(s.getLastModified());
				System.out.println(s.getSize());
				System.out.println(s.getETag());
			}
			//System.out.println(result.getParts());
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
		basicListParts();
	}
		
}