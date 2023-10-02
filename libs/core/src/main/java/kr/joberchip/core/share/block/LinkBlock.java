package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import kr.joberchip.core.share.BaseObject;
import lombok.*;

@Entity
@Table(name = "link_block_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class LinkBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "link")
  @Lob
  private String link;

  public static LinkBlock of(String title, String link) {
    return new LinkBlock(title, link);
  }

  public void modifyTitle(String title) {
    this.title = title;
  }

  public void modifyLink(String link) {
    this.link = link;
  }

  public UUID getLinkBlockId() {
    return this.objectId;
  }
}
