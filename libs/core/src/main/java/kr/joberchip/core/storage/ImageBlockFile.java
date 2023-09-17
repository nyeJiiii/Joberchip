package kr.joberchip.core.storage;

import javax.persistence.*;
import kr.joberchip.core.space.block.ImageBlock;

@Entity
@Table(name = "image_file_tb")
public class ImageBlockFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="image_file_id")
  private Long imageFileId;

  @OneToOne private ImageBlock imageBlock;

  @OneToOne private AttachedFile attachedFile;
}
