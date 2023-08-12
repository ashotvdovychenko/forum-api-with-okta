insert into COMMENTS (id, text, topic_id, user_id, created_at)
values (9, 'some text', 1, 1, now() + interval '8'),
       (8, 'some text', 2, 2, now() + interval '7'),
       (7, 'some text', 3, 3, now() + interval '6'),
       (6, 'some text', 1, 4, now() + interval '5'),
       (5, 'some text', 2, 5, now() + interval '4'),
       (4, 'some text', 3, 1, now() + interval '3'),
       (3, 'some text', 1, 2, now() + interval '2'),
       (2, 'some text', 2, 3, now() + interval '1'),
       (1, 'some text', 3, 4, now());