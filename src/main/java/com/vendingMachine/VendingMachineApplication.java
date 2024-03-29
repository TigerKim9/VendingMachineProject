package com.vendingMachine;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

import com.vendingMachine.home.DTO.WebClientBean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class VendingMachineApplication {

	@Autowired
	WebClient.Builder webClient;
	
	public static void main(String[] args) {
		SpringApplication.run(VendingMachineApplication.class, args);
	}

	@Bean
	public ApplicationRunner web() {
		
		return args -> {
			
			
			WebClient webClients = webClient.baseUrl("https://reqres.in/api/").build();
		
			Mono<WebClientBean> respo = webClients.get().uri(uri -> uri.path("users")
					.queryParam("page", 1)
					.build())
					.accept(MediaType.APPLICATION_FORM_URLENCODED)
					.retrieve().bodyToMono(WebClientBean.class);
//			.retrieve().bodyToMono(String.class);
			
			respo.doOnSuccess(ra ->{
				
					System.out.println("repo " + ra);
			}).subscribe();
			
					
		};
	}
	
}
