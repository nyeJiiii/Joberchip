package kr.joberchip.core.space.block;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import kr.joberchip.core.space.BaseObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template_block_tb")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateBlock extends BaseObject {
  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;
}
