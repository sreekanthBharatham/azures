package com.advana.azure.storage.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.advana.common.ApiSignature;

import io.swagger.v3.oas.annotations.media.Schema;

@CrossOrigin(origins = "http://localhost:52221")
@RestController
@RequestMapping(value = "/data/azure_storage/")
public class AzureStorageData {
	public static final Logger logger = LogManager.getLogger(AzureStorageData.class);
	
	@Autowired
	RestTemplate restTemplate;
	//GET https://management.azure.com/subscriptions?api-version=2020-01-01
	/**
	 * To fetch account subscriptions id list from azure
	 * 
	 * @param file details like azure subscriptions
	 * @return Response with  subscriptions id  list 
	 */

	@RequestMapping(value = ApiSignature.FETCH_SUBSCRIPTION, method = RequestMethod.GET)
	public List<String> fetchSubscriptions(@RequestHeader(value="Authorization",required = false) String bearerToken) {
		List<String> subscriptionsList = new ArrayList<>();
		
		/*try {
			
			///if(bearerToken!=null) {
			System.out.println("bearerToken :; "+bearerToken);
			logger.info("bearerToken   :: "+bearerToken);
			//JSONObject obj = (JSONObject) new JSONParser().parse(details);
			// accountName = obj.get("accountName").toString();
			String baseUrl = "https://management.azure.com/subscriptions?api-version=2022-05-01";
		 	
			//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
			System.out.println("baseUrl  :: "+baseUrl);
		    URI uri = new URI(baseUrl);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    if(bearerToken.contains("Bearer ")) {
		    headers.set("Authorization", bearerToken );
		    }else {
		    	headers.set("Authorization", "Bearer "+bearerToken );
		    }
	        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		    //Response res = result.getBody()
		    System.out.println("result body ::" +response.getBody());
		    String resultStr = response.getBody();
		    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
		    if(resultObj.containsKey("value")) {
		    JSONArray valueArray = (JSONArray) resultObj.get("value");
		    
		    for(int index = 0;index<valueArray.size();index++) {
		    	JSONObject contObj = (JSONObject) valueArray.get(index);
		    	System.out.println("contObj ::" +contObj);
		    	subscriptionsList.add(contObj.get("subscriptionId").toString());
		    }
		    logger.info("subscriptionsList ::" ,subscriptionsList);
		    System.out.println("subscriptionsList ::" +subscriptionsList);
		   
	       // return secList;
		   
		    }
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
		if(subscriptionsList.size()==0) {
			subscriptionsList.add("9051542b-d42a-467b-a53b-207052cb174e");
		}
		 return subscriptionsList;
	}
	/**
	 * To fetch account subscriptions id list from azure
	 * 
	 * @param file details like azure subscriptions
	 * @return Response with  subscriptions id  list 
	 */

	@RequestMapping(value = ApiSignature.FETCH_RESOURCEGROUPS, method = RequestMethod.GET)
	public List<String> fetchResourcegroups(@RequestHeader(value="Authorization",required = false) String bearerToken) {
		List<String> resourceGroupList = new ArrayList<>();
		
		/*try {
			
			//if(bearerToken!=null) {
			System.out.println("bearerToken :; "+bearerToken);
			logger.info("bearerToken   :: "+bearerToken);
			//JSONObject obj = (JSONObject) new JSONParser().parse(details);
			// accountName = obj.get("accountName").toString();
			List<String> subIds = fetchSubscriptions(bearerToken);
			if(subIds.size()>0) {
			String baseUrl = "https://management.azure.com/subscriptions/"+subIds.get(0)+"/resourcegroups?api-version=2021-04-01";
			       
			
			//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
			System.out.println("baseUrl  :: "+baseUrl);
		    URI uri = new URI(baseUrl);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", bearerToken );
	        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		    //Response res = result.getBody()
		    System.out.println("result body ::" +response.getBody());
		    String resultStr = response.getBody();
		    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
		    if(resultObj.containsKey("value")) {
		    JSONArray valueArray = (JSONArray) resultObj.get("value");
		    
		    for(int index = 0;index<valueArray.size();index++) {
		    	JSONObject contObj = (JSONObject) valueArray.get(index);
		    	System.out.println("contObj ::" +contObj);
		    	resourceGroupList.add(contObj.get("name").toString());
		    }
		    logger.info("subscriptionsList ::" ,resourceGroupList);
		    System.out.println("subscriptionsList ::" +resourceGroupList);
		    }
		   
	       // return secList;
		   
		    }
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
		
		if(resourceGroupList.size()==0) {
			resourceGroupList.add("dev-adv-001-rg");
		}
		 return resourceGroupList;
	}
	/**
	 * To fetch account names list from azure storage
	 * 
	 * @param file details like azure storage name
	 * @return Response with  storage account names Name list 
	 */

	@RequestMapping(value = ApiSignature.FETCH_STORAGE_ACCOUNT_NAMES, method = RequestMethod.GET)
	public List<String> fetchStorageAccounts(@RequestHeader(value="Authorization",required = false) String bearerToken) {
		List<String> conNamesList = new ArrayList<>();
		
		try {
			
			//if(bearerToken!=null) {
			System.out.println("bearerToken :; "+bearerToken);
			logger.info("bearerToken   :: "+bearerToken);
			List<String> subIds = fetchSubscriptions(bearerToken);
			if(subIds.size()>0) {
			//JSONObject obj = (JSONObject) new JSONParser().parse(details);
			// accountName = obj.get("accountName").toString();
			String baseUrl = "https://management.azure.com/subscriptions/"+subIds.get(0)+"/providers/Microsoft.Storage/storageAccounts?api-version=2022-05-01";
			       
			
			//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
			System.out.println("baseUrl  :: "+baseUrl);
		    URI uri = new URI(baseUrl);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", bearerToken );
	        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		    //Response res = result.getBody()
		    System.out.println("result body ::" +response.getBody());
		    String resultStr = response.getBody();
		    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
		    if(resultObj.containsKey("value")) {
		    JSONArray valueArray = (JSONArray) resultObj.get("value");
		    
		    for(int index = 0;index<valueArray.size();index++) {
		    	JSONObject contObj = (JSONObject) valueArray.get(index);
		    	System.out.println("contObj ::" +contObj);
		    	conNamesList.add(contObj.get("name").toString());
		    }
		    logger.info("conNamesList ::" ,conNamesList);
		    System.out.println("conNamesList ::" +conNamesList);
		    }else {
		    	//return Response.status(ResponseMessage.RS_10_5, response.getBody());
		    //if(conNamesList.size()==0) {
		    	
		    	
		    }
			/*}else {
				conNamesList.add("devadv001sa ");
		    	conNamesList.add("devadv002sa");
		    }*/
	       // return secList;
		   
			}
		    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 return conNamesList;
	}
	/**
	 * To fetch account names list from azure storage
	 * 
	 * @param file details like azure storage name
	 * @return Response with  storage account names Name list 
	 */

	@RequestMapping(value = ApiSignature.FETCH_CONTAINER_FOLDERS, method = RequestMethod.POST)
	public List<String> fetchStorageResources(@RequestHeader(value="Authorization",required = false) String bearerToken,@Schema(name = "storage_account_name",example = "{\"storage_account_name\":\"value\"}") @RequestBody JSONObject obj) {
		List<String> conNamesList = new ArrayList<>();
		//for plexadevstorageaccount
		/*conNamesList.add("FlexCloud_Demo");
		conNamesList.add("Healthcare");
		conNamesList.add("Lending_C	lub");
		conNamesList.add("ETA");
		
		return conNamesList;*/
		try {
			
			//if(bearerToken!=null) {
			 List<String> subIds = fetchSubscriptions(bearerToken);
			if(subIds.size()>0) {
			List<String> resIds = fetchResourcegroups(bearerToken);
			System.out.println("bearerToken :; "+bearerToken);
			logger.info("bearerToken   :: "+bearerToken);
			if(resIds.size()>0) {
				//JSONObject objnew = (JSONObject) new JSONParser().parse(obj);
				//String accountName = "devadv001sa";
				String accountName = obj.get("storage_account_name").toString();
			//String accountName = obj.get("accountName").toString();
			String baseUrl = "https://management.azure.com/subscriptions/"+subIds.get(0)+"/resourceGroups/"+resIds.get(0)+"/providers/Microsoft.Storage/storageAccounts/"+accountName+"/blobServices/default/containers";
			        //baseUrl= "https://management.azure.com/subscriptions/0f560e4a-e476-4f83-89d5-ac9949c5fdb8/resourceGroups/dev-adv-001-rg/providers/Microsoft.Storage/storageAccounts/devadv001sa/blobServices/default/containers";
			if(obj.containsKey("containerName")) {
				baseUrl = baseUrl+"/"+obj.get("containerName").toString();
			}
			baseUrl = baseUrl+"?api-version=2022-05-01";
			
			
			//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
			System.out.println("baseUrl  :: "+baseUrl);
		    URI uri = new URI(baseUrl);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("Authorization", bearerToken );
	        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
	        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
		    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		    //Response res = result.getBody()
		    System.out.println("result body ::" +response.getBody());
		    String resultStr = response.getBody();
		    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
		    if(resultObj.containsKey("value")) {
		    JSONArray valueArray = (JSONArray) resultObj.get("value");
		    
		    for(int index = 0;index<valueArray.size();index++) {
		    	JSONObject contObj = (JSONObject) valueArray.get(index);
		    	System.out.println("contObj ::" +contObj);
		    	conNamesList.add(contObj.get("name").toString());
		    }
		    logger.info("conNamesList ::" ,conNamesList);
		    System.out.println("conNamesList ::" +conNamesList);
		    }else {
		    	//return Response.status(ResponseMessage.RS_10_5, response.getBody());
		    //if(conNamesList.size()==0) {
		    	
		    	
		    
			}
	       // return secList;
			}
			}
		    
		} catch (Exception ex) {
			ex.getMessage();
		}
		 return conNamesList;
	}

			
		/**
		 * To fetch  containers list from azure storage
		 * 
		 * @param file details like azure storage name
		 * @return Response with  conatiners Name list 
		 */
		
		@RequestMapping(value = ApiSignature.FETCH_DATA_LOCATIONS, method = RequestMethod.POST)
		public List<String> fetchDataLocations(@RequestHeader(value="Authorization",required = false) String bearerToken,@Schema(name = "storage_account_name",example = "{\"storage_account_name\":\"value\"}") @RequestBody JSONObject obj) {
			List<String> conNamesList = new ArrayList<>();
			
			try {
				
				//if(bearerToken!=null) {
				 List<String> subIds = fetchSubscriptions(bearerToken);
				if(subIds.size()>0) {
				List<String> resIds = fetchResourcegroups(bearerToken);
				System.out.println("bearerToken :; "+bearerToken);
				logger.info("bearerToken   :: "+bearerToken);
				if(resIds.size()>0) {
					//JSONObject objnew = (JSONObject) new JSONParser().parse(obj);
					//String accountName = "devadv001sa";
					String accountName = obj.get("storage_account_name").toString();
				//String accountName = obj.get("accountName").toString();
				String baseUrl = "https://management.azure.com/subscriptions/"+subIds.get(0)+"/resourceGroups/"+resIds.get(0)+"/providers/Microsoft.Storage/storageAccounts/"+accountName+"/blobServices/default/containers";
				        //baseUrl= "https://management.azure.com/subscriptions/0f560e4a-e476-4f83-89d5-ac9949c5fdb8/resourceGroups/dev-adv-001-rg/providers/Microsoft.Storage/storageAccounts/devadv001sa/blobServices/default/containers";
				if(obj.containsKey("containerName")) {
					baseUrl = baseUrl+"/"+obj.get("containerName").toString();
				}
				baseUrl = baseUrl+"?api-version=2022-05-01";
				
				
				//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
				System.out.println("baseUrl  :: "+baseUrl);
			    URI uri = new URI(baseUrl);
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    headers.set("Authorization", bearerToken );
		        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
		        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			    //Response res = result.getBody()
			    System.out.println("result body ::" +response.getBody());
			    String resultStr = response.getBody();
			    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
			    if(resultObj.containsKey("value")) {
			    JSONArray valueArray = (JSONArray) resultObj.get("value");
			    
			    for(int index = 0;index<valueArray.size();index++) {
			    	JSONObject contObj = (JSONObject) valueArray.get(index);
			    	System.out.println("contObj ::" +contObj);
			    	conNamesList.add(contObj.get("name").toString());
			    }
			    logger.info("conNamesList ::" ,conNamesList);
			    System.out.println("conNamesList ::" +conNamesList);
			    }else {
			    	//return Response.status(ResponseMessage.RS_10_5, response.getBody());
			    //if(conNamesList.size()==0) {
			    	
			    	
			    
				}
		       // return secList;
				}
				}
			    
			} catch (Exception ex) {
				ex.getMessage();
			}
			 return conNamesList;
		}

			
			//for plexadevstorageaccount
			/*conNamesList.add("advanadevdata");
			conNamesList.add("plexademo");
			conNamesList.add("plexadevdata");
			conNamesList.add("advanamongodbbackup");
			conNamesList.add("databrickstransformationpreview");
			conNamesList.add("databricksadvanadevdata");
			return conNamesList;*/
			/*try {
				
				if(bearerToken!=null) {
				System.out.println("bearerToken :; "+bearerToken);
				logger.info("bearerToken   :: "+bearerToken);
				//JSONObject obj = (JSONObject) new JSONParser().parse(details);
				String accountName = obj.get("accountName").toString();
				String baseUrl = "https://management.azure.com/subscriptions/0f560e4a-e476-4f83-89d5-ac9949c5fdb8/resourceGroups/dev-adv-001-rg/providers/Microsoft.Storage/storageAccounts/devadv001sa/blobServices/default/containers";
				        //baseUrl= "https://management.azure.com/subscriptions/0f560e4a-e476-4f83-89d5-ac9949c5fdb8/resourceGroups/dev-adv-001-rg/providers/Microsoft.Storage/storageAccounts/devadv001sa/blobServices/default/containers";
				if(obj.containsKey("containerName")) {
					baseUrl = baseUrl+"/"+obj.get("containerName").toString();
				}
				baseUrl = baseUrl+"?api-version=2022-05-01";
				accountName = "devadv001sa";
				
				//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
				System.out.println("baseUrl  :: "+baseUrl);
			    URI uri = new URI(baseUrl);
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    headers.set("Authorization", bearerToken );
		        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
		        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			    //Response res = result.getBody()
			    System.out.println("result body ::" +response.getBody());
			    String resultStr = response.getBody();
			    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
			    if(resultObj.containsKey("value")) {
			    JSONArray valueArray = (JSONArray) resultObj.get("value");
			    
			    for(int index = 0;index<valueArray.size();index++) {
			    	JSONObject contObj = (JSONObject) valueArray.get(index);
			    	System.out.println("contObj ::" +contObj);
			    	conNamesList.add(contObj.get("name").toString());
			    }
			    logger.info("conNamesList ::" ,conNamesList);
			    System.out.println("conNamesList ::" +conNamesList);
			    }else {
			    	return Response.status(ResponseMessage.RS_10_5, response.getBody());
			    //if(conNamesList.size()==0) {
			    	
			    	
			    }
				}else {
					conNamesList.add("Dummy1");
			    	conNamesList.add("Dummy2");
			    }
	           // return secList;
			    return Response.status(ResponseMessage.RS_10_0, conNamesList);
				
			    
			} catch (Exception ex) {
				return Response.status(ResponseMessage.RS_10_1, ex.getMessage());
			}*/
		

		@RequestMapping(value = ApiSignature.FETCH_BLOBS, method = RequestMethod.POST)
		public JSONArray fetchBlobs(@RequestHeader(value="Authorization",required = false) String bearerToken,@Schema(name = "container_name",example = "{\"container_name\":\"value\"}") @RequestBody JSONObject containerObj) {
			//System.out.println("containername  :: "+containername);
			//String containername=containernamevalue.replace("\"", "");
			//List<String> conNamesList = new ArrayList<>();
			JSONArray conNamesArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			String containername = containerObj.get("container_name").toString().replace("\"", "");
			if(containername.equalsIgnoreCase("advanadevdata")) {
			//for plexadevstorageaccount
				jsonObject = new JSONObject();
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesArray.add(jsonObject);
				jsonObject = new JSONObject();
			jsonObject.put("filename", "aggregate_model_input.parquet");
			jsonObject.put("filetype", "parquet");
			conNamesArray.add(jsonObject);
			jsonObject = new JSONObject();
			jsonObject.put("filename", "auto_imports.parquet");
			jsonObject.put("filetype", "parquet");
			conNamesArray.add(jsonObject);
			jsonObject = new JSONObject();
			jsonObject.put("filename", "Aggregate_Model_Scoring_Latest.csv");
			jsonObject.put("filetype", "csv");
			conNamesArray.add(jsonObject);
			
			}else if(containername.equalsIgnoreCase("plexademo")) {
				jsonObject = new JSONObject();
				jsonObject.put("filename", "11.csv");
				jsonObject.put("filetype", "csv");
				conNamesArray.add(jsonObject);
				jsonObject = new JSONObject();
				jsonObject.put("filename", "Final_Master.csv");
				jsonObject.put("filetype", "csv");
				conNamesArray.add(jsonObject);
				jsonObject = new JSONObject();
				jsonObject.put("filename", "mytabletest.xlsx");
				jsonObject.put("filetype", "xlsx");
				conNamesArray.add(jsonObject);
				
			}else if(containername.equalsIgnoreCase("plexadevdata")) {
				jsonObject = new JSONObject();
				jsonObject.put("filename", "ab.csv");
				jsonObject.put("filetype", "csv");
				conNamesArray.add(jsonObject);
				jsonObject = new JSONObject();
				jsonObject.put("filename", "abc.parquet");
				jsonObject.put("filetype", "parquet");
				conNamesArray.add(jsonObject);
				jsonObject = new JSONObject();
				jsonObject.put("filename", "adcadc");
				jsonObject.put("filetype", "folder");
				conNamesArray.add(jsonObject);
			}else {
				jsonObject.put("filedata", "No Data available");
				conNamesArray.add(jsonObject);
				return conNamesArray;
			}
			/*else if(containername.equalsIgnoreCase("advanamongodbbackup")) {
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("advanamongodbbackup");
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("databrickstransformationpreview");
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("databricksadvanadevdata");
			}else if(containername.equalsIgnoreCase("databrickstransformationpreview")) {
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("advanamongodbbackup");
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("databrickstransformationpreview");
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("databricksadvanadevdata");
			}else if(containername.equalsIgnoreCase("databricksadvanadevdata")) {
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("advanamongodbbackup");
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("databrickstransformationpreview");
				jsonObject.put("filename", "Cartype_Age_Training_Data");
				jsonObject.put("filetype", "folder");
				conNamesList.add("databricksadvanadevdata");
			}*/
			return conNamesArray;
			/*try {
				
				if(bearerToken!=null) {
				System.out.println("bearerToken :; "+bearerToken);
				logger.info("bearerToken   :: "+bearerToken);
				//JSONObject obj = (JSONObject) new JSONParser().parse(details);
				String accountName = obj.get("accountName").toString();
				String baseUrl = "https://management.azure.com/subscriptions/0f560e4a-e476-4f83-89d5-ac9949c5fdb8/resourceGroups/dev-adv-001-rg/providers/Microsoft.Storage/storageAccounts/devadv001sa/blobServices/default/containers";
				        //baseUrl= "https://management.azure.com/subscriptions/0f560e4a-e476-4f83-89d5-ac9949c5fdb8/resourceGroups/dev-adv-001-rg/providers/Microsoft.Storage/storageAccounts/devadv001sa/blobServices/default/containers";
				if(obj.containsKey("containerName")) {
					baseUrl = baseUrl+"/"+obj.get("containerName").toString();
				}
				baseUrl = baseUrl+"?api-version=2022-05-01";
				accountName = "devadv001sa";
				
				//Key ==  eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL3ZhdWx0LmF6dXJlLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2QxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OS8iLCJpYXQiOjE2NjgxNDM1NzcsIm5iZiI6MTY2ODE0MzU3NywiZXhwIjoxNjY4MTQ3NTIyLCJhY3IiOiIxIiwiYWlvIjoiQVRRQXkvOFRBQUFBTDkyQnZtWUk4TzFmN21qSUJqbFJ6UC9BL0VzcnBvWERDWHlxOTN6S3VoSmFvNVNNZ3VXWUpVTU5NR3JrejcrbCIsImFtciI6WyJwd2QiXSwiYXBwaWQiOiI4NmZhZDExNS03ZmRiLTQzOWItODIwYS05Y2FkZTMxZjNkYWQiLCJhcHBpZGFjciI6IjAiLCJmYW1pbHlfbmFtZSI6IlBhbGNodXJpIiwiZ2l2ZW5fbmFtZSI6IlNyaW5pdmFzYXJhbyIsImdyb3VwcyI6WyIwMDlhNDMwMy1kMTYzLTRjZjEtYTU5My03YzFkOWNkNGIyZWQiLCJmYjU1NTA4NS05ZTU1LTRmOTYtODk1Ny00ZmI2ZTZmMDU2ZGYiXSwiaXBhZGRyIjoiNTAuNzcuMTYyLjE4NSIsIm5hbWUiOiJTcmluaXZhc2FyYW8gUGFsY2h1cmkiLCJvaWQiOiIzYzdhYzFlMy02MjBjLTRlNjAtODgzNi0zZjk3NzUzN2JkZTMiLCJwdWlkIjoiMTAwMzIwMDIzNUIwMTNCOSIsInJoIjoiMC5BWDBBbDJBYTBRN2M1azZKZmFlbHZmWGVXVG16cU0taWdocEhvOGtQd0w1NlFKT2NBSzAuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoib2lwRVFPU18wLWhUT2JLLTYtYVpkajhaS3h5N1dCRWRiYVpqd09GbUF2SSIsInRpZCI6ImQxMWE2MDk3LWRjMGUtNGVlNi04OTdkLWE3YTViZGY1ZGU1OSIsInVuaXF1ZV9uYW1lIjoic3Jpbml2YXNhcmFvLnBhbGNodXJpQGFkdmFuYS5hcHAiLCJ1cG4iOiJzcmluaXZhc2FyYW8ucGFsY2h1cmlAYWR2YW5hLmFwcCIsInV0aSI6IlBEN2dlTDNBSkVpZmd6N0ZvVU1EQUEiLCJ2ZXIiOiIxLjAifQ.E6KpeACTNgL4J94Di-YQPulZniFW-ICwrw8lDrMpZbRnBTQDg91Ciitmkrn2Lzc3ewynbotVjMLIURp1sV39XFWBBEON32tHukpWXiYjt8GnNVO1F3Vd0B34gXLSgvLucHwemNoVj946Mu89ZG0-sqeEsaupRgp7p0WScg5c4vKhd4yYK3HMtAqd-VgUUxe_OC0P1G8WiBpJSEnFqdIhs-F1dAl45wRIaf7QvMlolGRQgFDe4Dd1rtYnhLy2T5S6nTS-0rNXDLIjSC6Kk34DeCDOuf9oGyp8Y7gNTOhx8Sb_Sg2rFjlQ0s-Kld2p4uUihXCVegKkDHFGE_S_JFYy3Q
				System.out.println("baseUrl  :: "+baseUrl);
			    URI uri = new URI(baseUrl);
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);
			    headers.set("Authorization", bearerToken );
		        HttpEntity<String> entity = new HttpEntity<String>("tokens", headers);
		        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			    //ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
			    //Response res = result.getBody()
			    System.out.println("result body ::" +response.getBody());
			    String resultStr = response.getBody();
			    JSONObject resultObj = (JSONObject) new JSONParser().parse(resultStr);
			    if(resultObj.containsKey("value")) {
			    JSONArray valueArray = (JSONArray) resultObj.get("value");
			    
			    for(int index = 0;index<valueArray.size();index++) {
			    	JSONObject contObj = (JSONObject) valueArray.get(index);
			    	System.out.println("contObj ::" +contObj);
			    	conNamesList.add(contObj.get("name").toString());
			    }
			    logger.info("conNamesList ::" ,conNamesList);
			    System.out.println("conNamesList ::" +conNamesList);
			    }else {
			    	return Response.status(ResponseMessage.RS_10_5, response.getBody());
			    //if(conNamesList.size()==0) {
			    	
			    	
			    }
				}else {
					conNamesList.add("Dummy1");
			    	conNamesList.add("Dummy2");
			    }
	           // return secList;
			    return Response.status(ResponseMessage.RS_10_0, conNamesList);
				
			    
			} catch (Exception ex) {
				return Response.status(ResponseMessage.RS_10_1, ex.getMessage());
			}*/
		}


}
