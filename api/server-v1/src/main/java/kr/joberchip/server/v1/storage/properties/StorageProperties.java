package kr.joberchip.server.v1.storage.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Getter
public class StorageProperties {

  /** Folder location for storing files */
  private String location = "upload-dir";

  public void setLocation(String location) {
    this.location = location;
  }
}
