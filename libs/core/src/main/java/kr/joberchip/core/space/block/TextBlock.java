package kr.joberchip.core.space.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "text_block_tb")
public class TextBlock extends BaseBlock {
  @Lob
  @Column(name = "content")
  private String content;
}
