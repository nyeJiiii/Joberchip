package kr.joberchip.core.storage;

import javax.persistence.*;
import kr.joberchip.core.share.block.VideoBlock;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "video_file_tb")
@Getter
@Setter
public class VideoBlockFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "video_file_id")
  private Long videoFileId;

  @OneToOne private VideoBlock videoBlock;

  @OneToOne private AttachedFile attachedFile;
}
