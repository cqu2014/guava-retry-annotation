package com.oliver.rest;

import com.oliver.rest.rpc.client.ResponseErrorHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;

/**
 * @author dell
 */
@SpringBootApplication
@EnableConfigurationProperties
public class RestApplication {
	/**
	 * 需要验证的hostname
	 */
	private static String[] VERIFY_HOST_NAME_ARRAY = new String[]{};

	private static final HostnameVerifier HOSTNAME_VERIFIER = (hostname, sslSession) -> {
		if (StringUtils.isEmpty(hostname)){
			return false;
		}
		return !Arrays.asList(VERIFY_HOST_NAME_ARRAY).contains(hostname);
	};

	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}


	@Bean("restTemplate")
	public RestTemplate getRestTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ResponseErrorHandler());
		restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory(){
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				//如果是https请求设置hostname验证
				if (connection instanceof HttpsURLConnection){
					((HttpsURLConnection) connection)
							.setHostnameVerifier(HOSTNAME_VERIFIER);
				}
				connection.setReadTimeout(61000);
				connection.setConnectTimeout(10000);
				super.prepareConnection(connection,httpMethod);
			}
		});

		return restTemplate;
	}

}
