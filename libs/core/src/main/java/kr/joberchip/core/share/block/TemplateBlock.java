package kr.joberchip.core.share.block;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import kr.joberchip.core.share.BaseObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TemplateBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  public static TemplateBlock of(String title, String description) {
    return new TemplateBlock(title, description);
  }

  public void modifyTitle(String title) {
    this.title = title;
  }

  public void modifyDescription(String description) {
    this.description = description;
  }

  public UUID getTemplateBlockId() {
    return this.objectId;
  }
}
