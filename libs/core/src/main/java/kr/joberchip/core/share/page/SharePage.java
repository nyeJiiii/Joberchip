package kr.joberchip.core.share.page;

import kr.joberchip.core.share.BaseObject;
import kr.joberchip.core.share.block.*;
import kr.joberchip.core.storage.ProfileImageFile;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

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

  @OneToMany(mappedBy = "sharePage")
  private Set<PageHashtag> hashtags = new LinkedHashSet<>();

  @OneToOne(mappedBy = "sharePage", cascade = CascadeType.ALL)
  private ProfileImageFile profileImageFile;

  private SharePage(String title, String description) {
    this.title = title;
    this.description = description;
  }

  private SharePage(UUID parentObjectId, String title, String description) {
    this.parentObjectId = parentObjectId;
    this.title = title;
    this.description = description;
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
    textBlock.setParentObjectId(this.getPageId());
    this.getTextBlocks().add(textBlock);
  }

  public void addTemplateBlock(TemplateBlock templateBlock) {
    templateBlock.setParentObjectId(this.getPageId());
    this.getTemplateBlocks().add(templateBlock);
  }

  public void addLinkBlock(LinkBlock linkBlock) {
    linkBlock.setParentObjectId(this.getPageId());
    this.getLinkBlocks().add(linkBlock);
  }

  public void addImageBlock(ImageBlock imageBlock) {
    imageBlock.setParentObjectId(this.getPageId());
    this.getImageBlocks().add(imageBlock);
  }

  public void addVideoBlock(VideoBlock videoBlock) {
    videoBlock.setParentObjectId(this.getPageId());
    this.getVideoBlocks().add(videoBlock);
  }

  public void addChildPage(SharePage sharePage) {
    sharePage.setParentObjectId(this.getPageId());
    this.getChildPages().add(sharePage);
  }

  public UUID getPageId() {
    return this.objectId;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "\n{\n"
        + "\ttitle = "
        + title
        + ",\n"
        + "\tdescription = "
        + description
        + ",\n"
        + "\tprofileImageFile = "
        + profileImageFile
        + "\n}\n";
  }
}
