package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.integration.annotation.MessagingGateway;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
@SpringBootApplication
public class DemoApplication {
	private static final Log LOGGER = LogFactory.getLog(DemoApplication.class);
	@Autowired
	private MessageSenderSvc.PubsubOutboundGateway messagingGateway;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}




	@PostMapping("/postMessage")
	public RedirectView postMessage(@RequestParam("message") String message) {
		this.messagingGateway.sendToPubsub(message);
		//LOGGER.info("Message Sent! Payload: " + message);
		return new RedirectView("/");
	}

}
