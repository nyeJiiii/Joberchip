package kr.joberchip.core.space.block;

import javax.persistence.*;
import kr.joberchip.core.file.VideoBlockFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "video_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoBlock extends BaseBlock {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "video_link")
  private String videoLink;

  @OneToOne
  @JoinColumn(name = "video_file_id")
  private VideoBlockFile videoFile;
}
