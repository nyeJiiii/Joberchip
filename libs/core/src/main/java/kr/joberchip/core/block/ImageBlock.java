package kr.joberchip.core.block;

import javax.persistence.*;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "image_block_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ImageBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "image_link")
  @Lob
  private String imageLink;

  public static ImageBlock of(String title, String description, String imageLink) {
    return new ImageBlock(title, description, imageLink);
  }

  public void modifyTitle(String title) {
    this.title = title;
  }

  public void modifyDescription(String description) {
    this.description = description;
  }

  public void modifyLink(String imageLink) {
    this.imageLink = imageLink;
  }
}
