insert into entity.users(id, name, email, password, role)
values (-10, 'admin_test', 'admin_test@mail.ru', '$2a$08$C/rwJDUXe6fpVN.ViMKjBuPtS0q0zx9Sg73D0WxHIupfuZc.WtWhe', 'ADMIN'),
       (-20, 'user1', 'a_test@mail.ru', '$2a$08$si6Z2oAD8/JmD4.brrTnbOQQG7DSjGxnr1cY.kM.nnEXjOsqgp6dK', 'USER'),
       (-30, 'user2', 'b_test@mail.ru', '$2a$08$RknMO7K52XKpq0RBtVu2Z.UBjrBVNT7BUVqd3S7kr3YO/hUiu3Aqm', 'USER');

insert into entity.habits(id, name, description, frequency, created, user_id)
values (-10, 'h1_test', 'd1', 'DAILY', to_date('2024-10-01', 'YYYY/MM/DD'), -20),
       (-20, 'h2_test', 'd2', 'DAILY', to_date('2024-09-20', 'YYYY/MM/DD'), -20),
       (-30, 'h3_test', 'd3', 'WEEKLY', to_date('2024-08-01', 'YYYY/MM/DD'), -20),
       (-40, 'hb1_test', 'ds1', 'WEEKLY', to_date('2024-09-10', 'YYYY/MM/DD'), -30),
       (-50, 'hb2_test', 'ds2', 'MONTHLY', to_date('2024-05-01', 'YYYY/MM/DD'), -30);

insert into entity.habit_history(user_id, habit_id, completed_on)
values (-20, -10, to_date('2024-10-01', 'YYYY/MM/DD')),
       (-20, -10, to_date('2024-10-05', 'YYYY/MM/DD')),
       (-20, -10, to_date('2024-10-10', 'YYYY/MM/DD')),
       (-30, -40, to_date('2024-09-10', 'YYYY/MM/DD')),
       (-30, -40, to_date('2024-09-17', 'YYYY/MM/DD')),
       (-30, -40, to_date('2024-09-24', 'YYYY/MM/DD'));