package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.share.BaseObject;
import kr.joberchip.core.storage.VideoBlockFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "video_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoBlock extends BaseObject {
  @Column(name = "title")
  @Getter
  private String title;

  @Column(name = "description")
  @Getter
  private String description;

  @Column(name = "video_link")
  @Getter
  private String videoLink;

  @OneToOne(mappedBy = "videoBlock")
  private VideoBlockFile videoFile;

  public UUID getVideoBlockId() {
    return this.objectId;
  }
}
