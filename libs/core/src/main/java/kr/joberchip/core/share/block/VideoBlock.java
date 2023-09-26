package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.share.BaseObject;
import kr.joberchip.core.storage.VideoBlockFile;
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

  @OneToOne(mappedBy = "videoBlock")
  private VideoBlockFile videoBlockFile;

  public static VideoBlock of(String title, String description) {
    return new VideoBlock(title, description, null, null);
  }

  public static VideoBlock of(String title, String description, String videoLink) {
    return new VideoBlock(title, description, videoLink, null);
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

  public void attachVideoFile(VideoBlockFile videoBlockFile) {
    this.videoBlockFile = videoBlockFile;
  }

  public UUID getVideoBlockId() {
    return this.objectId;
  }
}
