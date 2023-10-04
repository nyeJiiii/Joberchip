package kr.joberchip.core.block;

import javax.persistence.*;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "text_block_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class TextBlock extends BaseObject {
  @Column(name = "content")
  @Lob
  @Setter
  private String content;

  public static TextBlock of(String content, Integer x, Integer y, Integer w, Integer h) {
    TextBlock generated = new TextBlock(content);

    generated.setX(x);
    generated.setY(y);
    generated.setW(w);
    generated.setH(h);

    return generated;
  }
}
