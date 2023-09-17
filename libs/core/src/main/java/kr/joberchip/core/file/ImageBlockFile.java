package kr.joberchip.core.file;

import javax.persistence.*;
import kr.joberchip.core.space.block.ImageBlock;

@Entity
@Table(name = "image_file_tb")
public class ImageBlockFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long imageFileId;

  @OneToOne
  @JoinColumn(name = "block_id")
  private ImageBlock imageBlock;

  @OneToOne
  @JoinColumn(name = "file_id")
  private AttachedFile attachedFile;
}
