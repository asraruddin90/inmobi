/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inmobi.sample.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author am
 */
public class InMobi {
	int i;
	/**
	 * @param args the command line arguments
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException, Exception {
		InMobi inMobi = new InMobi();

		while(true)
		{
			inMobi.process();
			Thread.sleep(360000);
		}
	}


	private  void process() throws IOException, JsonGenerationException, JsonMappingException,
	ClientProtocolException, UnsupportedEncodingException, JSONException {
		RequestObject requestObj = new RequestObject();
		Device device = new Device();
		Imp imp = new Imp();
		Site site = new Site();

		String[] ipArray ={"116.202.0.139","1.209.188.180",  "118.180.1.37", "42.104.128.0", "103.13.250.119", "109.120.23.111", "81.52.140.7", "109.144.115.47", "58.97.0.0", "104.72.70.45"};
		String[] userAgentArray = {"Mozilla/5.0 (Linux; Android 5.0; Aqua Power+ Build/LRX21M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36",
				"Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML,like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36", 
				"Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
				"Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev>(KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>",
				"Mozilla/5.0 (Linux; U; Android 2.3.4; fr-fr; HTC Desire Build/GRJ22) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
				"Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; HTC_IncredibleS_S710e Build/GRJ90) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
				"Mozilla/5.0 (Linux; Android 6.0; Nexus 6P Build/MDA83) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36",
				"Mozilla/5.0 (Linux; U; Android 4.4.4; Nexus 5 Build/KTU84P) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
				"Mozilla/5.0 (Linux; Android 5.0.2; SAMSUNG SM-G925F Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/3.0 Chrome/38.0.2125.102 Mobile Safari/537.36",
				"Mozilla/5.0 (Linux; Android 4.4.4; tr-tr; SAMSUNG SM-N910C Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/2.0 Chrome/34.0.1847.76 Mobile Safari/537.36"
		};

		device.gpid = "70a01ef9-8d4f-4509-bce6-6f955cead6a3";
		device.ip = ipArray[new Random().nextInt(ipArray.length)];
		System.out.println("IP Address: " + device.ip);
		device.o1 = "5e9d69fb33c3e4eafda62df0749297edc2a7b709";
		device.um5 ="f7c791b7cd71dd626215f106923da607";
		device.ua =userAgentArray[new Random().nextInt(userAgentArray.length)];
		System.out.println("User Agent: "+device.ua);
		device.adt =0;
		imp.ads= 5;
		site.id = "57ed1df39c83477eb3cff1945cbc7dc5";
		site.reftag = "ICS_303001749";
		requestObj.responseformat = "native"; 
		requestObj.trackerType ="url_ping";
		requestObj.device = device;
		requestObj.site = site;

		List<Imp> impList = new ArrayList<Imp>();
		impList.add(imp);

		requestObj.imp = impList;
		ObjectMapper mapper = new ObjectMapper();
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://api.w.inmobi.com/showad/v2.1");
		String jsonInString = mapper.writeValueAsString(requestObj);
		System.out.println("Request: " +jsonInString);
		StringEntity entity = new StringEntity(jsonInString, "UTF-8"); 
		entity.setContentType("application/json"); 
		httppost.setEntity(entity);

		//Execute and get the response.
		Date date = new Date(System.currentTimeMillis());
		String requestTime = date.toString();
		HttpResponse response = httpclient.execute(httppost); 

		InputStream ips = response.getEntity().getContent(); 
		BufferedReader buf = new BufferedReader(new InputStreamReader(ips,"UTF-8")); 
		if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) 
		{ 
			//throw new Exception(response.getStatusLine().getReasonPhrase()); 
		} 
		StringBuilder sb = new StringBuilder(); 
		String s; 
		while(true ) 
		{ 
			s = buf.readLine(); 
			if(s==null || s.length()==0) 
				break; 
			sb.append(s); 
		} 
		buf.close(); 
		ips.close(); 
		String responseString = sb.toString();
		//System.out.println(new String(responseString));
		try{
			JSONObject jsonObject = new JSONObject(responseString);
			System.out.println("ADS Response - All PubContent responses: " +jsonObject.getJSONArray("ads"));
			JSONArray array = jsonObject.getJSONArray("ads");
			JSONObject jsonObjectAds;
			String pubContent = null;
			for(int i=1; i<=5; i++)
			{
			jsonObjectAds= array.getJSONObject(i);
			//System.out.println("pubContent :"+jsonObjectAds.getString("pubContent"));
			pubContent = jsonObjectAds.getString("pubContent");
			

//			String pubContent = jsonObjectAds.getString("pubContent");

			byte[] byteArray = Base64.decodeBase64(pubContent);

			String pubContentJsonString = new String(byteArray);
			System.out.println("pubContent Number "+i+ " after decoding :"+ pubContentJsonString);

			JSONObject jsonObjectPubContent = new JSONObject(pubContentJsonString);

			//System.out.println("title :"+jsonObjectPubContent.getString("title"));
			System.out.println("image_xhdpi :"+jsonObjectPubContent.getJSONObject("image_xhdpi").toString());
			System.out.println("click_url :"+jsonObjectPubContent.getString("click_url"));
			System.out.println("package_name :"+jsonObjectPubContent.getString("package_name"));
			
			DataAccess access = new DataAccess();
			String img = jsonObjectPubContent.getJSONObject("image_xhdpi").toString();
			String click = jsonObjectPubContent.getString("click_url");
			String pck = jsonObjectPubContent.getString("package_name");
				access.insertDb(img, click, pck, requestTime, device.ip);
			}
		}
			catch(JSONException e )
		{
			System.out.println("Unable to parse JSON String : "+e.getMessage());
		}
			catch (Exception e) {
			System.out.println("Some Exception : "+e.getMessage());
		}
		
		
	}
}
