package kr.joberchip.server.v1._config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"kr.joberchip.core"})
@EnableJpaRepositories(basePackages = {"kr.joberchip.server.v1.space"})
@EnableJpaAuditing
public class ServerJpaConfig {}
