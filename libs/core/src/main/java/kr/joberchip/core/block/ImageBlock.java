package kr.joberchip.core.block;

import javax.persistence.*;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "image_block_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class ImageBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "image_link")
  @Lob
  private String imageLink;

  private ImageBlock(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public static ImageBlock of(
      String title, String description, Integer x, Integer y, Integer w, Integer h, Boolean visible) {
    ImageBlock generated = new ImageBlock(title, description);

    generated.setX(x);
    generated.setY(y);
    generated.setW(w);
    generated.setH(h);
    generated.setVisible(visible);

    return generated;
  }
}
