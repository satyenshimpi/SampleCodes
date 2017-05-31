package com.delphix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

//This solution uses the APIs available at coderpad. So this will use the coderpad constraints.
public class Solution {
   //constants for NASA apis
   public static final String NASA_API_URL = "https://api.nasa.gov/planetary/earth/assets";
   public static final String DATE_TAG = "date";
   public static final String RESULTS_TAG = "results";
   public static final String COUNT_TAG = "count";
   public static final String LONGITUDE_TAG = "lon";
   public static final String LATITUDE_TAG = "lat";
   public static final String API_KEY_TAG = "api_key";
   public static final String API_KEY = "DEMO_KEY";
   public static final SimpleDateFormat SDF_RESULTS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //date format in apis result
   
   //constant for the time duration.
   //we can get the time delta in two format Day or Hours.
   //Setting default delta format as number of Days
   private static final ResultType resultType = ResultType.DAY;
   private static final long DAY_AS_LONG = (24 * 60 * 60 * 1000);
   private static final long HOUR_AS_LONG = (60 * 60 * 1000);
   
   //Minimum number of sample set required to calculate effective
   private static final int MIN_RESULT_CNT = 2;                //minimum required number of elements in sample set
   
   //logging purpose. Can use Logj
   private static Logger log = Logger.getLogger("Sample");
   private static boolean isDebug = true;
   
   public static void main(String[] args) {
      log.setLevel(Level.ALL);
      System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s %5$s%6$s%n");
      flyby(1.5, 100.75);       
   }
		
   public static void flyby(double latitude, double longitude){
      //calling by fixed start date and no end Date.
	   //as per API doc begin date is necessary
      Date beginDate = new Date(114,1,2);    //for testing passing date 2014-02-02
      flyby(latitude, longitude, beginDate, null);
   }
   
   //this takes additional params beginDate and endDate
   public static void flyby(double latitude, double longitude, Date beginDate, Date endDate){
      String json = null;
      URL url = constructURL(latitude, longitude, beginDate, endDate);
      if(url != null)      //or url is not malformed
         json = fetchJSON(url);
      if(json != null)
         parseJSON(SDF_RESULTS, json);
   }
   
   //Fetch JSON data from given URL using GET method
	private static String fetchJSON(URL url) {
		if (url == null) {
			log.warning("Non null API url should be provided");
			return null;
		}

		StringBuilder sb = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				log.severe("Failed: HTTP error code : " + conn.getResponseCode());
				return null;
			}

			// using java.net package due coderpad constraints
			// For better parsing and calling REST servises we can use special
			// APIs like Jersey.
			// using simple BufferedReader for reading input
			InputStream inputStream = conn.getInputStream();
			if (inputStream == null) {
				log.severe("Could not read the output from API call");
				return null;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			log.severe("ERROR while fetching JSON.");
			return null;
		}
		String json = sb.toString();
		if (isDebug) {
			log.log(Level.INFO, "DEBUG: JSON-> " + json);
		}
		return json;
	}
   
   //Parse JSON into useful data
   private static void parseJSON(SimpleDateFormat sdfResults, String json) {
      JSONParser parser = new JSONParser();
      JSONObject jsonObject;
      int maxFreqElement = 0;
      int maxFreq = 0;
      Date last_date = null;     //last date when image was captured
      
      try {
         jsonObject = (JSONObject)parser.parse(json);
         JSONArray results = (JSONArray)jsonObject.get(RESULTS_TAG);   //get the results into array

         if (results != null && !results.isEmpty()) {
            if(results.size() < MIN_RESULT_CNT){
               log.warning("Result set doesn't have minimum number of result. Size:" + results.size());
               return;
            }
            
            Iterator<JSONObject> iterator = results.iterator();

            Date prevDate = null;
            //map of element as key and frequency as value
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            
            while (iterator.hasNext()) {
               JSONObject result = (JSONObject)iterator.next();
				Date newDate = sdfResults.parse(result.get(DATE_TAG).toString());
				// truncate Time from date so that we can get the difference in term of days or hours.
				//this way the diff between two dates will be more accurate
				switch (resultType) {
				case DAY: newDate = DateUtils.truncate(newDate, Calendar.DATE); break;
				case HOUR: newDate = DateUtils.truncate(newDate, Calendar.HOUR); break;
				}

               int diff = 0;
               if (prevDate != null) {
            	   switch (resultType) {
					case DAY: prevDate = DateUtils.truncate(prevDate, Calendar.DATE); break;
					case HOUR: prevDate = DateUtils.truncate(prevDate, Calendar.HOUR); break;
					}
                  diff = (int)((newDate.getTime() - prevDate.getTime()) / (ResultType.DAY.compareTo(resultType)==0 ? DAY_AS_LONG : HOUR_AS_LONG));
                  if(isDebug){
                     log.info("DEBUG: Image captured " + String.valueOf(diff) + (ResultType.DAY.compareTo(resultType)==0 ? " days" : " hours") + " before " + SDF_RESULTS.format(newDate) + " (ON " + SDF_RESULTS.format(prevDate) + ")");
                  }
//                  System.out.println(diff);
               }
               Integer val = map.get(diff) != null ? map.get(diff) : 0;
               if(diff > 0)
                  map.put(diff, ++val);
               
               if (val > maxFreq) {
                  maxFreqElement = diff;
                  maxFreq = val;
               }               
               prevDate = newDate;
               
            }
            JSONObject last = (JSONObject)results.get(results.size()-1);
            last_date = sdfResults.parse(last.get(DATE_TAG).toString());
            
            log.info("maxFreqElement mode : " + maxFreqElement + (ResultType.DAY.compareTo(resultType)==0 ? " days" : " hours"));
            log.info("freq count is " + map.get(maxFreqElement));
            if(isDebug){
               log.info("DEBUG: last time of imaging " + sdfResults.format(last_date));
            }
         }
      } catch (ParseException | java.text.ParseException e) {
         log.severe("ERROR while parsing JSON.");
      }
      Calendar c = Calendar.getInstance();
      c.setTime(last_date);
      c.add(ResultType.DAY.compareTo(resultType)==0 ? Calendar.DAY_OF_MONTH : Calendar.HOUR_OF_DAY, maxFreqElement);
      System.out.println("Next time: " + SDF_RESULTS.format(c.getTime()));
   }
   
   //can add URLEncoder.encode(sdf.format(beginDate), CHARSET) to encode properly
   //this will create a formated URL to fetch data from
   private static URL constructURL(double latitude, double longitude, Date beginDate, Date endDate){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      if(beginDate == null) {
         log.warning("Begin date must be specified before calling APIs.");
         return null;
      }
      
      //java.net.URL we have to add query params in string. No APIs
      StringBuilder sb = new StringBuilder();
      sb.append(NASA_API_URL)
         .append("?")
         .append(LONGITUDE_TAG).append("=").append(longitude)
         .append("&").append(LATITUDE_TAG).append("=").append(latitude)
         .append("&").append("begin").append("=").append(sdf.format(beginDate));
      
      if(endDate != null) sb.append("&").append("end").append("=").append(sdf.format(endDate));
      sb.append("&").append(API_KEY_TAG).append("=").append(API_KEY);
      
      String url = sb.toString();
      if(isDebug){
         log.log(Level.INFO, "DEBUG: URL-> " + url);
      }
      try {
         return new URL(url);
      } catch (MalformedURLException e) {
         log.log(Level.SEVERE, "ERROR: url can not be constructed.", e);
         return null;
      }
   }
   
   //enum for Timeframe, how we want to compare the time.(per day or per hour) 
   enum ResultType {
      DAY, HOUR
   }
   
   
   // unit test for constructing api URL
	@Test @Ignore
	public void testURLConstruct() {
		org.junit.Assert.assertNull(constructURL(0, 0, null, null));
		org.junit.Assert.assertNotNull(constructURL(1.5, 100.75, new Date(114, 1, 2), null));
		org.junit.Assert.assertNull(constructURL(1.5, 100.75, null, null));
	}
	
	//unit test for fetching JSON
	@Test @Ignore
	public void testFetchJSON() {
		org.junit.Assert.assertNotNull(fetchJSON(constructURL(1.5, 100.75, new Date(114,1,2), null)));
		org.junit.Assert.assertNull(fetchJSON(constructURL(1.5, 100.75, null, null)));
	}
	
	//Unit test for parsing JSON
	@Test @Ignore
	public void testParseJSON() {
	      String json = "{\"count\": 65, \"results\": [{\"date\": \"2014-02-04T03:30:01\", \"id\": \"LC8_L1T_TOA/LC81270592014035LGN00\"}"
	          + ", {\"date\": \"2014-02-20T03:29:47\", \"id\": \"LC8_L1T_TOA/LC81270592014051LGN00\"}"
	          + ", {\"date\": \"2014-03-08T03:29:33\", \"id\": \"LC8_L1T_TOA/LC81270592014067LGN00\"}"
	          + ", {\"date\": \"2014-03-24T03:29:20\", \"id\": \"LC8_L1T_TOA/LC81270592014083LGN00\"}"
	          + ", {\"date\": \"2014-04-09T03:29:06\", \"id\": \"LC8_L1T_TOA/LC81270592014099LGN00\"}"
	          + ", {\"date\": \"2014-04-25T03:28:50\", \"id\": \"LC8_L1T_TOA/LC81270592014115LGN00\"}, {\"date\": \"2014-05-11T03:28:35\", \"id\": \"LC8_L1T_TOA/LC81270592014131LGN00\"}, {\"date\": \"2014-05-27T03:28:32\", \"id\": \"LC8_L1T_TOA/LC81270592014147LGN00\"}, {\"date\": \"2014-06-12T03:28:41\", \"id\": \"LC8_L1T_TOA/LC81270592014163LGN00\"}, {\"date\": \"2014-06-28T03:28:43\", \"id\": \"LC8_L1T_TOA/LC81270592014179LGN00\"}, {\"date\": \"2014-07-14T03:28:51\", \"id\": \"LC8_L1T_TOA/LC81270592014195LGN01\"}, {\"date\": \"2014-07-30T03:28:56\", \"id\": \"LC8_L1T_TOA/LC81270592014211LGN00\"}, {\"date\": \"2014-08-15T03:29:03\", \"id\": \"LC8_L1T_TOA/LC81270592014227LGN00\"}, {\"date\": \"2014-08-31T03:29:05\", \"id\": \"LC8_L1T_TOA/LC81270592014243LGN00\"}, {\"date\": \"2014-09-16T03:29:07\", \"id\": \"LC8_L1T_TOA/LC81270592014259LGN00\"}, {\"date\": \"2014-10-02T03:29:09\", \"id\": \"LC8_L1T_TOA/LC81270592014275LGN00\"}, {\"date\": \"2014-10-18T03:29:13\", \"id\": \"LC8_L1T_TOA/LC81270592014291LGN00\"}, {\"date\": \"2014-11-03T03:29:11\", \"id\": \"LC8_L1T_TOA/LC81270592014307LGN00\"}, {\"date\": \"2014-11-19T03:29:10\", \"id\": \"LC8_L1T_TOA/LC81270592014323LGN00\"}, {\"date\": \"2014-12-05T03:29:09\", \"id\": \"LC8_L1T_TOA/LC81270592014339LGN00\"}, {\"date\": \"2014-12-21T03:29:07\", \"id\": \"LC8_L1T_TOA/LC81270592014355LGN00\"}, {\"date\": \"2015-01-06T03:29:02\", \"id\": \"LC8_L1T_TOA/LC81270592015006LGN00\"}, {\"date\": \"2015-02-07T03:28:54\", \"id\": \"LC8_L1T_TOA/LC81270592015038LGN00\"}, {\"date\": \"2015-02-23T03:28:48\", \"id\": \"LC8_L1T_TOA/LC81270592015054LGN00\"}, {\"date\": \"2015-03-11T03:28:37\", \"id\": \"LC8_L1T_TOA/LC81270592015070LGN00\"}, {\"date\": \"2015-03-27T03:28:29\", \"id\": \"LC8_L1T_TOA/LC81270592015086LGN00\"}, {\"date\": \"2015-04-12T03:28:22\", \"id\": \"LC8_L1T_TOA/LC81270592015102LGN00\"}, {\"date\": \"2015-04-28T03:28:16\", \"id\": \"LC8_L1T_TOA/LC81270592015118LGN00\"}, {\"date\": \"2015-05-14T03:28:00\", \"id\": \"LC8_L1T_TOA/LC81270592015134LGN00\"}, {\"date\": \"2015-05-30T03:28:04\", \"id\": \"LC8_L1T_TOA/LC81270592015150LGN00\"}, {\"date\": \"2015-06-15T03:28:16\", \"id\": \"LC8_L1T_TOA/LC81270592015166LGN00\"}, {\"date\": \"2015-07-01T03:28:23\", \"id\": \"LC8_L1T_TOA/LC81270592015182LGN00\"}, {\"date\": \"2015-07-17T03:28:33\", \"id\": \"LC8_L1T_TOA/LC81270592015198LGN00\"}, {\"date\": \"2015-08-02T03:28:36\", \"id\": \"LC8_L1T_TOA/LC81270592015214LGN00\"}, {\"date\": \"2015-08-18T03:28:43\", \"id\": \"LC8_L1T_TOA/LC81270592015230LGN00\"}, {\"date\": \"2015-09-03T03:28:48\", \"id\": \"LC8_L1T_TOA/LC81270592015246LGN00\"}, {\"date\": \"2015-10-05T03:29:00\", \"id\": \"LC8_L1T_TOA/LC81270592015278LGN00\"}, {\"date\": \"2015-10-21T03:29:03\", \"id\": \"LC8_L1T_TOA/LC81270592015294LGN00\"}, {\"date\": \"2015-11-22T03:29:08\", \"id\": \"LC8_L1T_TOA/LC81270592015326LGN00\"}, {\"date\": \"2015-12-08T03:29:07\", \"id\": \"LC8_L1T_TOA/LC81270592015342LGN00\"}, {\"date\": \"2015-12-24T03:29:08\", \"id\": \"LC8_L1T_TOA/LC81270592015358LGN00\"}, {\"date\": \"2016-01-09T03:29:03\", \"id\": \"LC8_L1T_TOA/LC81270592016009LGN00\"}, {\"date\": \"2016-01-25T03:29:04\", \"id\": \"LC8_L1T_TOA/LC81270592016025LGN00\"}, {\"date\": \"2016-02-10T03:28:59\", \"id\": \"LC8_L1T_TOA/LC81270592016041LGN00\"}, {\"date\": \"2016-02-26T03:28:53\", \"id\": \"LC8_L1T_TOA/LC81270592016057LGN00\"}, {\"date\": \"2016-03-13T03:28:50\", \"id\": \"LC8_L1T_TOA/LC81270592016073LGN00\"}, {\"date\": \"2016-03-29T03:28:40\", \"id\": \"LC8_L1T_TOA/LC81270592016089LGN00\"}, {\"date\": \"2016-04-14T03:28:35\", \"id\": \"LC8_L1T_TOA/LC81270592016105LGN00\"}, {\"date\": \"2016-04-30T03:28:35\", \"id\": \"LC8_L1T_TOA/LC81270592016121LGN00\"}, {\"date\": \"2016-06-01T03:28:39\", \"id\": \"LC8_L1T_TOA/LC81270592016153LGN00\"}, {\"date\": \"2016-07-03T03:28:51\", \"id\": \"LC8_L1T_TOA/LC81270592016185LGN00\"}, {\"date\": \"2016-07-19T03:28:58\", \"id\": \"LC8_L1T_TOA/LC81270592016201LGN00\"}, {\"date\": \"2016-08-04T03:29:01\", \"id\": \"LC8_L1T_TOA/LC81270592016217LGN00\"}, {\"date\": \"2016-08-20T03:29:06\", \"id\": \"LC8_L1T_TOA/LC81270592016233LGN00\"}, {\"date\": \"2016-09-05T03:29:12\", \"id\": \"LC8_L1T_TOA/LC81270592016249LGN00\"}, {\"date\": \"2016-09-21T03:29:13\", \"id\": \"LC8_L1T_TOA/LC81270592016265LGN00\"}, {\"date\": \"2016-10-23T03:29:20\", \"id\": \"LC8_L1T_TOA/LC81270592016297LGN00\"}, {\"date\": \"2016-11-08T03:29:19\", \"id\": \"LC8_L1T_TOA/LC81270592016313LGN00\"}, {\"date\": \"2016-11-24T03:29:20\", \"id\": \"LC8_L1T_TOA/LC81270592016329LGN00\"}, {\"date\": \"2016-12-10T03:29:17\", \"id\": \"LC8_L1T_TOA/LC81270592016345LGN00\"}, {\"date\": \"2017-01-11T03:29:10\", \"id\": \"LC8_L1T_TOA/LC81270592017011LGN00\"}, {\"date\": \"2017-01-27T03:29:04\", \"id\": \"LC8_L1T_TOA/LC81270592017027LGN00\"}"
	          + ", {\"date\": \"2017-02-12T03:28:56\", \"id\": \"LC8_L1T_TOA/LC81270592017043LGN00\"}, {\"date\": \"2017-02-28T03:28:51\", \"id\": \"LC8_L1T_TOA/LC81270592017059LGN00\"}, {\"date\": \"2017-03-16T03:28:42\", \"id\": \"LC8_L1T_TOA/LC81270592017075LGN00\"}"

	          + "]}";
	      parseJSON(SDF_RESULTS, json);	    
	}
	
	@Test
	public void testFlyby() {
//		flyby(1.5, 100.75);
		flyby(1.5, 100.75, new Date(114, 1, 2), null);
	}
	
	@Test @Ignore
	public void testDateDiff(){
		Date newDate;
	   SimpleDateFormat daysFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = SDF_RESULTS.parse("2014-02-20T03:29:47");
			Date prevDate = SDF_RESULTS.parse("2014-02-19T03:30:01");
			newDate = DateUtils.truncate(newDate, Calendar.DATE);
			prevDate = DateUtils.truncate(prevDate, Calendar.DATE);
			
			int diff = (int) ((newDate.getTime() - prevDate.getTime())
					/ (ResultType.DAY.compareTo(resultType) == 0 ? DAY_AS_LONG : HOUR_AS_LONG));
			System.out.println(diff);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
