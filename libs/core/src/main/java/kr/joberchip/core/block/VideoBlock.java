package kr.joberchip.core.block;

import javax.persistence.*;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "video_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class VideoBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "video_link")
  @Lob
  private String videoLink;

  private VideoBlock(String title, String description) {
    this.title = title;
    this.description = description;
  }

  public static VideoBlock of(String title, String description) {
    return new VideoBlock(title, description);
  }

  public static VideoBlock of(
      String title, String description, Integer x, Integer y, Integer w, Integer h) {
    VideoBlock generated = new VideoBlock(title, description);

    generated.setX(x);
    generated.setY(y);
    generated.setW(w);
    generated.setH(h);

    return generated;
  }

  public static VideoBlock of(String title, String description, String videoLink) {
    return new VideoBlock(title, description, videoLink);
  }
}
