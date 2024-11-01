insert into entity.users(id, name, email, password, role)
values (0, 'admin_test', 'admin_test@mail.ru', '$2a$08$C/rwJDUXe6fpVN.ViMKjBuPtS0q0zx9Sg73D0WxHIupfuZc.WtWhe', 'ADMIN'),
       (-1, 'user1', 'a_test@mail.ru', '$2a$08$si6Z2oAD8/JmD4.brrTnbOQQG7DSjGxnr1cY.kM.nnEXjOsqgp6dK', 'USER'),
       (-2, 'user2', 'b_test@mail.ru', '$2a$08$RknMO7K52XKpq0RBtVu2Z.UBjrBVNT7BUVqd3S7kr3YO/hUiu3Aqm', 'USER');

insert into entity.habits(id, name, description, frequency, created, user_id)
values (-1, 'h1_test', 'd1', 'DAILY', to_date('2024-10-01', 'YYYY/MM/DD'), -1),
       (-2, 'h2_test', 'd2', 'DAILY', to_date('2024-09-20', 'YYYY/MM/DD'), -1),
       (-3, 'h3_test', 'd3', 'WEEKLY', to_date('2024-08-01', 'YYYY/MM/DD'), -1),
       (-4, 'hb_1test', 'ds1', 'WEEKLY', to_date('2024-09-10', 'YYYY/MM/DD'), -2),
       (-5, 'hb_2test', 'ds2', 'MONTHLY', to_date('2024-05-01', 'YYYY/MM/DD'), -2);

insert into entity.habit_history(user_id, habit_id, completed_on)
values (-1, -1, to_date('2024-10-01', 'YYYY/MM/DD')),
       (-1, -1, to_date('2024-10-05', 'YYYY/MM/DD')),
       (-1, -1, to_date('2024-10-10', 'YYYY/MM/DD')),
       (-2, -4, to_date('2024-09-10', 'YYYY/MM/DD')),
       (-2, -4, to_date('2024-09-17', 'YYYY/MM/DD')),
       (-2, -4, to_date('2024-09-24', 'YYYY/MM/DD'));