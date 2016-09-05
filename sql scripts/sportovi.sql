create table sport_discipline
(
	id int not null auto_increment,
    name varchar(128),
    sport_id int,
    primary key(id),
    foreign key (sport_id) references sport(idsport)
);

insert into sport (name) values ('Kosarka'), ('Odbojka');

insert into sport (name) values ('Vaterpolo'), ('Atletika'), ('Biciklizam'), ('Plivanje'), ('Stoni tenis'), ('Streljastvo'), ('Tenis');

alter table sport_discipline add discipline_type varchar(20);

select * from sport_discipline;

select * from sport;

insert into sport_discipline (name, sport_id, min_players, max_players, discipline_type) values
('Kosarka', 1, 5, 12, 'ekipni'),
('Odbojka', 2, 6, 12, 'ekipni'),
('Vaterpolo', 3, 6, 13, 'ekipni'),
('100m trcanje', 4, 1, 1, 'individualni'),
('200m trcanje', 4, 1, 1, 'individualni'),
('400m trcanje', 4, 1, 1, 'individualni'),
('800m trcanje', 4, 1, 1, 'individualni'),
('1000m trcanje', 4, 1, 1, 'individualni'),
('5000m trcanje', 4, 1, 1, 'individualni'),
('Skok u vis', 4, 1, 1, 'individualni'),
('Skok u dalj', 4, 1, 1, 'individualni'),
('Troskok', 4, 1, 1, 'individualni'),
('Skok s motkom', 4, 1, 1, 'individualni'),
('Bacanje kugle', 4, 1, 1, 'individualni'),
('Bacanje diska', 4, 1, 1, 'individualni'),
('Bacanje kladiva', 4, 1, 1, 'individualni'),
('Bacanje koplja', 4, 1, 1, 'individualni'),
('Maraton', 4, 1, 1, 'individualni'),
('20km brzo hodanje', 4, 1, 1, 'individualni'),
('50km brzo hodanje', 4, 1, 1, 'individualni'),
('Drumska trka 255km', 5, 1, 1, 'individualni'),
('100m leptir', 6, 1, 1, 'individualni'),
('200m slobodno', 6, 1, 1, 'individualni'),
('Signl', 7, 1, 1, 'individualni'),
('Dubl', 7, 2, 2, 'ekipni'),
('50m trostav', 8, 1, 1, 'individualni'),
('10m vazdusna puska', 8, 1, 1, 'individualni'),
('25m malokalib. pistolj', 8, 1, 1, 'individualni'),
('10m vazdusni pistolj', 8, 1, 1, 'individualni'),
('Signl', 9, 1, 1, 'individualni'),
('Dubl', 9, 2, 2, 'ekipni');