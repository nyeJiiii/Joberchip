package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joberchip.core.share.BaseObject;
import kr.joberchip.core.storage.ImageBlockFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageBlock extends BaseObject {
  @Column(name = "title")
  @Getter
  private String title;

  @Column(name = "description")
  @Getter
  private String description;

  @Column(name = "image_link")
  @Getter
  private String imageLink;

  @OneToOne(mappedBy = "imageBlock")
  private ImageBlockFile imageBlockFile;

  public UUID getImageBlockId() {
    return this.objectId;
  }
}
