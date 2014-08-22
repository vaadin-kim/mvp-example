insert into person (id, firstname, lastname, title, email) values 
(1, 'Jack', 'Bauer', 'Project Manager', 'jack@example.com'),
(2, 'John', 'Doe', 'Project Manager', 'john@example.com'),
(3, 'Jane', 'Doe', 'Project Manager', 'jane@example.com');

insert into skill (id, skill_name) values
(1, 'Programming'),
(2, 'Testing'),
(3, 'XP');

insert into person_skill (person_id, skill_id) values 
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(3, 2),
(3, 3);