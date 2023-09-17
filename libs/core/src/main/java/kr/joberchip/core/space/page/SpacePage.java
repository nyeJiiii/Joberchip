package kr.joberchip.core.space.page;

import java.util.*;
import javax.persistence.*;
import kr.joberchip.core.space.BaseBlock;
import kr.joberchip.core.space.block.ImageBlock;
import kr.joberchip.core.space.block.LinkBlock;
import kr.joberchip.core.space.block.TextBlock;
import kr.joberchip.core.space.block.VideoBlock;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "space_page")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SpacePage extends BaseBlock {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "parentPage", cascade = CascadeType.ALL)
  private Set<SpacePage> childPages = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentPage")
  private List<TextBlock> textBlocks;

  @OneToMany(mappedBy = "parentPage")
  private List<LinkBlock> linkBlocks;

  @OneToMany(mappedBy = "parentPage")
  private List<ImageBlock> imageBlocks;

  @OneToMany(mappedBy = "parentPage")
  private List<VideoBlock> videoBlocks;

  @OneToMany(mappedBy = "spacePage")
  private Set<PageHashtag> hashtags = new LinkedHashSet<>();

  @OneToOne(mappedBy = "spacePage")
  private SpacePageProfile spacePageProfile;
}
