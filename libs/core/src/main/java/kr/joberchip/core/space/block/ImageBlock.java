package kr.joberchip.core.space.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joberchip.core.file.ImageBlockFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageBlock extends BaseBlock {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "image_link")
  private String imageLink;

  @OneToOne(mappedBy = "imageBlock")
  private ImageBlockFile imageBlockFile;
}
