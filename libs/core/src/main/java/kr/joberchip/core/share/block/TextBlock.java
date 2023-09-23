package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.share.BaseObject;
import lombok.Getter;

@Entity
@Table(name = "text_block_tb")
public class TextBlock extends BaseObject {
  @Lob
  @Column(name = "content")
  @Getter
  private String content;

  public UUID getTextBlockId() {
    return this.objectId;
  }
}
