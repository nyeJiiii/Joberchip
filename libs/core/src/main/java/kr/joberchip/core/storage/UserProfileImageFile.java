package kr.joberchip.core.storage;

import kr.joberchip.core.user.User;
import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "user_profile_image_file_tb")
public class UserProfileImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_image_file_id")
    private Long UserProfileImageFileId;

    @OneToOne private User user;

    @OneToOne private AttachedFile attachedFile;
}
