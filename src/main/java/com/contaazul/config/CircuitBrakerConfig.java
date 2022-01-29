package com.contaazul.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class CircuitBrakerConfig {

	@Bean
	public CircuitBreakerRegistry cityCircuitBraker() {
		return CircuitBreakerRegistry.of( CircuitBreakerConfig
				.custom()
				.slidingWindowType( CircuitBreakerConfig.SlidingWindowType.COUNT_BASED )
				.slidingWindowSize( 10 )
				.waitDurationInOpenState( Duration.ofSeconds( 5 ) )
				.failureRateThreshold( 70.0f )
				.build() );
	}

}
