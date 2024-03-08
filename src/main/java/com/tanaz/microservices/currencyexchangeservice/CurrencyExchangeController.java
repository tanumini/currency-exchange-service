package com.tanaz.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
  
	private Logger logger =LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ExchangeValueRepository repository;
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to){
		//642a564c9fd6f3184ee7dbaffd708fe4,a249be8d41eaa26d] [642a564c9fd6f3184ee7dbaffd708fe4-a249be8d41eaa26d]
		logger.info("retriveExchangeValue call with {} to",from,to);
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		if(exchangeValue==null) {
			throw new RuntimeException("Unable to find data for "+from+"to"+to);
		}
		//exchangeValue.setPort(8000);
		exchangeValue.setPort(
				Integer.parseInt(env.getProperty("local.server.port")));
		return exchangeValue;
	}
}
