package kr.joberchip.core;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
public abstract class BaseEntity {

  @CreatedDate LocalDateTime createdAt;

  @LastModifiedDate LocalDateTime modifiedAt;
}
