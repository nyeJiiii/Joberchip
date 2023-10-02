package kr.joberchip.core.page;

import javax.persistence.*;
import kr.joberchip.core.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "page_tag")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Hashtag extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "hashtag_id")
  private Long hashtagId;

  @Column(name = "name")
  private String name;
}
