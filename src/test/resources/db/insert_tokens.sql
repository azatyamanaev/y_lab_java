delete from security.refresh_tokens;
insert into security.refresh_tokens(token, user_id, created, expires)
values ('token', -1, (now() - interval '1' day), (now() + interval '14' day));