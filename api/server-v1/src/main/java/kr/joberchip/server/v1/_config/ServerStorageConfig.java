package kr.joberchip.server.v1._config;

import kr.joberchip.server.v1.storage.properties.StorageProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = {StorageProperties.class})
public class ServerStorageConfig {}
