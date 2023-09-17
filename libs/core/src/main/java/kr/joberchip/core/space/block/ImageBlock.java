package kr.joberchip.core.space.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joberchip.core.storage.ImageBlockFile;
import kr.joberchip.core.space.BaseObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "image_link")
  private String imageLink;

  @OneToOne(mappedBy = "imageBlock")
  private ImageBlockFile imageBlockFile;
}
