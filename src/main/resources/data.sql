insert into wym.status (id, name)
values  (1, 'CONSUMER'),
        (2, 'AUTHOR'),
        (10, 'MODERATOR');

insert into wym.user (id, avatar, balance, banned, biography, firstname, lastname, login, middlename, password, payment_info_id, status_id)
values  (16, null, 0, false, null, null, null, 'qwe', null, '$2a$10$Tb4ve.KQ3QLTw8C8VRjUWuMGmjqZDfeyED5IIMEtSuLfj9JvVxlTC', null, 10),
        (18, null, 0, false, null, null, null, '1', null, '$2a$10$lFMYvLmMfucoOo0sq4NMSuK6I.E9W.1MdSpoiX1EEMP4TlBWioaii', null, 1),
        (19, null, 0, false, null, null, null, 'qwer', null, '$2a$10$sOSgkQItnKmjfKl9bWOfB.DsDxKt9QRDLCsos07I.dGqOpotf.7hS', null, 1),
        (20, null, 0, false, null, null, null, 'super-user', null, '$2a$10$uPZfvG2iUz9lAwXgJUJ0guiZh.kilAtYMX1EPl.kNOMDsOPOY.ZlW', null, 1),
        (22, null, 0, false, null, null, null, 'qwerty', null, '$2a$10$hxRvjAacJUFzFJJPjvgTI.Gm8MTTM7gFBb6dYrQDxp9PGB2qjPIrS', null, 1),
        (23, null, 0, false, null, null, null, 'zxc', null, '$2a$10$FIfyRKzWgUI7pGIOjlSmje67Xu0zIitvTl63xqgKyMQiqJfKbEdby', null, 1),
        (24, null, 0, false, null, null, null, 'ghjj', null, '$2a$10$61zf48tMpBBQMBTkOWJdfuxeWye2Ryqkd7rl7NCrT4.G1YXXz12wq', null, 1);

insert into wym.track (id, bpm, cover, description, file, genre, mood, name, price, author_id, active)
values  (228, 1, null, 'говно', null, 'хуйня', 'моча', 'Я роняю запад', 228, 16, false),
        (1337, 2, null, 'Релаксирующий массаж простаты', null, 'что?', 'почему?', 'Восход', 1337, 16, true),
        (3985, 666, null, 'DIEEEEEEEE', null, 'hard-cock', 'angry', 'LELELETME', 200, 16, true),
        (7489, 120, null, 'sdfhjklsdfgjl', null, 'jazz', 'chill', 'aughtumn', 100000, 18, true);

insert into wym.user_cart (user_id, cart_id)
values  (16, 228),
        (16, 1337),
        (18, 3985);

insert into wym.user_favorites (user_id, favorites_id)
values  (16, 228),
        (16, 1337),
        (24, 1337),
        (16, 3985),
        (18, 3985);

insert into wym.user_releases (user_id, releases_id)
values  (16, 228),
        (16, 1337),
        (16, 3985),
        (18, 7489);