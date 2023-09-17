package kr.joberchip.core.space.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import kr.joberchip.core.space.BaseObject;

@Entity
@Table(name = "text_block_tb")
public class TextBlock extends BaseObject {
  @Lob
  @Column(name = "content")
  private String content;
}
