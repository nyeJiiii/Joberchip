package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joberchip.core.share.BaseObject;
import kr.joberchip.core.storage.ImageBlockFile;
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

  @OneToOne(mappedBy = "imageBlock")
  private ImageBlockFile imageBlockFile;

  public static ImageBlock of(String title, String description) {
    return new ImageBlock(title, description, null);
  }

  public void modifyTitle(String title) {
    this.title = title;
  }

  public void modifyDescription(String description) {
    this.description = description;
  }

  public void modifyImageBlockFile(ImageBlockFile imageBlockFile) {
    this.imageBlockFile = imageBlockFile;
  }

  public void attachImageFile(ImageBlockFile imageBlockFile) {
    this.imageBlockFile = imageBlockFile;
  }

  public UUID getImageBlockId() {
    return this.objectId;
  }
}
