package kr.joberchip.core.storage;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 첨부파일 테이블 엔티티 */
@Entity
@Table(name = "attached_file_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class AttachedFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attached_file_id")
  private Long attachedFileId;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "stored_name")
  private String storedName;

  @OneToOne(mappedBy = "attachedFile")
  private VideoBlockFile videoFile;

  @OneToOne(mappedBy = "attachedFile")
  private ImageBlockFile imageBlockFile;

  @OneToOne(mappedBy = "attachedFile")
  private ProfileImageFile profileImageFile;

  @OneToOne(mappedBy = "attachedFile")
  private UserProfileImageFile userProfileImageFile;

  private AttachedFile(String contentType, String storedName) {
    this.contentType = contentType;
    this.storedName = storedName;
  }

  public static AttachedFile of(String contentType, String storedName) {
    return new AttachedFile(contentType, storedName);
  }
}
