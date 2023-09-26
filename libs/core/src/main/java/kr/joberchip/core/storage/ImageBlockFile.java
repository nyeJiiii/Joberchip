package kr.joberchip.core.storage;

import javax.persistence.*;
import kr.joberchip.core.share.block.ImageBlock;
import lombok.Getter;

@Entity
@Getter
@Table(name = "image_file_tb")
public class ImageBlockFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_file_id")
  private Long imageFileId;

  @OneToOne private ImageBlock imageBlock;

  @OneToOne private AttachedFile attachedFile;
}
