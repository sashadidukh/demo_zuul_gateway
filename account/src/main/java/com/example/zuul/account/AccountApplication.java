package com.example.zuul.account;


import java.net.URI;
import java.util.List;
import java.util.Map;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AccountApplication {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private  BookClient bookClient;

	@RequestMapping(value = "/account/{userName}")
	public ResponseEntity<String> available(HttpServletRequest request, @PathVariable String userName) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(request.getRequestURL().toString()));
		JSONObject result = new JSONObject();
		String host =  discoveryClient.getLocalServiceInstance().getHost() ;
		Integer port =  discoveryClient.getLocalServiceInstance().getPort();

		result.put("userName",userName);
		result.put("books",bookClient.available(userName));
		result.put("node",host+":"+port);
		return new ResponseEntity<String>(result.toJSONString(),headers, HttpStatus.OK);
	}


	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@FeignClient(serviceId = "book")
	public interface BookClient{

		@RequestMapping(method = RequestMethod.GET,value = "/available/{userName}")
		Map<String,Object> available(@PathVariable(name = "userName") String userName);

	}


}
