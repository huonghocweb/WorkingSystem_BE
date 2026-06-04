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
	CONSTRAINT UQ_USERS_EMAIL UNIQUE(email) ,
	CONSTRAINT UQ_USERS_PHONENUMBER UNIQUE(phone_number)
)

insert into users(user_name, first_name, last_name, password, birth_day, phone_number, address, image_public_id, gender, email) VALUES
(N'huongpham', N'Hưởng', N'Phạm Văn', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '2000-12-12', N'0128283692', N'11 Võ Văn tần , Quận 3', N'user/xrivhibyw8fjambgmyqu', 0, N'huongpham12@gmail.com'),
(N'linhpham', N'Linh', N'Phạm Thị Thùy', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '2001-02-12', N'0903239422', N'99 Cộng Hòa , Tan Binh', N'user/x8tlvv6zp1lyilx5rlrt', 1, N'thuylinh199@gmail.com'),
(N'trungtran', N'Trung', N'Trần Đức', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '1999-10-02', N'0989892112', N'123 Cách Mạng Tháng 8, Quận 10', N'user/wccckiuoxvyvovwpm6pl', 1, N'trungduc09@gmail.com'),
(N'minhhoang', N'Hoàng', N'Nguyễn Minh', '$2a$10$VnzPDG7A0xOq8TfNe4muIuP.Ic5R9kaqCPDACHJ2ASC/DLOngDpDO', '1998-05-20', N'0901234567', N'45 Lê Lợi, Quận 1', N'image_url_4', 1, N'hoangminh@gmail.com'),
(N'lananh', N'Anh', N'Trương Lan', '123', '2002-08-15', N'0912345678', N'120 Hai Bà Trưng, Quận 3', N'', 0, N'lananh88@gmail.com'),
(N'thanhnam', N'Nam', N'Vũ Thành', '123', '1997-11-30', N'0923456789', N'789 Điện Biên Phủ, Bình Thạnh', N'', 1, N'namthanh@gmail.com'),
(N'bichngoc', N'Ngọc', N'Đỗ Bích', '123', '2001-03-25', N'0934567890', N'12 Phan Xích Long, Phú Nhuận', N'', 0, N'ngocbich@gmail.com'),
(N'quanghuy', N'Huy', N'Lê Quang', '123', '2000-01-10', N'0945678901', N'56 Nguyễn Trãi, Quận 5', N'', 1, N'huyquang@gmail.com'),
(N'thuyduong', N'Dương', N'Hoàng Thùy', '123', '2003-07-04', N'0956789012', N'234 Trần Hưng Đạo, Quận 1', N'', 0, N'duongthuy@gmail.com'),
(N'anhtuan', N'Tuấn', N'Phan Anh', '123', '1999-12-25', N'0967890123', N'88 Song Hành, Quận 2', N'', 1, N'tuananh@gmail.com'),
(N'ngocha', N'Hà', N'Nguyễn Ngọc', '123', '2002-02-14', N'0978901234', N'442 Lý Thường Kiệt, Tân Bình', N'', 0, N'hangoc@gmail.com'),
(N'duykhanh', N'Khánh', N'Trần Duy', '123', '1996-09-09', N'0989012345', N'15 Quang Trung, Gò Vấp', N'image_url_25', 1, N'khanhduy@gmail.com'),
(N'maiphuong', N'Phương', N'Lý Mai', '123', '2001-06-18', N'0990123456', N'33 Hậu Giang, Quận 6', N'image_url_15', 0, N'phuongmai@gmail.com');

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
	visibility_id int foreign key
	references visibilities(visibility_id)
)

INSERT INTO workspaces (workspace_title, create_at, visibility_id) VALUES
('Personal Projects', '2026-04-07', 1),
('Freelance Team', '2026-04-07', 2),
('Learning & Research', '2026-04-07', 1)

create table boards
(
	board_id int primary key identity(1,1) ,
	board_title nvarchar(100) ,
	create_at datetime default getdate() ,
	color nvarchar(50) ,
	workspace_id int,
	foreign key (workspace_id )references workspaces(workspace_id)
)

INSERT INTO boards (board_title, create_at, color, workspace_id) VALUES
('My Portfolio Website', '2026-03-10', '#0079bf', 1),
('Java Core Practice', '2026-03-12', '#4bce97', 1),
('System Documentation', '2025-09-21', '#607d8b', 1),

('E-commerce Website', '2026-03-15', '#ff78cb', 2),
('Healthcare Mobile App', '2026-02-09', '#f2d600', 2),

('English Speaking Club', '2026-01-05', '#eb5757', 3)

create table board_lists(
	board_list_id int primary key identity(1,1) ,
	board_list_title nvarchar(100) ,
	position int ,
	create_at datetime ,
    delete_at datetime default null,
	board_id int ,
	foreign key (board_id) references boards(board_id)
)

insert into board_lists(board_list_title , position , board_id ) values
(N'To Do',0 , 1) ,
(N'Progress',100 , 1) ,
(N'Review',200 , 1) ,
(N'Failed',300 , 1) ,
(N'Done',400 , 1) ,
(N'Back Log',500 , 1) ,
(N'To Do',0 , 2) ,
(N'Progress',100 , 2)

create table cards
(
	card_id int primary key identity(1,1) ,
	card_title nvarchar(100),
	card_description nvarchar(max),
	start_date datetime ,
	end_date datetime ,
	order_index float not null ,
	create_at datetime,
	delete_at datetime default null,
	board_list_id int ,
	foreign key (board_list_id) references board_lists(board_list_id)
)

insert into cards(card_title,card_description,start_date,end_date,order_index,delete_at,board_list_id) values
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
)

create table checklists(
	checklist_id int  primary key identity(1,1)  ,
	checklist_name nvarchar(100)
)
