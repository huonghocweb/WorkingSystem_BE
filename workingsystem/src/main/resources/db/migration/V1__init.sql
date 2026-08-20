create table users
(
	user_id int primary key identity(1,1) ,
	user_name nvarchar(100) ,
	first_name nvarchar(100),
	last_name nvarchar(100) ,
	password nvarchar(max) ,
	birth_day date ,
	phone_number nvarchar(100) unique,
	address nvarchar(100) ,
	image_public_id nvarchar(max) ,
	gender bit ,
	email nvarchar(100),
    status nvarchar(50) DEFAULT 'ACTIVE',
    is_online BIT default 0 ,
    last_login_at datetime ,
    last_active_at datetime ,
    create_at datetime default getDate() ,
    update_at  datetime,
	CONSTRAINT UQ_USERS_EMAIL UNIQUE(email) ,
	CONSTRAINT UQ_USERS_PHONENUMBER UNIQUE(phone_number)
)

insert into  users
   ( user_name, first_name, last_name, password, birth_day, phone_number,   address, image_public_id, gender, email,
    status, is_online, last_login_at, last_active_at, update_at) values

(N'huongpham', N'Hưởng', N'Phạm Văn', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '2000-12-12', N'0128283692', N'11 Võ Văn tần , Quận 3', N'user/xrivhibyw8fjambgmyqu', 0, N'huongpham12@gmail.com',
 'ACTIVE', 1, '2026-07-10 15:30:00', '2026-07-10 17:15:00', '2026-07-10 17:15:00'),
(N'linhpham', N'Linh', N'Phạm Thị Thùy', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '2001-02-12', N'0903239422', N'99 Cộng Hòa , Tan Binh', N'user/x8tlvv6zp1lyilx5rlrt', 1, N'thuylinh199@gmail.com',
 'ACTIVE', 1, '2026-07-10 16:00:00', '2026-07-10 17:20:00', '2026-07-10 17:20:00'),
(N'trungtran', N'Trung', N'Trần Đức', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '1999-10-02', N'0989892112', N'123 Cách Mạng Tháng 8, Quận 10', N'user/wccckiuoxvyvovwpm6pl', 1, N'trungduc09@gmail.com',
 'ACTIVE', 0, '2026-07-09 09:15:00', '2026-07-09 10:30:00', '2026-07-09 10:30:00'),
(N'minhhoang', N'Hoàng', N'Nguyễn Minh', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '1998-05-20', N'0901234567', N'45 Lê Lợi, Quận 1', N'image_url_4', 1, N'hoangminh@gmail.com',
 'ACTIVE', 1, '2026-07-10 14:00:00', '2026-07-10 17:22:00', '2026-07-10 17:22:00'),
(N'lananh', N'Anh', N'Trương Lan', '123', '2002-08-15', N'0912345678', N'120 Hai Bà Trưng, Quận 3', N'', 0, N'lananh88@gmail.com',
 'ACTIVE', 0, '2026-07-10 08:00:00', '2026-07-10 08:45:00', '2026-07-10 08:45:00'),
(N'thanhnam', N'Nam', N'Vũ Thành', '123', '1997-11-30', N'0923456789', N'789 Điện Biên Phủ, Bình Thạnh', N'', 1, N'namthanh@gmail.com',
 'BANNED', 0, '2026-06-30 11:00:00', '2026-06-30 11:15:00', '2026-07-10 10:00:00'),
(N'bichngoc', N'Ngọc', N'Đỗ Bích', '123', '2001-03-25', N'0934567890', N'12 Phan Xích Long, Phú Nhuận', N'', 0, N'ngocbich@gmail.com',
 'ACTIVE', 1, '2026-07-10 17:00:00', '2026-07-10 17:23:00', '2026-07-10 17:23:00'),
(N'quanghuy', N'Huy', N'Lê Quang', '123', '2000-01-10', N'0945678901', N'56 Nguyễn Trãi, Quận 5', N'', 1, N'huyquang@gmail.com',
 'ACTIVE', 0, '2026-07-05 20:00:00', '2026-07-05 22:10:00', '2026-07-05 22:10:00'),
(N'thuyduong', N'Dương', N'Hoàng Thùy', '123', '2003-07-04', N'0956789012', N'234 Trần Hưng Đạo, Quận 1', N'', 0, N'duongthuy@gmail.com',
 'ACTIVE', 1, '2026-07-10 16:45:00', '2026-07-10 17:19:00', '2026-07-10 17:19:00'),
(N'anhtuan', N'Tuấn', N'Phan Anh', '123', '1999-12-25', N'0967890123', N'88 Song Hành, Quận 2', N'', 1, N'tuananh@gmail.com',
 'ACTIVE', 0, '2026-07-10 12:00:00', '2026-07-10 12:30:00', '2026-07-10 12:30:00'),
(N'ngocha', N'Hà', N'Nguyễn Ngọc', '123', '2002-02-14', N'0978901234', N'442 Lý Thường Kiệt, Tân Bình', N'', 0, N'hangoc@gmail.com',
 'PENDING', 0, NULL, NULL, '2026-07-10 17:00:00'),
(N'duykhanh', N'Khánh', N'Trần Duy', '123', '1996-09-09', N'0989012345', N'15 Quang Trung, Gò Vấp', N'image_url_25', 1, N'khanhduy@gmail.com',
 'ACTIVE', 1, '2026-07-10 15:00:00', '2026-07-10 17:10:00', '2026-07-10 17:10:00'),
(N'maiphuong', N'Phương', N'Lý Mai', '123', '2001-06-18', N'0990123456', N'33 Hậu Giang, Quận 6', N'image_url_15', 0, N'phuongmai@gmail.com',
 'ACTIVE', 0, '2026-07-08 14:00:00', '2026-07-08 15:30:00', '2026-07-08 15:30:00');

create table roles
(
	role_id int primary key identity(1,1) ,
	role_name nvarchar(100)
)

insert into roles(role_name) values
(N'ADMIN') ,
(N'USER') ,
(N'MANAGER')


create table user_role
(
	user_id int ,
	role_id int ,
	primary key(user_id , role_id),
	foreign key (user_id) references  users(user_id) ,
	foreign key (role_id) references  roles(role_id)
)


insert into user_role(user_id , role_id) values
(1,1) ,
(1,2) ,
(2,2) ,
(1,3) ,
(3,2)

create table visibilities (
	visibility_id int primary key identity(1,1) ,
	visibility_name nvarchar(100)
)
insert into visibilities(visibility_name) values
(N'Public'),
(N'Private')

create table workspaces
(
	workspace_id int primary key identity(1,1) ,
	workspace_title nvarchar(100),
	create_at datetime default getdate(),
	owner_id int foreign key
	references users(user_id),
	visibility_id int foreign key
	references visibilities(visibility_id)
)

INSERT INTO workspaces (workspace_title, create_at, owner_id, visibility_id) VALUES
('Personal Projects', '2026-04-07',1, 1),
('Freelance Team', '2026-04-07',1,  2),
('Learning & Research', '2026-04-07',2, 1)

create table boards
(
	board_id int primary key identity(1,1) ,
	board_title nvarchar(100) ,
	create_at datetime default getdate() ,
	update_at datetime ,
    start_date datetime default getdate(),
    due_date datetime ,
    status nvarchar(50)  ,
	color nvarchar(50) ,
	workspace_id int,
	foreign key (workspace_id )references workspaces(workspace_id)
)

INSERT INTO boards (board_title, create_at, due_date,status, color, workspace_id) VALUES
('My Portfolio Website', '2026-03-10', '2026-03-10', 'ACTIVE', '#0079bf', 1),
('Java Core Practice', '2026-03-12', '2026-09-20', 'ACTIVE', '#4bce97', 1),
('System Documentation', '2025-09-21', '2026-11-30','ACTIVE', '#607d8b', 1),
('E-commerce Website', '2026-03-15', '2027-03-11', 'ACTIVE','#ff78cb', 2),
('Healthcare Mobile App', '2026-02-09', '2026-12-09','ARCHIVE', '#f2d600', 2),
('English Speaking Club', '2026-01-05', '2026-03-05','COMPLETE', '#eb5757', 3)
create table board_list_type
(
	board_list_type_id int primary key identity(1,1) ,
	board_list_type_code nvarchar(100) unique ,
	board_list_type_title nvarchar(100),
	description nvarchar(100)
)

insert into board_list_type (board_list_type_code, board_list_type_title, description)VALUES
    ('TODO',        N'To Do',       N'Tasks that have not been started yet'),
    ('PROGRESS',	N'In Progress', N'Tasks that are currently being worked on'),
    ('REVIEW',      N'Review',      N'Tasks waiting for review or approval'),
    ('DONE',        N'Done',        N'Tasks that have been completed successfully'),
    ('FAILED',      N'Failed',      N'Tasks that failed or were rejected'),
    ('BLOCKED',     N'Blocked',     N'Tasks that cannot proceed due to a blocker'),
    ('CANCELLED',   N'Cancelled',   N'Tasks that have been cancelled')

create table board_lists(
	board_list_id int primary key identity(1,1) ,
	board_list_title nvarchar(100) ,
	position int ,
	create_at datetime ,
    delete_at datetime default null,
	board_id int ,
	foreign key (board_id) references boards(board_id),
	board_list_type_id int foreign key
    references board_list_type(board_list_type_id)
)

insert into board_lists(board_list_title , position , board_id , board_list_type_id) values
(N'ToDo',0 , 1,1) ,
(N'Progress',100 , 1,2) ,
(N'Review',200 , 1,3) ,
(N'Failed',300 , 1,5) ,
(N'Done',400 , 1,4) ,
(N'BackLog',500 , 1,6) ,
(N'ToDo',0 , 2,1) ,
(N'Progress',100 , 2,2)
create table cards
(
	card_id int primary key identity(1,1) ,
	card_title nvarchar(100),
	card_description nvarchar(max),
	start_date datetime ,
	due_date datetime ,
	resolved_at datetime null,
	order_index float not null ,
	create_at datetime,
	delete_at datetime default null,
	owner_id int
	foreign key references users(user_id),
	board_list_id int ,
	foreign key (board_list_id) references board_lists(board_list_id)
)

insert into cards(card_title,card_description,start_date,due_date,order_index,delete_at,board_list_id) values
(N'Login Api' , N'Create method Login Api and Spring Security', N'2026-03-15',N'2026-03-20', 1024,null, 1) ,
(N'Security Config', N'Config Spring Security 6 to protect URL', N'2026-03-20',N'2026-04-01', 2048,null, 1),
(N'JWT Config', N'Create Class JWT Utils and JWT Filter Chain', N'2026-03-10',N'2026-03-29', 3072,null, 1),
(N'Create Account', N'Create query connect and get user by userId', N'2026-04-01',N'2026-04-05', 1024,null, 2),
(N'Payment Api', N'Create Payment Method and payment service', N'2026-02-20',N'2026-05-01', 2048,null, 2)

create table attachments (
	attachment_id int primary key identity(1,1) ,
	file_public_id nvarchar(max) ,
    file_name  nvarchar(255) ,
    file_type nvarchar(100),
    file_size bigint ,
	create_at datetime default getdate() ,
	delete_at datetime default null ,
	card_id int foreign key
	references cards(card_id) ,
	user_id int foreign key
	references users(user_id)
)

insert into attachments(file_public_id , create_at , card_id , user_id) values
(N'fileurl.png' , '2026-03-16' , 1, 1),
(N'document.text' , '2026-03-11' , 1, 1),
(N'text.docx' , '2026-03-15' , 2, 2)

create table labels (
	label_id int primary key identity(1,1) ,
	label_name nvarchar(100) ,
	label_color nvarchar(100) ,
	board_id int foreign key
	references boards(board_id)
)
insert into labels(label_name ,label_color,board_id) values
(N'Task', N'Red', 1),
(N'Test', N'Green', 1),
(N'Mission', N'Yellow', 1),
(N'Document', N'Blue', 2)

create table card_labels
(
	card_id int ,
	label_id int ,
	primary key (card_id , label_id) ,
	foreign key (card_id) references cards(card_id) ,
	foreign key (label_id) references labels(label_id)
)
insert into card_labels(card_id , label_id) values
(1, 1) ,
(1,2) ,
(2,3) ,
(2,1) ,
(2,4)

create table card_user (
	card_id int ,
	user_id int ,
	primary key (card_id , user_id) ,
	foreign key (card_id) references cards(card_id) ,
	foreign key (user_id) references users(user_id)
)
insert into card_user(card_id , user_id) values
(1, 1),
(2, 3),
(2, 1),
(1, 2)

create table workspace_members(
	workspace_id int ,
	user_id int ,
	role nvarchar(100),
	primary key (workspace_id , user_id),
	foreign key (workspace_id) references workspaces(workspace_id) ,
	foreign key (user_id) references users(user_id)
)

insert into workspace_members(workspace_id , user_id, role) values
(1, 1, N'ADMIN') ,
(1,2, N'MEMBER') ,
(1,4, N'MEMBER') ,
(1,3, N'MEMBER') ,
(1,5, N'MEMBER') ,
(2, 1,N'ADMIN'),
(2,4, N'MEMBER'),
(2,5, N'MEMBER')

create table board_members(
	board_id int ,
	user_id int ,
	role nvarchar(100) ,
	primary key (board_id , user_id) ,
	foreign key (board_id) references boards(board_id) ,
	foreign key (user_id) references users(user_id)
)

create table comments(
	comment_id int primary key identity(1,1) ,
	comment_content nvarchar(max) ,
	create_at datetime default getdate() ,
	update_at datetime ,
	delete_at datetime  default null,
    parent_id  int foreign key
    references comments(comment_id),
	card_id int foreign key
	references cards(card_id) ,
	user_id int foreign key
	references users(user_id)
)

insert into comments (comment_content, parent_id, card_id, user_id)
values
(N'Bài này rất hay và dễ hiểu', NULL, 1, 1),
(N'Tôi thấy phần này cần bổ sung thêm ví dụ', NULL, 1, 2),
(N'Có ai giải thích thêm đoạn này không?', NULL, 2, 3)

insert into comments (comment_content, parent_id, card_id, user_id)
values
(N'Mình đồng ý với bạn!', 1, 1, 2),
(N'Bạn có thể xem thêm tài liệu ở Google', 3, 2, 1),
(N'Ví dụ có thể là dùng REST API', 2, 1, 3),
(N'Cảm ơn bạn đã góp ý', 2, 1, 1)

create table activity_logs(
    activity_log_id int primary key identity(1,1) ,
	user_id int not null ,
	user_name nvarchar(50),
	action_type nvarchar(50),
	content text ,
	create_at datetime,
	extra_data nvarchar(max),
	entity_type nvarchar(50) ,
	entity_id int ,
	entity_name nvarchar(50),
	context_id int,
	context_type nvarchar(50),
	context_name nvarchar(50),
	old_value nvarchar(100) ,
    new_value  nvarchar(100),
    field_name nvarchar(100)
)

create table card_list_duration
(
	duration_id int primary key identity(1,1)  ,
	card_id int foreign key
	references cards(card_id) ,
	board_list_id int foreign key
	references board_lists(board_list_id) ,
	board_id int foreign key
	references boards(board_id) ,
	enter_time datetime  ,
	exit_time datetime ,
	duration_seconds bigint
)

create table checklists(
	checklist_id int  primary key identity(1,1)  ,
	checklist_name nvarchar(100)
)
