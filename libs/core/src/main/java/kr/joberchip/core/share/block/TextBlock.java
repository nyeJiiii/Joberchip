package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.share.BaseObject;
import lombok.*;

@Entity
@Table(name = "text_block_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class TextBlock extends BaseObject {
  @Column(name = "content")
  @Lob
  @Setter
  private String content;

  public static TextBlock of(String content) {
    return new TextBlock(content);
  }

  public void modifyContent(String content) {
    this.content = content;
  }

  public UUID getTextBlockId() {
    return this.objectId;
  }

  public void changeVisible() {
    this.visible = !this.visible;
  }
}
