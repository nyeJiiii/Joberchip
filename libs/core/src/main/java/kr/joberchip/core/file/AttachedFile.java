package kr.joberchip.core.file;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/** 첨부파일 테이블 엔티티 */
@Entity
@Table(name = "attached_file_tb")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AttachedFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "file_id")
  private Long fileId;

  @Column(name = "content_type")
  private String contentType;

  @Column(name = "save_path")
  private String savePath;

  @OneToOne(mappedBy = "attachedFile")
  private VideoBlockFile videoFile;

  @OneToOne(mappedBy = "attachedFile")
  private ImageBlockFile imageBlockFile;

  @OneToOne(mappedBy = "attachedFile")
  private ProfileImageFile profileFile;

  private AttachedFile(Long fileId, String contentType, String savePath) {
    this.fileId = fileId;
    this.contentType = contentType;
    this.savePath = savePath;
  }

  private AttachedFile(String contentType, String savePath) {
    this.contentType = contentType;
    this.savePath = savePath;
  }

  public static AttachedFile of(String contentType, String savePath) {
    return new AttachedFile(contentType, savePath);
  }

  public static AttachedFile of(Long fileId, String contentType, String savePath) {
    return new AttachedFile(fileId, contentType, savePath);
  }

  public void changePath(String newPath) {
    this.savePath = newPath;
  }
}
