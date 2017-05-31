import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

//This solution uses the APIs available at coderpad. So this will use the coderpad constraints.
//I have used "if not proper then return" pattern. So for any error conditions return and no need to execute any more
public class Solution {
	//constants for NASA apis
	public static final String NASA_API_URL = "https://api.nasa.gov/planetary/earth/assets";
	public static final String DATE_TAG = "date";
	public static final String RESULTS_TAG = "results";
	public static final String COUNT_TAG = "count";
	public static final String LONGITUDE_TAG = "lon";
	public static final String LATITUDE_TAG = "lat";
	public static final String API_KEY_TAG = "api_key";
	public static final String API_KEY = "9Jz6tLIeJ0yY9vjbEUWaH9fsXA930J9hspPchute";
	public static final SimpleDateFormat API_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //date format as in api results
	
	//constant for the time duration.
	//we can get the time delta in two format Day or Hours.
	//Setting default delta format as number of Days
	private static final ResultType RESULT_TYPE = ResultType.DAY;
	private static final long MILLIS_IN_DAY = (24 * 60 * 60 * 1000);
	private static final long MILLIS_IN_HOUR = (60 * 60 * 1000);
	
	//Minimum number of sample set required to calculate effective
	private static final int MIN_RESULT_CNT = 2;                //minimum required number of elements in sample set
   
	//logging purpose. Can use Log4j for better logging
	private static Logger log = Logger.getLogger("Sample");
	//we can set this true to print few debug statements
	private static boolean isDebug = false;
   	
	//as per NASA API doc, begin date is necessary.
	//So created overloaded version of this API and calling it by providing default begin date
	//Please check the overloaded version of API.
	public static void flyby(double latitude, double longitude){
		//calling by fixed start date and no end Date.
		Date beginDate;
		try {
			//for testing passing date 2014-02-02
			beginDate = API_DATE_FORMAT.parse("2014-02-02T00:00:00");
			flyby(latitude, longitude, beginDate, null);
		} catch (java.text.ParseException e) {
			log.severe("Error while parsing date.");
		}    
	}
	
	//this takes additional params beginDate and endDate
	//this is necessary as the NASA API requires begin date. end date is optional in NASA API
	public static void flyby(double latitude, double longitude, Date beginDate, Date endDate){
		String json = null;
		//construct the API URL based on dates provided
		URL url = constructURL(latitude, longitude, beginDate, endDate);
		if(url == null){		//url might be malformed
			log.severe("No url specified for the API.");
			return;
		}
		//fetch the JSON results from API call
		json = fetchJSON(url);
		if(StringUtils.isBlank(json)){
			log.warning("Could not fetch the JSON results using API.");
			return;
		}
		//if we get JSON response then parse to find the next date. 
		parseJSON(API_DATE_FORMAT, json);
	}
	
	//Fetch JSON data from given URL using GET method
	private static String fetchJSON(URL url) {
		if (url == null) {
			log.warning("Non null API url should be provided");
			return null;
		}

		StringBuilder sb = new StringBuilder();
		try {
			// using java.net package due coderpad constraints
			// For better parsing and calling web servises we can use special
			// APIs like Jersey.
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				log.severe("Failed: HTTP error code : " + conn.getResponseCode() + " with Message : " + conn.getResponseMessage());
				return null;
			}

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
	
	// Parse JSON into useful data
	private static void parseJSON(final SimpleDateFormat sdfResults, String json) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		int maxFreqElement = 0;
		int maxFreq = 0;
		Date last_date = null; // last date when image was captured

		try {
			jsonObject = (JSONObject) parser.parse(json);
		} catch (ParseException e) {
			log.severe("ERROR while parsing JSON.");
			return;
		}
		JSONArray results = (JSONArray) jsonObject.get(RESULTS_TAG);	//get the results into array

		if (results != null && !results.isEmpty()) {
			if (results.size() < MIN_RESULT_CNT) {
				log.warning("Result set doesn't have minimum number of result. Size:" + results.size());
				return;
			}
			
			//Is writing sort algorithm expected?
			//we implement quick sort for this.
			
			//we may need to sort the results as some dates might not be in order
			List<Object> arrList = Arrays.asList(results.toArray());
			//comparator for the sorting
			Comparator<Object> comp = new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					try {
						return DateUtils.truncatedCompareTo(sdfResults.parse(((JSONObject)o1).get(DATE_TAG).toString()), sdfResults.parse(((JSONObject)o2).get(DATE_TAG).toString()), Calendar.DATE);
					} catch (java.text.ParseException e) {
						//for error we need to consider both as equal
						return 0;
					}
				}				
			};
			Collections.sort(arrList, comp);

			Date prevDate = null;
			// hashmap of date difference as key and frequency as value
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

			Iterator<Object> iterator = arrList.iterator();
			while (iterator.hasNext()) {
				try{
					JSONObject result = (JSONObject) iterator.next();
					Date newDate = sdfResults.parse(result.get(DATE_TAG).toString());
					// truncate Time from date so that we can get the difference
					// in term of days or hours.
					// this way the diff between two dates will be more accurate
					int diff = 0;
					if (prevDate != null) {
						long longDiff = 0;
						switch (RESULT_TYPE) {
						case DAY:
							longDiff = DateUtils.truncate(newDate, Calendar.DATE).getTime() - DateUtils.truncate(prevDate, Calendar.DATE).getTime();
							diff = (int) (longDiff / MILLIS_IN_DAY);
							break;
						case HOUR:
							longDiff = DateUtils.truncate(newDate, Calendar.HOUR).getTime() - DateUtils.truncate(prevDate, Calendar.HOUR).getTime();
							diff = (int) (longDiff / MILLIS_IN_HOUR);
							break;
						}

						if (isDebug) {
							log.info("DEBUG: Image captured " + String.valueOf(diff)
									+ (ResultType.DAY.compareTo(RESULT_TYPE) == 0 ? " days" : " hours") + " before "
									+ API_DATE_FORMAT.format(newDate) + " (ON " + API_DATE_FORMAT.format(prevDate)
									+ ")");
						}
					}
					//if the difference is already in the map then get the frequency value. 
					Integer val = map.get(diff) != null ? map.get(diff) : 0;
					//for first time difference will be 0. So we don't need to add 0 diff as key
					//for any other diff values ad to hashmap and increment frequency count
					if (diff > 0)
						map.put(diff, ++val);

					//if the new frequency value is greater than previously calculated maxFrequency
					//then mark new frequency value as maxFrequency
					if (val > maxFreq) {
						maxFreqElement = diff;
						maxFreq = val;
					}
					prevDate = newDate;

				} catch (java.text.ParseException e){
					//one of the dates might get parse exception if not proper.
					//then skipping this record and continuing with next
					log.severe("ERROR while parsing the Date from Json. Continue with next result");
					continue;
				}
			}
			JSONObject last = (JSONObject) arrList.get(arrList.size() - 1);
			try {
				last_date = sdfResults.parse(last.get(DATE_TAG).toString());
			} catch (java.text.ParseException e) {
				log.severe("ERROR while parsing the Last Date from Json.");
				return;
			}

			if (isDebug) {
				log.info("maxFreqElement mode : " + maxFreqElement
						+ (ResultType.DAY.compareTo(RESULT_TYPE) == 0 ? " days" : " hours"));
				log.info("freq count is " + map.get(maxFreqElement));
				log.info("DEBUG: last time of imaging " + sdfResults.format(last_date));
			}
		}
			
		Calendar c = Calendar.getInstance();
		c.setTime(last_date);
		c.add(ResultType.DAY.compareTo(RESULT_TYPE) == 0 ? Calendar.DAY_OF_MONTH : Calendar.HOUR_OF_DAY, maxFreqElement);
		System.out.println("Next time: " + API_DATE_FORMAT.format(c.getTime()));
		//we might next time in past.
		System.out.println(c.getTime().getTime() < System.currentTimeMillis() ? "Next Time is in Past so the image will be taken any time soon." : "Image will be taken soon.");
	}

	// can add URLEncoder.encode(sdf.format(beginDate), CHARSET) to encode
	// properly
	// this will create a formated URL to fetch data from
	private static URL constructURL(double latitude, double longitude, Date beginDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (beginDate == null) {
			log.warning("Begin date must be specified before calling APIs.");
			return null;
		}

		// java.net.URL we have to add query params in string. No APIs
		StringBuilder sb = new StringBuilder();
		sb.append(NASA_API_URL).append("?")
		.append(LONGITUDE_TAG).append("=").append(longitude)
		.append("&").append(LATITUDE_TAG).append("=").append(latitude)
		.append("&").append("begin").append("=").append(sdf.format(beginDate));

		if (endDate != null)
			sb.append("&").append("end").append("=").append(sdf.format(endDate));
		sb.append("&").append(API_KEY_TAG).append("=").append(API_KEY);

		String url = sb.toString();
		if (isDebug) {
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
   
   //******* main method
	public static void main(String[] args) {
		try {
			// manually setting the log level as all logs
			log.setLevel(Level.ALL);
			// manually setting the log output format
			System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s %5$s%6$s%n");
			
			//call the original api call. But this has missing begin date, so it calls overloaded api with hard coded date
			flyby(1.5, 100.75);			
			
			//Grand Canyon           36.098592   -112.097796
			flyby(36.098592, -112.097796, API_DATE_FORMAT.parse("2014-02-02T00:00:00"), null);
			
			//Niagra Falls           43.078154   -79.075891
			flyby(43.078154, -79.075891, API_DATE_FORMAT.parse("2014-02-02T00:00:00"), null);
		} catch (java.text.ParseException e) {
			log.severe("ERROR while parsing Date format.");
		}
		
	}

	//***************************************************** JUNIT tests follows
   // unit test for constructing api URL
	@Test
	public void testURLConstruct() throws java.text.ParseException {
		org.junit.Assert.assertNull(constructURL(0, 0, null, null));
		org.junit.Assert.assertNotNull(constructURL(1.5, 100.75, API_DATE_FORMAT.parse("2014-02-02T00:00:00"), null));
		org.junit.Assert.assertNull(constructURL(1.5, 100.75, null, null));
	}
	
	//unit test for fetching JSON
	@Test
	public void testFetchJSON() throws java.text.ParseException {
		org.junit.Assert.assertNotNull("Error fetching JSON. A non null JSON string expected.", fetchJSON(constructURL(1.5, 100.75, API_DATE_FORMAT.parse("2014-02-02T00:00:00"), null)));
		org.junit.Assert.assertNull(fetchJSON(constructURL(1.5, 100.75, null, null)));
	}
	
	//Unit test for parsing JSON
	@Test
	public void testParseJSON() {
	      String json = "{\"count\": 65, \"results\": [{\"date\": \"2014-02-04T03:30:01\", \"id\": \"LC8_L1T_TOA/LC81270592014035LGN00\"}"
	          + ", {\"date\": \"2014-02-20T03:29:47\", \"id\": \"LC8_L1T_TOA/LC81270592014051LGN00\"}"
	          + ", {\"date\": \"2014-03-08T03:29:33\", \"id\": \"LC8_L1T_TOA/LC81270592014067LGN00\"}"
	          + ", {\"date\": \"2014-03-24T03:29:20\", \"id\": \"LC8_L1T_TOA/LC81270592014083LGN00\"}"
	          + ", {\"date\": \"2014-04-09T03:29:06\", \"id\": \"LC8_L1T_TOA/LC81270592014099LGN00\"}"
	          + ", {\"date\": \"2014-04-25T03:28:50\", \"id\": \"LC8_L1T_TOA/LC81270592014115LGN00\"}, {\"date\": \"2014-05-11T03:28:35\", \"id\": \"LC8_L1T_TOA/LC81270592014131LGN00\"}, {\"date\": \"2014-05-27T03:28:32\", \"id\": \"LC8_L1T_TOA/LC81270592014147LGN00\"}, {\"date\": \"2014-06-12T03:28:41\", \"id\": \"LC8_L1T_TOA/LC81270592014163LGN00\"}, {\"date\": \"2014-06-28T03:28:43\", \"id\": \"LC8_L1T_TOA/LC81270592014179LGN00\"}, {\"date\": \"2014-07-14T03:28:51\", \"id\": \"LC8_L1T_TOA/LC81270592014195LGN01\"}, {\"date\": \"2014-07-30T03:28:56\", \"id\": \"LC8_L1T_TOA/LC81270592014211LGN00\"}, {\"date\": \"2014-08-15T03:29:03\", \"id\": \"LC8_L1T_TOA/LC81270592014227LGN00\"}, {\"date\": \"2014-08-31T03:29:05\", \"id\": \"LC8_L1T_TOA/LC81270592014243LGN00\"}, {\"date\": \"2014-09-16T03:29:07\", \"id\": \"LC8_L1T_TOA/LC81270592014259LGN00\"}, {\"date\": \"2014-10-02T03:29:09\", \"id\": \"LC8_L1T_TOA/LC81270592014275LGN00\"}, {\"date\": \"2014-10-18T03:29:13\", \"id\": \"LC8_L1T_TOA/LC81270592014291LGN00\"}, {\"date\": \"2014-11-03T03:29:11\", \"id\": \"LC8_L1T_TOA/LC81270592014307LGN00\"}, {\"date\": \"2014-11-19T03:29:10\", \"id\": \"LC8_L1T_TOA/LC81270592014323LGN00\"}, {\"date\": \"2014-12-05T03:29:09\", \"id\": \"LC8_L1T_TOA/LC81270592014339LGN00\"}, {\"date\": \"2014-12-21T03:29:07\", \"id\": \"LC8_L1T_TOA/LC81270592014355LGN00\"}, {\"date\": \"2015-01-06T03:29:02\", \"id\": \"LC8_L1T_TOA/LC81270592015006LGN00\"}, {\"date\": \"2015-02-07T03:28:54\", \"id\": \"LC8_L1T_TOA/LC81270592015038LGN00\"}, {\"date\": \"2015-02-23T03:28:48\", \"id\": \"LC8_L1T_TOA/LC81270592015054LGN00\"}, {\"date\": \"2015-03-11T03:28:37\", \"id\": \"LC8_L1T_TOA/LC81270592015070LGN00\"}, {\"date\": \"2015-03-27T03:28:29\", \"id\": \"LC8_L1T_TOA/LC81270592015086LGN00\"}, {\"date\": \"2015-04-12T03:28:22\", \"id\": \"LC8_L1T_TOA/LC81270592015102LGN00\"}, {\"date\": \"2015-04-28T03:28:16\", \"id\": \"LC8_L1T_TOA/LC81270592015118LGN00\"}, {\"date\": \"2015-05-14T03:28:00\", \"id\": \"LC8_L1T_TOA/LC81270592015134LGN00\"}, {\"date\": \"2015-05-30T03:28:04\", \"id\": \"LC8_L1T_TOA/LC81270592015150LGN00\"}, {\"date\": \"2015-06-15T03:28:16\", \"id\": \"LC8_L1T_TOA/LC81270592015166LGN00\"}, {\"date\": \"2015-07-01T03:28:23\", \"id\": \"LC8_L1T_TOA/LC81270592015182LGN00\"}, {\"date\": \"2015-07-17T03:28:33\", \"id\": \"LC8_L1T_TOA/LC81270592015198LGN00\"}, {\"date\": \"2015-08-02T03:28:36\", \"id\": \"LC8_L1T_TOA/LC81270592015214LGN00\"}, {\"date\": \"2015-08-18T03:28:43\", \"id\": \"LC8_L1T_TOA/LC81270592015230LGN00\"}, {\"date\": \"2015-09-03T03:28:48\", \"id\": \"LC8_L1T_TOA/LC81270592015246LGN00\"}, {\"date\": \"2015-10-05T03:29:00\", \"id\": \"LC8_L1T_TOA/LC81270592015278LGN00\"}, {\"date\": \"2015-10-21T03:29:03\", \"id\": \"LC8_L1T_TOA/LC81270592015294LGN00\"}, {\"date\": \"2015-11-22T03:29:08\", \"id\": \"LC8_L1T_TOA/LC81270592015326LGN00\"}, {\"date\": \"2015-12-08T03:29:07\", \"id\": \"LC8_L1T_TOA/LC81270592015342LGN00\"}, {\"date\": \"2015-12-24T03:29:08\", \"id\": \"LC8_L1T_TOA/LC81270592015358LGN00\"}, {\"date\": \"2016-01-09T03:29:03\", \"id\": \"LC8_L1T_TOA/LC81270592016009LGN00\"}, {\"date\": \"2016-01-25T03:29:04\", \"id\": \"LC8_L1T_TOA/LC81270592016025LGN00\"}, {\"date\": \"2016-02-10T03:28:59\", \"id\": \"LC8_L1T_TOA/LC81270592016041LGN00\"}, {\"date\": \"2016-02-26T03:28:53\", \"id\": \"LC8_L1T_TOA/LC81270592016057LGN00\"}, {\"date\": \"2016-03-13T03:28:50\", \"id\": \"LC8_L1T_TOA/LC81270592016073LGN00\"}, {\"date\": \"2016-03-29T03:28:40\", \"id\": \"LC8_L1T_TOA/LC81270592016089LGN00\"}, {\"date\": \"2016-04-14T03:28:35\", \"id\": \"LC8_L1T_TOA/LC81270592016105LGN00\"}, {\"date\": \"2016-04-30T03:28:35\", \"id\": \"LC8_L1T_TOA/LC81270592016121LGN00\"}, {\"date\": \"2016-06-01T03:28:39\", \"id\": \"LC8_L1T_TOA/LC81270592016153LGN00\"}, {\"date\": \"2016-07-03T03:28:51\", \"id\": \"LC8_L1T_TOA/LC81270592016185LGN00\"}, {\"date\": \"2016-07-19T03:28:58\", \"id\": \"LC8_L1T_TOA/LC81270592016201LGN00\"}, {\"date\": \"2016-08-04T03:29:01\", \"id\": \"LC8_L1T_TOA/LC81270592016217LGN00\"}, {\"date\": \"2016-08-20T03:29:06\", \"id\": \"LC8_L1T_TOA/LC81270592016233LGN00\"}, {\"date\": \"2016-09-05T03:29:12\", \"id\": \"LC8_L1T_TOA/LC81270592016249LGN00\"}, {\"date\": \"2016-09-21T03:29:13\", \"id\": \"LC8_L1T_TOA/LC81270592016265LGN00\"}, {\"date\": \"2016-10-23T03:29:20\", \"id\": \"LC8_L1T_TOA/LC81270592016297LGN00\"}, {\"date\": \"2016-11-08T03:29:19\", \"id\": \"LC8_L1T_TOA/LC81270592016313LGN00\"}, {\"date\": \"2016-11-24T03:29:20\", \"id\": \"LC8_L1T_TOA/LC81270592016329LGN00\"}, {\"date\": \"2016-12-10T03:29:17\", \"id\": \"LC8_L1T_TOA/LC81270592016345LGN00\"}, {\"date\": \"2017-01-11T03:29:10\", \"id\": \"LC8_L1T_TOA/LC81270592017011LGN00\"}, {\"date\": \"2017-01-27T03:29:04\", \"id\": \"LC8_L1T_TOA/LC81270592017027LGN00\"}"
	          + ", {\"date\": \"2017-02-12T03:28:56\", \"id\": \"LC8_L1T_TOA/LC81270592017043LGN00\"}, {\"date\": \"2017-02-28T03:28:51\", \"id\": \"LC8_L1T_TOA/LC81270592017059LGN00\"}, {\"date\": \"2017-03-16T03:28:42\", \"id\": \"LC8_L1T_TOA/LC81270592017075LGN00\"}"

	          + "]}";
	      parseJSON(API_DATE_FORMAT, json);	 
	      
	      //this JSON has unsorted dates
	      json = "{\"count\": 72, \"results\": [{\"date\": \"2014-02-13T18:04:04\", \"id\": \"LC8_L1T_TOA/LC80370352014044LGN00\"}, {\"date\": \"2014-03-01T18:03:50\", \"id\": \"LC8_L1T_TOA/LC80370352014060LGN00\"}, {\"date\": \"2014-03-17T18:03:41\", \"id\": \"LC8_L1T_TOA/LC80370352014076LGN00\"}, {\"date\": \"2014-04-02T18:03:25\", \"id\": \"LC8_L1T_TOA/LC80370352014092LGN00\"}, {\"date\": \"2014-04-18T18:03:07\", \"id\": \"LC8_L1T_TOA/LC80370352014108LGN00\"}, {\"date\": \"2014-05-04T18:02:52\", \"id\": \"LC8_L1T_TOA/LC80370352014124LGN00\"}, {\"date\": \"2014-05-20T18:02:43\", \"id\": \"LC8_L1T_TOA/LC80370352014140LGN00\"}, {\"date\": \"2014-06-05T18:02:51\", \"id\": \"LC8_L1T_TOA/LC80370352014156LGN01\"}, {\"date\": \"2014-06-21T18:02:55\", \"id\": \"LC8_L1T_TOA/LC80370352014172LGN00\"}, {\"date\": \"2014-07-07T18:03:03\", \"id\": \"LC8_L1T_TOA/LC80370352014188LGN00\"}, {\"date\": \"2014-07-23T18:03:05\", \"id\": \"LC8_L1T_TOA/LC80370352014204LGN00\"}, {\"date\": \"2014-08-08T18:03:14\", \"id\": \"LC8_L1T_TOA/LC80370352014220LGN00\"}, {\"date\": \"2014-08-24T18:03:17\", \"id\": \"LC8_L1T_TOA/LC80370352014236LGN00\"}, {\"date\": \"2014-09-09T18:03:21\", \"id\": \"LC8_L1T_TOA/LC80370352014252LGN00\"}, {\"date\": \"2014-09-25T18:03:18\", \"id\": \"LC8_L1T_TOA/LC80370352014268LGN00\"}, {\"date\": \"2014-10-11T18:03:25\", \"id\": \"LC8_L1T_TOA/LC80370352014284LGN00\"}, {\"date\": \"2014-10-27T18:03:23\", \"id\": \"LC8_L1T_TOA/LC80370352014300LGN00\"}, {\"date\": \"2014-11-12T18:03:26\", \"id\": \"LC8_L1T_TOA/LC80370352014316LGN00\"}, {\"date\": \"2014-11-28T18:03:24\", \"id\": \"LC8_L1T_TOA/LC80370352014332LGN00\"}, {\"date\": \"2014-12-14T18:03:18\", \"id\": \"LC8_L1T_TOA/LC80370352014348LGN00\"}, {\"date\": \"2014-12-30T18:03:14\", \"id\": \"LC8_L1T_TOA/LC80370352014364LGN00\"}, {\"date\": \"2015-01-15T18:03:14\", \"id\": \"LC8_L1T_TOA/LC80370352015015LGN00\"}, {\"date\": \"2015-01-31T18:03:10\", \"id\": \"LC8_L1T_TOA/LC80370352015031LGN00\"}, {\"date\": \"2015-02-16T18:03:01\", \"id\": \"LC8_L1T_TOA/LC80370352015047LGN00\"}, {\"date\": \"2015-03-04T18:02:56\", \"id\": \"LC8_L1T_TOA/LC80370352015063LGN00\"}, {\"date\": \"2015-03-20T18:02:46\", \"id\": \"LC8_L1T_TOA/LC80370352015079LGN00\"}, {\"date\": \"2015-04-05T18:02:35\", \"id\": \"LC8_L1T_TOA/LC80370352015095LGN00\"}, {\"date\": \"2015-04-21T18:02:34\", \"id\": \"LC8_L1T_TOA/LC80370352015111LGN00\"}, {\"date\": \"2015-05-07T18:02:18\", \"id\": \"LC8_L1T_TOA/LC80370352015127LGN00\"}, {\"date\": \"2015-05-23T18:02:16\", \"id\": \"LC8_L1T_TOA/LC80370352015143LGN00\"}, {\"date\": \"2015-06-08T18:02:26\", \"id\": \"LC8_L1T_TOA/LC80370352015159LGN00\"}, {\"date\": \"2015-06-24T18:02:32\", \"id\": \"LC8_L1T_TOA/LC80370352015175LGN00\"}, {\"date\": \"2015-07-10T18:02:43\", \"id\": \"LC8_L1T_TOA/LC80370352015191LGN00\"}, {\"date\": \"2015-07-26T18:02:49\", \"id\": \"LC8_L1T_TOA/LC80370352015207LGN00\"}, {\"date\": \"2015-08-11T18:02:53\", \"id\": \"LC8_L1T_TOA/LC80370352015223LGN00\"}, {\"date\": \"2015-08-27T18:03:00\", \"id\": \"LC8_L1T_TOA/LC80370352015239LGN00\"}, {\"date\": \"2015-09-12T18:03:07\", \"id\": \"LC8_L1T_TOA/LC80370352015255LGN00\"}, {\"date\": \"2015-09-28T18:03:13\", \"id\": \"LC8_L1T_TOA/LC80370352015271LGN00\"}, {\"date\": \"2015-10-14T18:03:13\", \"id\": \"LC8_L1T_TOA/LC80370352015287LGN00\"}, {\"date\": \"2015-10-30T18:03:19\", \"id\": \"LC8_L1T_TOA/LC80370352015303LGN00\"}, {\"date\": \"2015-11-15T18:03:19\", \"id\": \"LC8_L1T_TOA/LC80370352015319LGN00\"}, {\"date\": \"2015-12-01T18:03:21\", \"id\": \"LC8_L1T_TOA/LC80370352015335LGN00\"}, {\"date\": \"2015-12-17T18:03:21\", \"id\": \"LC8_L1T_TOA/LC80370352015351LGN00\"}, {\"date\": \"2016-01-02T18:03:18\", \"id\": \"LC8_L1T_TOA/LC80370352016002LGN00\"}, {\"date\": \"2016-01-18T18:03:18\", \"id\": \"LC8_L1T_TOA/LC80370352016018LGN00\"}, {\"date\": \"2016-02-03T18:03:15\", \"id\": \"LC8_L1T_TOA/LC80370352016034LGN00\"}, {\"date\": \"2016-02-19T18:03:06\", \"id\": \"LC8_L1T_TOA/LC80370352016050LGN00\"}, {\"date\": \"2016-03-06T18:03:05\", \"id\": \"LC8_L1T_TOA/LC80370352016066LGN00\"}, {\"date\": \"2016-03-22T18:02:58\", \"id\": \"LC8_L1T_TOA/LC80370352016082LGN00\"}, {\"date\": \"2016-04-07T18:02:50\", \"id\": \"LC8_L1T_TOA/LC80370352016098LGN00\"}, {\"date\": \"2016-04-23T18:02:43\", \"id\": \"LC8_L1T_TOA/LC80370352016114LGN00\"}, {\"date\": \"2016-05-09T18:02:46\", \"id\": \"LC8_L1T_TOA/LC80370352016130LGN00\"}, {\"date\": \"2016-05-25T18:02:50\", \"id\": \"LC8_L1T_TOA/LC80370352016146LGN00\"}, {\"date\": \"2016-06-10T18:02:54\", \"id\": \"LC8_L1T_TOA/LC80370352016162LGN00\"}, {\"date\": \"2016-06-26T18:03:01\", \"id\": \"LC8_L1T_TOA/LC80370352016178LGN00\"}, {\"date\": \"2016-07-12T18:03:09\", \"id\": \"LC8_L1T_TOA/LC80370352016194LGN00\"}, {\"date\": \"2016-07-28T18:03:13\", \"id\": \"LC8_L1T_TOA/LC80370352016210LGN00\"}, {\"date\": \"2016-08-13T18:03:15\", \"id\": \"LC8_L1T_TOA/LC80370352016226LGN00\"}, {\"date\": \"2016-08-29T18:03:23\", \"id\": \"LC8_L1T_TOA/LC80370352016242LGN00\"}, {\"date\": \"2016-09-14T18:03:26\", \"id\": \"LC8_L1T_TOA/LC80370352016258LGN00\"}, {\"date\": \"2016-09-30T18:03:27\", \"id\": \"LC8_L1T_TOA/LC80370352016274LGN00\"}, {\"date\": \"2016-10-16T18:03:32\", \"id\": \"LC8_L1T_TOA/LC80370352016290LGN00\"}, {\"date\": \"2016-11-01T18:03:33\", \"id\": \"LC8_L1T_TOA/LC80370352016306LGN00\"}, {\"date\": \"2016-11-17T18:03:33\", \"id\": \"LC8_L1T_TOA/LC80370352016322LGN00\"}, {\"date\": \"2016-12-03T18:03:32\", \"id\": \"LC8_L1T_TOA/LC80370352016338LGN00\"}, {\"date\": \"2016-12-19T18:03:27\", \"id\": \"LC8_L1T_TOA/LC80370352016354LGN00\"}, {\"date\": \"2017-01-04T18:03:24\", \"id\": \"LC8_L1T_TOA/LC80370352017004LGN00\"}, {\"date\": \"2017-02-05T18:03:12\", \"id\": \"LC8_L1T_TOA/LC80370352017036LGN00\"}, {\"date\": \"2017-02-21T18:03:07\", \"id\": \"LC8_L1T_TOA/LC80370352017052LGN00\"}, {\"date\": \"2017-03-09T18:02:59\", \"id\": \"LC8_L1T_TOA/LC80370352017068LGN00\"}, {\"date\": \"2016-04-30T18:08:59\", \"id\": \"LC8_L1T_TOA/LC80380352016121LGN00\"}, {\"date\": \"2016-05-16T18:08:57\", \"id\": \"LC8_L1T_TOA/LC80380352016137LGN00\"}]}";
	      parseJSON(API_DATE_FORMAT, json);
	}
	
	@Test
	public void testFlyby() throws java.text.ParseException {
//		flyby(1.5, 100.75);
//		flyby(1.5, 100.75, API_DATE_FORMAT.parse("2014-02-02T00:00:00"), null);
	   //Grand Canyon           36.098592   -112.097796
	   flyby(36.098592, -112.097796, API_DATE_FORMAT.parse("2014-02-02T00:00:00"), null);
	}
	
	@Test @Ignore
	public void testDateDiff(){
		Date newDate;
	   SimpleDateFormat daysFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {   
			newDate = API_DATE_FORMAT.parse("2014-03-24T03:29:20");
			Date prevDate = API_DATE_FORMAT.parse("2014-03-08T03:29:33");
			newDate = DateUtils.truncate(newDate, Calendar.DATE);
			prevDate = DateUtils.truncate(prevDate, Calendar.DATE);
			
			int diff = (int) ((newDate.getTime() - prevDate.getTime())
					/ (ResultType.DAY.compareTo(RESULT_TYPE) == 0 ? MILLIS_IN_DAY : MILLIS_IN_HOUR));
			System.out.println(diff);
		} catch (java.text.ParseException e) {
			log.severe("ERROR while parsing Date format.");
		}
	}
	
	//*********************************** JUNIT test ends
}
