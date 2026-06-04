create table workspace_invitations (
	invitation_id int  primary key  identity(1,1),
	status nvarchar(50) ,
	invite_token nvarchar(100) unique ,
	email nvarchar(100) not null ,
	workspace_id int foreign key
	references workspaces(workspace_id) ,
	inviter_id int foreign key
	references users(user_id) ,
	CONSTRAINT UC_WORKSPACE_EMAIL unique (workspace_id , email)
)
go