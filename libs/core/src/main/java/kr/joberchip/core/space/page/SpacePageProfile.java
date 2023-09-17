package kr.joberchip.core.space.page;

import javax.persistence.*;
import kr.joberchip.core.file.ProfileImageFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "space_page_profile_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SpacePageProfile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "space_profile_id")
  private Long spaceProfileId;

  @OneToOne private SpacePage spacePage;

  @Column(name = "profile_name")
  private String profileName;

  @Column(name = "description")
  private String description;

  @OneToOne(mappedBy = "pageProfile")
  private ProfileImageFile profileFile;
}
