create table refresh_tokens
(
	refresh_token_id int primary key identity(1,1) ,
	refresh_token nvarchar(max) ,
	expiry datetime ,
	user_id int foreign key
	references users(user_id)
)