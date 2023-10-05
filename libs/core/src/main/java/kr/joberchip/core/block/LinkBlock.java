package kr.joberchip.core.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "link_block_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class LinkBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "link")
  @Lob
  private String link;

  public static LinkBlock of(
      String title, String description, String link, Integer x, Integer y, Integer w, Integer h) {

    LinkBlock generated = new LinkBlock(title, description, link);

    generated.setX(x);
    generated.setY(y);
    generated.setW(w);
    generated.setH(h);

    return generated;
  }
}
