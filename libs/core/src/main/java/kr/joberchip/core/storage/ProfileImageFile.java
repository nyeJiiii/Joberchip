package kr.joberchip.core.storage;

import javax.persistence.*;
import kr.joberchip.core.space.page.SpacePageProfile;

@Entity
@Table(name = "profile_image_file_tb")
public class ProfileImageFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "profile_image_file_id")
  private Long profileImageFileId;

  @OneToOne private SpacePageProfile pageProfile;

  @OneToOne private AttachedFile attachedFile;
}
