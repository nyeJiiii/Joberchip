package kr.joberchip.core.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "template_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class TemplateBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "link")
  private String link;

  private TemplateBlock(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public static TemplateBlock of(
      String title, String description, Integer x, Integer y, Integer w, Integer h) {

    TemplateBlock generated = new TemplateBlock(title, description);

    generated.setX(x);
    generated.setY(y);
    generated.setW(w);
    generated.setH(h);

    return generated;
  }
}
