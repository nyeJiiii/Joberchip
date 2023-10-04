package kr.joberchip.core.page;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import kr.joberchip.core.BaseObject;
import kr.joberchip.core.block.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "share_page_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class SharePage extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "profile_image_link")
  @Lob
  private String profileImageLink;

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<SharePage> childPages = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<TextBlock> textBlocks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<TemplateBlock> templateBlocks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<LinkBlock> linkBlocks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<ImageBlock> imageBlocks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<VideoBlock> videoBlocks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parentObjectId", cascade = CascadeType.ALL)
  private Set<MapBlock> mapBlocks = new LinkedHashSet<>();

  @OneToMany(mappedBy = "sharePage")
  private Set<PageHashtag> hashtags = new LinkedHashSet<>();

  private SharePage(String title, String description) {
    this.title = title;
    this.description = description;
    this.profileImageLink =
        "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png";
  }

  public static SharePage of(String title, String description) {
    return new SharePage(title, description);
  }

  public static SharePage of(UUID parentObjectId, String title, String description) {
    SharePage generated = SharePage.of(title, description);
    generated.setParentObjectId(parentObjectId);
    return generated;
  }

  public void addTextBlock(TextBlock textBlock) {
    textBlock.setParentObjectId(this.objectId);
    this.getTextBlocks().add(textBlock);
  }

  public void addTemplateBlock(TemplateBlock templateBlock) {
    templateBlock.setParentObjectId(this.objectId);
    this.getTemplateBlocks().add(templateBlock);
  }

  public void addLinkBlock(LinkBlock linkBlock) {
    linkBlock.setParentObjectId(this.objectId);
    this.getLinkBlocks().add(linkBlock);
  }

  public void addImageBlock(ImageBlock imageBlock) {
    imageBlock.setParentObjectId(this.objectId);
    this.getImageBlocks().add(imageBlock);
  }

  public void addVideoBlock(VideoBlock videoBlock) {
    videoBlock.setParentObjectId(this.objectId);
    this.getVideoBlocks().add(videoBlock);
  }

  public void addMapBlock(MapBlock mapBlock) {
    mapBlock.setParentObjectId(this.objectId);
    this.getMapBlocks().add(mapBlock);
  }

  public void addChildPage(SharePage sharePage) {
    sharePage.setParentObjectId(this.objectId);
    this.getChildPages().add(sharePage);
  }

  @Override
  public String toString() {
    return "\nSharePage {\n"
        + "title : '"
        + title
        + '\''
        + ",\n description : '"
        + description
        + '\''
        + ",\n profileImageLink : '"
        + profileImageLink
        + '\''
        + ",\n childPages : "
        + childPages
        + ",\n textBlocks : "
        + textBlocks
        + ",\n templateBlocks : "
        + templateBlocks
        + ",\n linkBlocks : "
        + linkBlocks
        + ",\n imageBlocks : "
        + imageBlocks
        + ",\n videoBlocks : "
        + videoBlocks
        + ",\n mapBlocks : "
        + mapBlocks
        + ",\n hashtags : "
        + hashtags
        + "\n}\n";
  }
}
