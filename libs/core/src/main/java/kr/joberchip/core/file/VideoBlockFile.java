package kr.joberchip.core.file;

import javax.persistence.*;
import kr.joberchip.core.space.block.VideoBlock;

@Entity
@Table(name = "video_file_tb")
public class VideoBlockFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long videoFileId;

  @OneToOne
  @JoinColumn(name = "block_id")
  private VideoBlock videoBlock;

  @OneToOne
  @JoinColumn(name = "file_id")
  private AttachedFile attachedFile;
}
