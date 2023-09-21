package kr.joberchip.core.space.page;

import java.util.*;
import javax.persistence.*;
import kr.joberchip.core.space.BaseObject;
import kr.joberchip.core.space.block.ImageBlock;
import kr.joberchip.core.space.block.LinkBlock;
import kr.joberchip.core.space.block.TextBlock;
import kr.joberchip.core.space.block.VideoBlock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "space_page")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class SpacePage extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "parentPageId", cascade = CascadeType.ALL)
  private Set<SpacePage> childPages = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentPageId")
  private List<TextBlock> textBlocks;

  @OneToMany(mappedBy = "parentPageId")
  private List<LinkBlock> linkBlocks;

  @OneToMany(mappedBy = "parentPageId")
  private List<ImageBlock> imageBlocks;

  @OneToMany(mappedBy = "parentPageId")
  private List<VideoBlock> videoBlocks;

  @OneToMany(mappedBy = "spacePage")
  private Set<PageHashtag> hashtags = new LinkedHashSet<>();

  @OneToOne(mappedBy = "spacePage")
  private SpacePageProfile spacePageProfile;

  public static SpacePage of(String title, String description) {
    return new SpacePage(title, description, null, null, null, null, null, null, null);
  }

  public UUID getPageId() {
    return this.objectId;
  }
}
