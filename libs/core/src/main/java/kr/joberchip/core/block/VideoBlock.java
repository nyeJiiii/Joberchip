package kr.joberchip.core.block;

import javax.persistence.*;
import kr.joberchip.core.BaseObject;
import lombok.*;

@Entity
@Table(name = "video_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class VideoBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "video_link")
  @Lob
  private String videoLink;

  public static VideoBlock of(String title, String description, String videoLink) {
    return new VideoBlock(title, description, videoLink);
  }

  public void modifyTitle(String title) {
    this.title = title;
  }

  public void modifyDescription(String description) {
    this.description = description;
  }

  public void modifyVideoLink(String videoLink) {
    this.videoLink = videoLink;
  }
}
