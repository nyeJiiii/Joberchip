package kr.joberchip.core.file;

import javax.persistence.*;
import kr.joberchip.core.space.page.SpacePageProfile;

@Entity
@Table(name = "profile_image_file_tb")
public class ProfileImageFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "profile_image_file_id")
  private Long profileImageFileId;

  @OneToOne(targetEntity = SpacePageProfile.class)
  @JoinColumn(name = "profile_id")
  private SpacePageProfile pageProfile;

  @OneToOne(targetEntity = AttachedFile.class)
  @JoinColumn(name = "file_id")
  private AttachedFile attachedFile;
}
