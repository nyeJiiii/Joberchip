package kr.joberchip.core.space.page;

import javax.persistence.*;

@Entity
@Table(name = "page_hashtag")
public class PageHashtag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "page_hashtag_id")
  private Long pageHashtagId;

  @ManyToOne(targetEntity = SpacePage.class)
  @JoinColumn(name = "page_id")
  private SpacePage spacePage;

  @ManyToOne(targetEntity = Hashtag.class)
  @JoinColumn(name = "hashtag_id")
  private Hashtag hashtag;
}
