package kr.joberchip.core.share.page;

import javax.persistence.*;

@Entity
@Table(name = "page_hashtag")
public class PageHashtag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "page_hashtag_id")
  private Long pageHashtagId;

  @ManyToOne(targetEntity = SharePage.class)
  @JoinColumn(name = "page_id")
  private SharePage sharePage;

  @ManyToOne(targetEntity = Hashtag.class)
  @JoinColumn(name = "hashtag_id")
  private Hashtag hashtag;
}
