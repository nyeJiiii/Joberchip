package kr.joberchip.core.space.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import kr.joberchip.core.space.BaseObject;

@Entity
@Table(name = "link_block_tb")
public class LinkBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "link")
  private String link;
}
