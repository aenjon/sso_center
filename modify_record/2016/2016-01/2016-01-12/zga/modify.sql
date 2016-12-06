#tbusermain
ALTER TABLE tbusermain CHANGE invitateCode inviteCode VARCHAR(8);

ALTER TABLE tbusermain ADD userIcon VARCHAR(500);

#tbusertemp
ALTER TABLE tbusertemp CHANGE invitateCode inviteCode VARCHAR(8);
ALTER TABLE tbusertemp ADD userIcon VARCHAR(500);


