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

INSERT INTO users(user_name, first_name, last_name, password, birth_day, phone_number, address, image_public_id, gender, email) VALUES
(N'huongpham', N'Hưởng', N'Phạm Văn', '123', '2000-12-12', N'012828392', N'11 Võ Văn tần , Quận 3', N'user/aklcloyhujbsoe6klu5s', 0, N'huongpham12@gmail.com'),
(N'linhpham', N'Linh', N'Phạm Thị Thùy', '123', '2001-02-12', N'09032392', N'99 Cộng Hòa , Tan Binh', N'user/jf4ukrzsaugn2akp2a2i', 1, N'thuylinh199@gmail.com'),
(N'trungtran', N'Trung', N'Trần Đức', '123', '1999-10-02', N'0989892', N'123 Cách Mạng Tháng 8, Quận 10', N'pngtree-character-default-avatar-image_2237203_ibkb1f', 1, N'trungduc09@gmail.com'),
(N'minhhoang', N'Hoàng', N'Nguyễn Minh', '123', '1998-05-20', N'0901234567', N'45 Lê Lợi, Quận 1', N'image_url_4', 1, N'hoangminh@gmail.com'),
(N'lananh', N'Anh', N'Trương Lan', '123', '2002-08-15', N'0912345678', N'120 Hai Bà Trưng, Quận 3', N'image_url_5', 0, N'lananh88@gmail.com'),
(N'thanhnam', N'Nam', N'Vũ Thành', '123', '1997-11-30', N'0923456789', N'789 Điện Biên Phủ, Bình Thạnh', N'image_url_6', 1, N'namthanh@gmail.com'),
(N'bichngoc', N'Ngọc', N'Đỗ Bích', '123', '2001-03-25', N'0934567890', N'12 Phan Xích Long, Phú Nhuận', N'image_url_7', 0, N'ngocbich@gmail.com'),
(N'quanghuy', N'Huy', N'Lê Quang', '123', '2000-01-10', N'0945678901', N'56 Nguyễn Trãi, Quận 5', N'image_url_8', 1, N'huyquang@gmail.com'),
(N'thuyduong', N'Dương', N'Hoàng Thùy', '123', '2003-07-04', N'0956789012', N'234 Trần Hưng Đạo, Quận 1', N'image_url_9', 0, N'duongthuy@gmail.com'),
(N'anhtuan', N'Tuấn', N'Phan Anh', '123', '1999-12-25', N'0967890123', N'88 Song Hành, Quận 2', N'image_url_10', 1, N'tuananh@gmail.com'),
(N'ngocha', N'Hà', N'Nguyễn Ngọc', '123', '2002-02-14', N'0978901234', N'442 Lý Thường Kiệt, Tân Bình', N'image_url_11', 0, N'hangoc@gmail.com'),
(N'duykhanh', N'Khánh', N'Trần Duy', '123', '1996-09-09', N'0989012345', N'15 Quang Trung, Gò Vấp', N'image_url_12', 1, N'khanhduy@gmail.com'),
(N'maiphuong', N'Phương', N'Lý Mai', '123', '2001-06-18', N'0990123456', N'33 Hậu Giang, Quận 6', N'image_url_13', 0, N'phuongmai@gmail.com');

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

create table work_spaces
(
	work_space_id int primary key identity(1,1) ,
	work_space_title nvarchar(100),
	create_at datetime default getdate(),
	visibility_id int foreign key
	references visibilities(visibility_id)
)

insert into work_spaces(work_space_title, create_at , visibility_id ) values
(N'CourseHubProject' , '2026-12-19', 1),
(N'GaVangProject' , '2026-12-19', 2),
(N'FlowerShopProject' , '2026-12-19', 2),
(N'MedicalProject' , '2026-12-19', 1),
(N'EnglishSpeakProject' , '2026-12-19', 1)

create table boards
(
	board_id int primary key identity(1,1) ,
	board_title nvarchar(100) ,
	create_at datetime default getdate() ,
	work_space_id int,
	foreign key (work_space_id )references work_spaces(work_space_id)
)
insert into boards(board_title , create_at , work_space_id) values
(N'CourseBackEnd', '2026-12-11', 1),
(N'CourseFronEnd', '2026-12-12', 1),
(N'CourseDocument', '2025-09-21', 1),
(N'PaymentMethod', '2026-08-03', 2),
(N'PaymentService', '2026-02-09', 2)
create table board_lists(
	board_list_id int primary key identity(1,1) ,
	board_list_title nvarchar(100) ,
	position int ,
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
	position int ,
	board_list_id int ,
	foreign key (board_list_id) references board_lists(board_list_id)
)
insert into cards(card_title,card_description,start_date,end_date,position,board_list_id) values
(N'Login Api' , N'Create method Login Api and Spring Security', N'2026-03-15',N'2026-03-20', 1, 1) ,
(N'Security Config', N'Config Spring Security 6 to protect URL', N'2026-03-20',N'2026-04-01', 100, 1),
(N'JWT Config', N'Create Class JWT Utils and JWT Filter Chain', N'2026-03-10',N'2026-03-29', 200, 1),
(N'Create Account', N'Create query connect and get user by userId', N'2026-04-01',N'2026-04-05', 300, 2),
(N'Payment Api', N'Create Payment Method and payment service', N'2026-02-20',N'2026-05-01', 400, 2)
create table attachments (
	attachment_id int primary key identity(1,1) ,
	file_url nvarchar(max) ,
	create_at datetime default getdate() ,
	card_id int foreign key
	references cards(card_id) ,
	user_id int foreign key
	references users(user_id)
)
insert into attachments(file_url , create_at , card_id , user_id) values
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

create table card_label
(
	card_id int ,
	label_id int ,
	primary key (card_id , label_id) ,
	foreign key (card_id) references cards(card_id) ,
	foreign key (label_id) references labels(label_id)
)
insert into card_label(card_id , label_id) values
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

create table work_space_member(
	work_space_id int ,
	user_id int ,
	role nvarchar(100),
	primary key (work_space_id , user_id),
	foreign key (work_space_id) references work_spaces(work_space_id) ,
	foreign key (user_id) references users(user_id)
)

create table board_member(
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
	card_id int foreign key
	references cards(card_id) ,
	user_id int foreign key
	references users(user_id)
)

create table action_types(
	action_type_id int primary key identity(1,1) ,
	action_type_title nvarchar(100)
)

create table activity_logs(
	activity_log_id int primary key identity(1,1) ,
	old_value nvarchar(100) ,
	new_value nvarchar(100) ,
	create_at datetime default getdate() ,
	action_type_id int ,
	foreign key (action_type_id) references action_types(action_type_id) ,
	user_id int,
	foreign key (user_id) references users(user_id) ,
	card_id int ,
	foreign key (card_id) references cards(card_id)
)

create table checklists(
	checklist_id int  primary key identity(1,1)  ,
	checklist_name nvarchar(100)
)
