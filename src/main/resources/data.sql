insert into wym.status (id, name)
values  (1, 'CONSUMER'),
        (2, 'AUTHOR'),
        (10, 'MODERATOR');

insert into wym.user (id, avatar, balance, banned, biography, firstname, lastname, login, middlename, password, status_id)
values  (16, null, 0, false, null, null, null, 'qwe', null, '$2a$10$Tb4ve.KQ3QLTw8C8VRjUWuMGmjqZDfeyED5IIMEtSuLfj9JvVxlTC', 1),
        (18, null, 0, false, null, null, null, '1', null, '$2a$10$lFMYvLmMfucoOo0sq4NMSuK6I.E9W.1MdSpoiX1EEMP4TlBWioaii', 1),
        (19, null, 0, false, null, null, null, 'qwer', null, '$2a$10$sOSgkQItnKmjfKl9bWOfB.DsDxKt9QRDLCsos07I.dGqOpotf.7hS', 1),
        (20, null, 0, false, null, null, null, 'super-user', null, '$2a$10$uPZfvG2iUz9lAwXgJUJ0guiZh.kilAtYMX1EPl.kNOMDsOPOY.ZlW', 1);

insert into wym.track (id, bpm, cover, description, file, genre, mood, name, price, author_id)
values  (228, 1, null, 'говно', null, 'хуйня', 'моча', 'Я роняю запад', 228, 16),
        (1337, 2, null, 'Релаксирующий массаж простаты', null, 'что?', 'почему?', 'Восход', 1337, 16),
        (3985, 666, null, 'DIEEEEEEEE', null, 'hard-cock', 'angry', 'LELELETME', 200, 16),
        (7489, 120, null, 'sdfhjklsdfgjl', null, 'jazz', 'chill', 'aughtumn', 100000, 18);