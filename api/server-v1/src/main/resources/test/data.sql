/*
  user_tb dummy
  password = '0000'
*/
insert into user_tb (username, password, user_roles)
values ('user1', '{bcrypt}$2a$10$B9YJT9AtusUZJAnmgznu1OX1kI.8TDizhfCPYCUQMOWat6K1s4oM.', 'ROLE_USER');
insert into user_tb (username, password, user_roles)
values ('user2', '{bcrypt}$2a$10$s0CMkutBLP.k5mz4rI5T/e0tOj9WWUDXBTYqsM4AQ3NS/30OphvIa', 'ROLE_USER');
insert into user_tb (username, password, user_roles)
values ('user3', '{bcrypt}$2a$10$85RcQmwBZQNLvI31sbGiN.KbtDIGxFnBuKkRNXEbFh6PpMdv8vY3.', 'ROLE_USER');
insert into user_tb (username, password, user_roles)
values ('user4', '{bcrypt}$2a$10$TU81VtR4POmoLoHyZVJCme5NwNEMfHKYZmKpiJdqBRNdF0qVzRwUS', 'ROLE_USER');
insert into user_tb (username, password, user_roles)
values ('user5', '{bcrypt}$2a$10$YC.lqsJc6stMpZu.Hzfctu0oEPCrRZmJsZ7ua8He6r6RbN2i4jUZO', 'ROLE_USER');


/*
    사용자 user1 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET @user1_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by , created_at, modified_at, modified_by)
values (@user1_space, 1, 'user1', '2023-09-26', '2023-09-26', 'user1');

SET @user1_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width, visible, title, description)
values (@user1_page1, 'user1', '2023-09-26', '2023-09-26', 'user1', 0, 0, 1, 2, 1, "[user1]<1>제목입니다.", "[user1]<1>설명입니다.");

UPDATE space_tb SET page_id =  @user1_page1 WHERE space_id = @user1_space;
UPDATE share_page_tb SET parent_object_id =  @user1_space WHERE object_id = @user1_page1;

/*
    사용자 user2 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET @user2_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by , created_at, modified_at, modified_by)
values (@user2_space, 2, 'user2', '2023-09-26', '2023-09-26', 'user2');

SET @user2_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width, visible, title, description)
values (@user2_page1, 'user2', '2023-09-26', '2023-09-26', 'user2', 0, 0, 1, 2, 1, "[user2]<1>제목입니다.", "[user2]<1>설명입니다.");

UPDATE space_tb SET page_id =  @user2_page1 WHERE space_id = @user2_space;
UPDATE share_page_tb SET parent_object_id =  @user1_space WHERE object_id = @user2_page1;

/*
    사용자 user3 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET @user3_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by , created_at, modified_at, modified_by)
values (@user3_space, 3, 'user3', '2023-09-26', '2023-09-26', 'user3');

SET @user3_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width, visible, title, description)
values (@user3_page1, 'user3', '2023-09-26', '2023-09-26', 'user3', 0, 0, 1, 2, 1, "[user3]<1>제목입니다.", "[user3]<1>설명입니다.");

UPDATE space_tb SET page_id =  @user3_page1 WHERE space_id = @user3_space;
UPDATE share_page_tb SET parent_object_id =  @user3_space WHERE object_id = @user3_page1;

/*
    사용자 user4 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET @user4_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by , created_at, modified_at, modified_by)
values (@user4_space, 4, 'user4', '2023-09-26', '2023-09-26', 'user4');

SET @user4_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width, visible, title, description)
values (@user4_page1, 'user4', '2023-09-26', '2023-09-26', 'user4', 0, 0, 1, 2, 1, "[user4]<1>제목입니다.", "[user4]<1>설명입니다.");

UPDATE space_tb SET page_id =  @user4_page1 WHERE space_id = @user4_space;
UPDATE share_page_tb SET parent_object_id =  @user4_space WHERE object_id = @user4_page1;

/*
    사용자 user5 의 space 생성
    해당 space내의 최상위 sharepage 생성
*/
SET @user5_space = UUID_TO_BIN(UUID(), TRUE);
insert into space_tb (space_id, creator_id, created_by , created_at, modified_at, modified_by)
values (@user5_space, 5, 'user5', '2023-09-26', '2023-09-26', 'user5');

SET @user5_page1 = UUID_TO_BIN(UUID(), TRUE);
insert into share_page_tb (object_id, created_by, created_at, modified_at, modified_by, x_pos, y_pos, height, width, visible, title, description)
values (@user5_page1, 'user5', '2023-09-26', '2023-09-26', 'user5', 0, 0, 1, 2, 1, "[user5]<1>제목입니다.", "[user5]<1>설명입니다.");

UPDATE space_tb SET page_id =  @user5_page1 WHERE space_id = @user5_space;
UPDATE share_page_tb SET parent_object_id =  @user5_space WHERE object_id = @user5_page1;
