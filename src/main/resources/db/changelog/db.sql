drop table if exists survey cascade;
create table survey(
                       id serial primary key,
                       title varchar(50),
                       start_date varchar(50),
                       end_date varchar(50),
                       is_active varchar(50)
);

drop table if exists question cascade ;
create table question(
                         id serial primary key ,
                         body varchar(100)
);
drop table if exists surveyslike;
create table surveysLike(
                            id serial ,
                            survey_id int not null ,
                            question_id int not null ,
                            primary key (survey_id,question_id),
                            foreign key (survey_id) references survey(id) on delete cascade ,
                            foreign key (question_id) references question(id) on  delete cascade
);
insert into question(body)
values
    ('то для вас самое сложное в работе программиста?'),
    ('Что вам нравится в программировании?'),
    ( 'What would your skills and personality contribute to our team?'),
    ('Name your greatest strength and your greatest weakness.'),
    ('What would your skills and personality contribute to our team?'),
    ('What’s the most recent language that you learned?'),
    ('What s your favorite programming language?'),
    ('How are you?'),
    ('Whats your favorite programming language?'),
    ('Where are you from?'),
    ('Что такое конструктор в Java'),
    ('Какие два класса не наследуются от Object?'),
    ('Что такое Local Variable?'),
    ('Что такое Instance Variable?'),
    ('Что такое модификаторы доступа?'),
    ('Что такое переопределение (overriding) методов?'),
    ('Что такое сигнатура метода?'),
    ('Что такое Interface?'),
    ('Что такое default method в Interface?'),
    ('А как тогда наследовать два одинаковых дефолтных метода?'),
    ('Что такое перегрузка методов?'),
    ('Какая разница между String, String Builder и String Buffer?'),
    ('Что такое абстрактные методы и классы?'),
    ('Какая разница между абстрактным классом и интерфейсом?'),
    ('Почему доступ по элементу в массиве происходит за O(1)?'),
    ('А как получается О(1) в доступе к объектам в ArrayList?'),
    ('Автоупаковка (autoboxing) и Автораспаковка (unboxing)'),
    ('Что такое ключевое слово final и где его использовать?'),
    ('Можно ли считать Final переменную константой?'),
    ('Что такое mutable immutable?'),
    ('Что такое состояние гонки?'),
    ('Как написать immutable класс?'),
    ('Какие классы и интерфейсы доступны в Collection фреймворке?'),
    ('Что подразумевается под sorted и ordered в коллекциях?'),
    ('Какие есть коллекции с List интерфейсом? Как происходит работа с List?'),
    ('Расскажите о коллекции Map и ее реализациях?'),
    ('Расскажите о коллекции Set и ее реализациях?'),
    ('Как программистам обрабатывать исключения?'),
    ('Что такое Exception?'),
    ('Что если вызвать напрямую метод run(), не вызывая метод start()?'),
    ('Каковы состояния в жизненном цикле потока?'),
    ('Какая разница между процессом и потоком?'),
    ('throw и throws в Java'),
    ('Можно ли запустить тред дважды?'),
    ('Что такое volatile переменная?'),
    ('Что такое deadlock'),
    ('Какие преимущества есть у многопоточности?'),
    ('Что такое try-with-resources?'),
    ('Что такое shutdownhook?'),
    ('Что такое синхронизация (synchronization)?'),
    ('Checked и Unchecked исключения в Java'),
    ('Реализуем интерфейс Runnable'),
    ('Как JVM обрабатывает исключения?'),
    ('Можно ли сделать поток демоном уже после его создания?'),
    ('Как создать в Java новый тред (поток)?'),
    ('Что такое daemon тред?');