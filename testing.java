import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.util.DateUtils;

public class testing{
	
    public static void main(String args[]) throws IOException, Exception
	{
    	//String old = "Thu Jan 03 13:16:06 GMT+08:00 2013"; //NG
    	//String old= "Thu, 03 Jan 2013 13:58:12 GMT+08:00";
    	String old = "2013-01-03 14:40:02.0";
    	String month="";
    	String day="";
    	String year="";
    	String time="";
    	
    	old = old.replace("GMT+08:00", "-");
    	System.out.println(old);
    	
    	Pattern pat;
    	Matcher mat;
    	Boolean found;
    	
    	pat = Pattern.compile("[-][0-9][0-9][-][0-9][0-9]");
    	mat = pat.matcher(old);
    	found=mat.find();
    	System.out.println(found);
    	System.out.println(mat.group(0));
    	if(found==true)
    	{
    		String temp = mat.group();
    		month = temp.substring(1, 3);
    		day = temp.substring(4, 6);
    		
    	}
   /* 	
    	pat = Pattern.compile("[a-zA-Z][a-z][a-z]");
    	mat = pat.matcher(old);
    	found=mat.find();
    	if(found==true)
    	{
    		month = mat.group();
    		if(month.equalsIgnoreCase("Jan")){month="01";}
        	else if(month.equalsIgnoreCase("Feb")){month="02";}
        	else if(month.equalsIgnoreCase("Mar")){month="03";}
        	else if(month.equalsIgnoreCase("Apr")){month="04";}
        	else if(month.equalsIgnoreCase("May")){month="05";}
        	else if(month.equalsIgnoreCase("Jun")){month="06";}
        	else if(month.equalsIgnoreCase("Jul")){month="07";}
        	else if(month.equalsIgnoreCase("Aug")){month="08";}
        	else if(month.equalsIgnoreCase("Sep")){month="09";}
        	else if(month.equalsIgnoreCase("Oct")){month="10";}
        	else if(month.equalsIgnoreCase("Nov")){month="11";}
        	else if(month.equalsIgnoreCase("Dec")){month="12";}
    	}*/
    	
    	pat = Pattern.compile("[0-9][0-9][0-9][0-9]");
    	mat = pat.matcher(old);
    	found=mat.find();
    	if(found==true)
    	{
    		year = mat.group();
    	}
    	
    	pat = Pattern.compile("[0-9][0-9][:][0-9][0-9][:][0-9][0-9]");
    	mat = pat.matcher(old);
    	found=mat.find();
    	if(found==true)
    	{
    		time = mat.group();
    	}
    	
       	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    	Date temp = dateFormat.parse(time);
    	temp.setHours(temp.getHours()-8);
    	time = temp.getHours() + ":" + temp.getMinutes() + ":" + temp.getMinutes();
    	
    	String result = year + '-' + month + '-' + day + 'T' + time + 'Z';
    	System.out.println(result);
 /*   	
    	try
    	{
    		DateUtils dateUtils= new DateUtils();
			Date times = dateUtils.parseIso8601Date(result);
    		System.out.println(times);
    	}
    	catch (ParseException e) {
    		throw new RuntimeException("Non-ISO8601 date for LastModified in copy object output: "+ result, e);
    		}*/
    	//System.out.println(found);
    	//System.out.println(mat.group());
    	
    	
    	//String text = "2013-01-03T02:07:09Z"; //OK
    	//String text = "2013-01-03T02:07:09.000Z"; //OK
    	//String text = "2013-01-03T11:26:09Z"; 
    	
 /*   	
    	
    	String month=old.substring(4, 7);
    	if(month.equalsIgnoreCase("Jan")){month="01";}
    	else if(month.equalsIgnoreCase("Feb")){month="02";}
    	else if(month.equalsIgnoreCase("Mar")){month="03";}
    	else if(month.equalsIgnoreCase("Apr")){month="04";}
    	else if(month.equalsIgnoreCase("May")){month="05";}
    	else if(month.equalsIgnoreCase("Jun")){month="06";}
    	else if(month.equalsIgnoreCase("Jul")){month="07";}
    	else if(month.equalsIgnoreCase("Aug")){month="08";}
    	else if(month.equalsIgnoreCase("Sep")){month="09";}
    	else if(month.equalsIgnoreCase("Oct")){month="10";}
    	else if(month.equalsIgnoreCase("Nov")){month="11";}
    	else if(month.equalsIgnoreCase("Dec")){month="12";}
    	//else{month="null";}

    	String day=old.substring(8, 10);
    	String time=old.substring(11, 19);
    	String year=old.substring(30, 34);
    	
    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    	Date temp = dateFormat.parse(time);
    	temp.setHours(temp.getHours()-8);
    	time = temp.getHours() + ":" + temp.getMinutes() + ":" + temp.getMinutes();
    	
    //	System.out.println(temp);
    	
    	
    	//time = hour.v
    	
    	String result = year + '-' + month + '-' + day + 'T' + time + 'Z';
    	System.out.println(result);
    	
    	
    	try
    	{
    		DateUtils dateUtils= new DateUtils();
			Date times = dateUtils.parseIso8601Date(result);
    		System.out.println(times);
    	}
    	catch (ParseException e) {
    		throw new RuntimeException("Non-ISO8601 date for LastModified in copy object output: "+ result, e);
    		}*/
    	

	}
		
}