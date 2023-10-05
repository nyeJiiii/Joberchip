/*
  user_tb dummy
  password = '0000'
*/
insert into user_tb (username, password, nickname, user_roles)
values ('user1', '{bcrypt}$2a$10$B9YJT9AtusUZJAnmgznu1OX1kI.8TDizhfCPYCUQMOWat6K1s4oM.', 'user1', 'ROLE_USER');
insert into user_tb (username, password, nickname, user_roles)
values ('user2', '{bcrypt}$2a$10$s0CMkutBLP.k5mz4rI5T/e0tOj9WWUDXBTYqsM4AQ3NS/30OphvIa', 'user2', 'ROLE_USER');
insert into user_tb (username, password, nickname, user_roles)
values ('user3', '{bcrypt}$2a$10$85RcQmwBZQNLvI31sbGiN.KbtDIGxFnBuKkRNXEbFh6PpMdv8vY3.', 'user3', 'ROLE_USER');
insert into user_tb (username, password, nickname, user_roles)
values ('user4', '{bcrypt}$2a$10$TU81VtR4POmoLoHyZVJCme5NwNEMfHKYZmKpiJdqBRNdF0qVzRwUS', 'user4', 'ROLE_USER');
insert into user_tb (username, password, nickname, user_roles)
values ('user5', '{bcrypt}$2a$10$YC.lqsJc6stMpZu.Hzfctu0oEPCrRZmJsZ7ua8He6r6RbN2i4jUZO', 'user5', 'ROLE_USER');

set
@user1_id = (select user_id from user_tb where username='user1');
set
@user2_id = (select user_id from user_tb where username='user2');
set
@user3_id = (select user_id from user_tb where username='user3');
set
@user4_id = (select user_id from user_tb where username='user4');
set
@user5_id = (select user_id from user_tb where username='user5');


/*
    사용자 user1 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET
@user1_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by, created_at, modified_at, modified_by)
values (@user1_space, 1, 'user1', '2023-09-26', '2023-09-26', 'user1');

/*
    사용자 user1 의 스페이스 참여 정보 등록 (기본 스페이스 default)
 */
insert into space_participation_info_tb (participation_type, space_id, user_id)
values ('DEFAULT', @user1_space, 1);


SET
@user1_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, description, profile_image_link)
values (@user1_page1, 'user1', '2023-09-26', '2023-09-26', 'user1', 0, 0, 1, 2, @user1_space, 1, "[user1]<1>제목입니다.",
        "[user1]<1>설명입니다.", 'https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png');

UPDATE space_tb
SET page_id = @user1_page1
WHERE space_id = @user1_space;


insert into share_page_privilege_tb (user_id, share_page_id, privilege_type)
values (@user1_id, @user1_page1, 'EDIT');


/*
    user1 의 최상위 sharePage에 텍스트블록, 링크블록, 지도블록, 페이지 생성
*/
SET
@user1_page1_text = UUID_TO_BIN(UUID(), TRUE);
insert into text_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                           parent_object_id, visible, content)
values (@user1_page1_text, 'user1', '2023-09-26', 'user1', '2023-09-26', 0, 1, 2, 2, @user1_page1, 1,
        "“{\“blocks\“:[{\“key\“:\“c3r5h\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“center\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:3,\“style\“:\“size24\“},{\“offset\“:0,\“length\“:2,\“style\“:\“red\“},{\“offset\“:6,\“length\“:2,\“style\“:\“UNDERLINE\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“5lp3j\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:2,\“style\“:\“STRIKETHROUGH\“},{\“offset\“:3,\“length\“:5,\“style\“:\“font3\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“6sut3\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“right\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:1,\“style\“:\“BOLD\“},{\“offset\“:1,\“length\“:1,\“style\“:\“ITALIC\“},{\“offset\“:5,\“length\“:1,\“style\“:\“size30\“},{\“offset\“:5,\“length\“:1,\“style\“:\“greenBg\“},{\“offset\“:5,\“length\“:1,\“style\“:\“white\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“d0c5c\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“cg06b\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“BOLD\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“bipql\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“orange\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“3rgkh\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“purple\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“f58pm\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“blue\“}],\“entityRanges\“:[],\“data\“:{}}],\“entityMap\“:{}}”");

SET
@user1_page1_link = UUID_TO_BIN(UUID(), TRUE);
insert into link_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, link)
values (@user1_page1_link, 'user1', '2023-09-26', 'user1', '2023-09-26', 0, 3, 1, 1, @user1_page1, 1,
        "[user1]<1>링크블록타이틀", "www.naver.com");

SET
@user1_page1_map = UUID_TO_BIN(UUID(), TRUE);
insert into map_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                          parent_object_id, visible, address, latitude, longitude)
values (@user1_page1_map, 'user1', '2023-09-26', 'user1', '2023-09-26', 1, 3, 1, 1, @user1_page1, 1, "미왕빌딩", 37.4955366,
        127.0293521);

SET
@user1_page2 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, description, profile_image_link)
values (@user1_page2, 'user1', '2023-09-26', '2023-09-26', 'user1', 0, 4, 1, 2, @user1_page1, 1, "[user1]<2>제목입니다.",
        "[user1]<2>설명입니다.", 'https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png');

insert into share_page_privilege_tb (user_id, share_page_id, privilege_type)
values (@user1_id, @user1_page2, 'EDIT');

/*
    user1 의 depth1 sharePage (@user1_page2)에 텍스트블록, 페이지, 지도블록 생성
*/
SET
@user1_page2_text = UUID_TO_BIN(UUID(), TRUE);
insert into text_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                           parent_object_id, visible, content)
values (@user1_page2_text, 'user1', '2023-09-26', 'user1', '2023-09-26', 0, 0, 1, 1, @user1_page2, 1,
        "“{\“blocks\“:[{\“key\“:\“c3r5h\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“center\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:3,\“style\“:\“size24\“},{\“offset\“:0,\“length\“:2,\“style\“:\“red\“},{\“offset\“:6,\“length\“:2,\“style\“:\“UNDERLINE\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“5lp3j\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:2,\“style\“:\“STRIKETHROUGH\“},{\“offset\“:3,\“length\“:5,\“style\“:\“font3\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“6sut3\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“right\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:1,\“style\“:\“BOLD\“},{\“offset\“:1,\“length\“:1,\“style\“:\“ITALIC\“},{\“offset\“:5,\“length\“:1,\“style\“:\“size30\“},{\“offset\“:5,\“length\“:1,\“style\“:\“greenBg\“},{\“offset\“:5,\“length\“:1,\“style\“:\“white\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“d0c5c\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“cg06b\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“BOLD\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“bipql\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“orange\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“3rgkh\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“purple\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“f58pm\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“blue\“}],\“entityRanges\“:[],\“data\“:{}}],\“entityMap\“:{}}”");


SET
@user1_page3 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, description, profile_image_link)
values (@user1_page3, 'user1', '2023-09-26', '2023-09-26', 'user1', 1, 0, 1, 1, @user1_page2, 1, "[user1]<3>제목입니다.",
        "[user1]<3>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");

insert into share_page_privilege_tb (user_id, share_page_id, privilege_type)
values (@user1_id, @user1_page3, 'EDIT');

SET
@user1_page2_map = UUID_TO_BIN(UUID(), TRUE);
insert into map_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                          parent_object_id, visible, address, latitude, longitude)

values (@user1_page2_map, 'user1', '2023-09-26', 'user1', '2023-09-26', 0, 1, 2, 2, @user1_page2, 1, "반장떡볶이",
        37.4967914, 127.030317);

SET
@user1_page4 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, description, profile_image_link)
values (@user1_page4, 'user1', '2023-09-26', '2023-09-26', 'user1', 0, 3, 1, 2, @user1_page2, 1, "[user1]<4>제목입니다.",
        "[user1]<4>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");

insert into share_page_privilege_tb (user_id, share_page_id, privilege_type)
values (@user1_id, @user1_page4, 'EDIT');

/*
    user1 의 depth2 sharePage (@user1_page3)에 링크블록 생성
*/
SET
@user1_page3_link = UUID_TO_BIN(UUID(), TRUE);
insert into link_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, link)
values (@user1_page3_link, 'user1', '2023-09-26', 'user1', '2023-09-26', 0, 0, 2, 2, @user1_page3, 1,
        "[user1]<3>링크블록타이틀", "www.youtube.com");


/*
    사용자 user2 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET
@user2_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by, created_at, modified_at, modified_by)
values (@user2_space, 2, 'user2', '2023-09-26', '2023-09-26', 'user2');

insert into space_participation_info_tb (participation_type, space_id, user_id)
values ('DEFAULT', @user2_space, 2),
       ('PARTICIPANT', @user1_space, 2);

SET
@user2_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           visible, title, description, profile_image_link)
values (@user2_page1, 'user2', '2023-09-26', '2023-09-26', 'user2', 0, 0, 1, 2, 1, "[user2]<1>제목입니다.",
        "[user2]<1>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");

UPDATE space_tb
SET page_id = @user2_page1
WHERE space_id = @user2_space;
UPDATE share_page_tb
SET parent_object_id = @user1_space
WHERE object_id = @user2_page1;


/*
    user2 의 최상위 sharePage에 텍스트블록, 링크블록, 지도블록, 페이지 생성
*/
SET
@user2_page1_link = UUID_TO_BIN(UUID(), TRUE);
insert into link_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, link)
values (@user2_page1_link, 'user2', '2023-09-26', 'user2', '2023-09-26', 0, 0, 1, 2, @user2_page1, 1,
        "[user2]<1>링크블록타이틀", "www.naver.com");

SET
@user2_page1_map = UUID_TO_BIN(UUID(), TRUE);
insert into map_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                          parent_object_id, visible, address, latitude, longitude)
values (@user2_page1_map, 'user2', '2023-09-26', 'user2', '2023-09-26', 0, 1, 1, 2, @user2_page1, 1, "산성역", 37.457001,
        127.150027);

SET
@user2_page1_text = UUID_TO_BIN(UUID(), TRUE);
insert into text_block_tb (object_id, created_by, created_at, modified_by, modified_at, x_pos, y_pos, height, width,
                           parent_object_id, visible, content)
values (@user2_page1_text, 'user2', '2023-09-26', 'user2', '2023-09-26', 0, 2, 1, 2, @user2_page1, 1,
        "“{\“blocks\“:[{\“key\“:\“c3r5h\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“center\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:3,\“style\“:\“size24\“},{\“offset\“:0,\“length\“:2,\“style\“:\“red\“},{\“offset\“:6,\“length\“:2,\“style\“:\“UNDERLINE\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“5lp3j\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:2,\“style\“:\“STRIKETHROUGH\“},{\“offset\“:3,\“length\“:5,\“style\“:\“font3\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“6sut3\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“right\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:1,\“style\“:\“BOLD\“},{\“offset\“:1,\“length\“:1,\“style\“:\“ITALIC\“},{\“offset\“:5,\“length\“:1,\“style\“:\“size30\“},{\“offset\“:5,\“length\“:1,\“style\“:\“greenBg\“},{\“offset\“:5,\“length\“:1,\“style\“:\“white\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“d0c5c\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“cg06b\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“BOLD\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“bipql\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“orange\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“3rgkh\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“purple\“}],\“entityRanges\“:[],\“data\“:{}},{\“key\“:\“f58pm\“,\“text\“:\“자버칩프라푸치노\“,\“type\“:\“unstyled\“,\“depth\“:0,\“inlineStyleRanges\“:[{\“offset\“:0,\“length\“:8,\“style\“:\“blue\“}],\“entityRanges\“:[],\“data\“:{}}],\“entityMap\“:{}}”");

SET
@user2_page2 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           parent_object_id, visible, title, description, profile_image_link)
values (@user2_page2, 'user2', '2023-09-26', '2023-09-26', 'user2', 0, 4, 1, 2, @user2_page1, 1, "[user2]<2>제목입니다.",
        "[user1]<2>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");


/*
    사용자 user3 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET
@user3_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by, created_at, modified_at, modified_by)
values (@user3_space, 3, 'user3', '2023-09-26', '2023-09-26', 'user3');

SET
@user3_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           visible, title, description, profile_image_link)
values (@user3_page1, 'user3', '2023-09-26', '2023-09-26', 'user3', 0, 0, 1, 2, 1, "[user3]<1>제목입니다.",
        "[user3]<1>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");

UPDATE space_tb
SET page_id = @user3_page1
WHERE space_id = @user3_space;
UPDATE share_page_tb
SET parent_object_id = @user3_space
WHERE object_id = @user3_page1;


/*
    사용자 user4 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET
@user4_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by, created_at, modified_at, modified_by)
values (@user4_space, 4, 'user4', '2023-09-26', '2023-09-26', 'user4');

SET
@user4_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           visible, title, description, profile_image_link)
values (@user4_page1, 'user4', '2023-09-26', '2023-09-26', 'user4', 0, 0, 1, 2, 1, "[user4]<1>제목입니다.",
        "[user4]<1>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");

UPDATE space_tb
SET page_id = @user4_page1
WHERE space_id = @user4_space;
UPDATE share_page_tb
SET parent_object_id = @user4_space
WHERE object_id = @user4_page1;


/*
    사용자 user5 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET
@user5_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by, created_at, modified_at, modified_by)
values (@user5_space, 5, 'user5', '2023-09-26', '2023-09-26', 'user5');

SET
@user5_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width,
                           visible, title, description, profile_image_link)
values (@user5_page1, 'user5', '2023-09-26', '2023-09-26', 'user5', 0, 0, 1, 2, 1, "[user5]<1>제목입니다.",
        "[user5]<1>설명입니다.", "https://joberchip-s3.s3.ap-northeast-2.amazonaws.com/default_profile.png");

UPDATE space_tb
SET page_id = @user5_page1
WHERE space_id = @user5_space;
UPDATE share_page_tb
SET parent_object_id = @user5_space
WHERE object_id = @user5_page1;


