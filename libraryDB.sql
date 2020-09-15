-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book_catalog`
--

DROP TABLE IF EXISTS `book_catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(200) NOT NULL,
  `book_title` varchar(200) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `author` (`author`),
  FULLTEXT KEY `author_2` (`author`),
  FULLTEXT KEY `author_3` (`author`),
  FULLTEXT KEY `author_4` (`author`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_catalog`
--

LOCK TABLES `book_catalog` WRITE;
/*!40000 ALTER TABLE `book_catalog` DISABLE KEYS */;
INSERT INTO `book_catalog` VALUES (1,'﻿Джон Р. Р. Толкин','﻿«Властелин колец»','Электронный'),(2,'Джейн Остин','«Гордость и предубеждение»','Электронный'),(3,'Филип Пулман','«Тёмные начала»','Электронный'),(4,'Дуглас Адамс','«Автостопом по галактике»','Электронный'),(5,'Джоан Роулинг','«Гарри Поттер и Кубок огня»','Электронный'),(6,'Харпер Ли','«Убить пересмешника»','Электронный'),(7,'Алан Александр Милн','«Винни Пух»','Электронный'),(8,'Джордж Оруэлл','«1984»','Электронный'),(9,'Клайв Стэйплз Льюис','«Лев, колдунья и платяной шкаф»','Электронный'),(10,'Шарлотта Бронте','«Джейн Эйр»','Электронный'),(11,'Джозеф Хеллер','«Уловка-22»','Электронный'),(12,'Эмили Бронте','«Грозовой перевал»','Электронный'),(13,'Себастьян Фолкс','«Пение птиц»','Электронный'),(14,'Дафна Дюморье','«Ребекка»','Электронный'),(15,'Джером Сэлинджер','«Над пропастью во ржи»','Электронный'),(16,'Кеннет Грэм','«Ветер в ивах»','Электронный'),(17,'Чарльз Диккенс','«Большие надежды»','Электронный'),(18,'Луиза Мэй Олкотт','«Маленькие женщины»','Электронный'),(19,'Луи де Берньер','«Мандолина капитана Корелли»','Электронный'),(20,'Лев Толстой','«Война и мир»','Электронный'),(21,'Маргарет Митчелл','«Унесённые ветром»','Электронный'),(22,'Джоан Роулинг','«Гарри Поттер и философский камень»','Электронный'),(23,'Джоан Роулинг','«Гарри Поттер и Тайная комната»','Электронный'),(24,'Джоан Роулинг','«Гарри Поттер и узник Азкабана»','Электронный'),(25,'Колин Маккалоу','«Поющие в терновнике»','Электронный'),(26,'Томас Харди','«Тэсс из рода д’Эрбервиллей»','Электронный'),(27,'Джордж Элиот','«Миддлмарч»','Электронный'),(28,'Джон Стейнбек','«Гроздья гнева»','Электронный'),(29,'Льюис Кэрролл','«Алиса в Стране чудес»','Электронный'),(30,'Жаклин Уилсон','«Дневник Трейси Бикер»','Электронный'),(31,'Габриэль Гарсиа Маркес','«Сто лет одиночества»','Электронный'),(32,'Кен Фоллетт','«Столпы Земли»','Электронный'),(33,'Чарльз Диккенс','«Дэвид Копперфильд»','Электронный'),(34,'Джон Ирвинг','«Молитва об Оуэне Мини»','Электронный'),(35,'Роальд Даль','«Чарли и шоколадная фабрика»','Электронный'),(36,'Роберт Стивенсон','«Остров сокровищ»','Электронный'),(37,'Невил Шют','«Город как Элис»','Электронный'),(38,'Джейн Остин','«Доводы рассудка»','Электронный'),(39,'Фрэнк Герберт','«Дюна»','Электронный'),(40,'Джейн Остин','«Эмма»','Электронный'),(41,'Люси Мод Монтгомери','«Аня из Зеленых Мезонинов»','Электронный'),(42,'Ричард Адамс','«Обитатели холмов»','Электронный'),(43,'Фрэнсис Скотт Фицджеральд','«Великий Гэтсби»','Электронный'),(44,'Ивлин Во','«Возвращение в Брайдсхед»','Электронный'),(45,'Александр Дюма','«Граф Монте-Кристо»','Электронный'),(46,'Джордж Оруэлл','«Скотный двор»','Электронный'),(47,'Чарльз Диккенс','«Рождественская песнь»','Электронный'),(48,'Томас Харди','«Вдали от обезумевшей толпы»','Электронный'),(49,'Мишель Магориан','«Спокойной ночи, мистер Том»','Электронный'),(50,'Розамунд Пилчер ','«Семейная реликвия»','Электронный'),(51,'Фрэнсис Бернетт','«Таинственный сад»','Электронный'),(52,'Джон Стейнбек','«О мышах и людях»','Электронный'),(53,'Лев Толстой','«Анна Каренина»','Электронный'),(54,'Стивен Кинг','«Противостояние»','Электронный'),(55,'Викрам Сет','«Подходящий жених»','Электронный'),(56,'Роальд Даль','«БДВ, или Большой и добрый великан»','Электронный'),(57,'Артур Рэнсом','«Ласточки и амазонки»','Электронный'),(58,'Анна Сьюэлл','«Чёрный красавчик»','Электронный'),(59,'Йон Колфер','«Артемис Фаул»','Электронный'),(60,'Мэлори Блэкмен','«Крестики-нолики»','Электронный'),(61,'Артур Голден','«Мемуары гейши»','Электронный'),(62,'Чарльз Диккенс','«Повесть о двух городах»','Электронный'),(63,'Терри Пратчетт','«Мор, ученик Смерти»','Электронный'),(64,'Федор Достоевский','«Преступление и наказание»','Электронный'),(65,'Энид Блайтон','«Далекое волшебное дерево»','Электронный'),(66,'Джон Р. Р. Толкин','«Хоббит, или Туда и обратно»','Электронный'),(67,'Джон Фаулз','«Волхв»','Электронный'),(68,'Терри Пратчетт и Нил Гейман','«Благие знамения»','Электронный'),(69,'Терри Пратчетт','«Стража! Стража!»','Электронный'),(70,'Уильям Голдинг','«Повелитель мух»','Электронный'),(71,'Патрик Зюскинд','«Парфюмер»','Электронный'),(72,'Роберт Тресселл','«Филантропы в рваных штанах»','Электронный'),(73,'Терри Пратчетт','«Ночная стража»','Электронный'),(74,'Роальд Даль','«Матильда»','Электронный'),(75,'Хелен Филдинг','«Дневник Бриджит Джонс»','Электронный'),(76,'Донна Тартт','«Тайная история»','Электронный'),(77,'Уильям Уилки Коллинз','«Женщина в белом»','Электронный'),(78,'Джеймс Джойс','«Улисс»','Электронный'),(79,'Чарльз Диккенс','«Холодный дом»','Электронный'),(80,'Жаклин Уилсон','«Двойняшки»','Электронный'),(81,'Роальд Даль','«Семейство Твит»','Электронный'),(82,'Доди Смит','«Я захватываю замок»','Электронный'),(83,'Луис Сейкер','«Ямы»','Электронный'),(84,'Арундати Рой','«Бог мелочей»','Электронный'),(85,'Мервин Пик','«Горменгаст»','Электронный'),(86,'Жаклин Уилсон','«Вики-Ангел»','Электронный'),(87,'Олдос Хаксли','«О дивный новый мир»','Электронный'),(88,'Стелла Гиббонс','«Неуютная ферма»','Электронный'),(89,'Раймонд Фэйст','«Чародей»','Электронный'),(90,'Джек Керуак','«В дороге»','Электронный'),(91,'Марио Пьюзо','«Крестный отец»','Электронный'),(92,'Джин Ауэл','«Клан пещерного медведя»','Электронный'),(93,'Терри Пратчетт','«Цвет волшебства»','Электронный'),(94,'Пауло Коэльо','«Алхимик»','Электронный'),(95,'Аня Сетон','«Кэтрин»','Электронный'),(96,'Джеффри Арчер','«Каин и Авель»','Электронный'),(97,'Габриэль Гарсиа Маркес','«Любовь во время холеры»','Электронный'),(98,'Жаклин Уилсон','«Девчонки в поисках любви»','Электронный'),(99,'Мэг Кэбот','«Дневники принцессы»','Электронный'),(100,'Салман Рушди','«Дети полуночи»','Электронный'),(101,'Джером К. Джером','«Трое в лодке, не считая собаки»','Электронный'),(102,'Терри Пратчетт','«Мелкие боги»','Электронный'),(103,'Алекс Гарленд','«Пляж»','Электронный'),(104,'Брэм Стокер','«Дракула»','Электронный'),(105,'Энтони Горовиц','«Белый пик»','Электронный'),(106,'Чарльз Диккенс','«Посмертные записки Пиквикского клуба»','Электронный'),(107,'Энтони Горовиц','«Громобой»','Электронный'),(108,'Иэн Бэнкс','«Осиная фабрика»','Электронный'),(109,'Фредерик Форсайт','«День Шакала»','Электронный'),(110,'Жаклин Уилсон','«Разрисованная мама»','Электронный'),(111,'Томас Харди','«Джуд Незаметный»','Электронный'),(112,'Сью Таунсенд','«Тайный дневник Адриана Моула, 13 3/4 лет»','Электронный'),(113,'Виктор Гюго','«Отверженные»','Электронный'),(114,'Николас Монсаррат','«Жестокое море»','Электронный'),(115,'Томас Харди','«Мэр Кэстербриджа»','Электронный'),(116,'Жаклин Уилсон','«Рискованные игры»','Электронный'),(117,'Жаклин Уилсон','«Плохие девчонки»','Электронный'),(118,'Оскар Уайльд ','«Портрет Дориана Грея»','Электронный'),(119,'Джеймс Клавелл','«Сегун»','Электронный'),(120,'Джон Уиндем','«День триффидов»','Электронный'),(121,'Жаклин Уилсон','«Лола Роза»','Электронный'),(122,'Уильям Теккерей','«Ярмарка тщеславия»','Электронный'),(123,'Марк Данилевски','«Дом из листьев»','Электронный'),(124,'Джон Голсуорси','«Сага о Форсайтах»','Электронный'),(125,'Барбара Кингсолвер','«Библия ядовитого леса»','Электронный'),(126,'Терри Пратчетт','«Мрачный жнец»','Электронный'),(127,'Луиз Рэннисон','«Ангус, ремни и конкретные обжимашки»','Электронный'),(128,'Артур Конан Дойль','«Собака Баскервилей»','Электронный'),(129,'Антония Сьюзен Байетт','«Обладать»','Электронный'),(130,'Михаил Булгаков','«Мастер и Маргарита»','Электронный'),(131,'Маргарет Этвуд','«Рассказ служанки»','Электронный'),(132,'Роальд Даль','«Денни — чемпион мира»','Электронный'),(133,'Джон Стейнбек','«К востоку от рая»','Электронный'),(134,'Роальд Даль','«Лечение Джорджа Марвелуса» ','Электронный'),(135,'Терри Пратчетт','«Вещие сестрички»','Электронный'),(136,'Элис Уокер','«Пурпурный цвет»','Электронный'),(137,'Терри Пратчетт','«Санта-Хрякус»','Электронный'),(138,'Джон Бьюкен','«39 ступеней»','Электронный'),(139,'Жаклин Уилсон','«Девчонки в слезах»','Электронный'),(140,'Жаклин Уилсон','«Вечеринка с ночевкой»','Электронный'),(141,'Эрих Мария Ремарк','«На западном фронте без перемен»','Электронный'),(142,'Кейт Аткинсон','«В хранилищах музея»','Электронный'),(143,'Ник Хорнби ','«Hi-Fi»','Электронный'),(144,'Стивен Кинг','«Оно»','Электронный'),(145,'Роальд Даль','«Джеймс и гигантский персик»','Электронный'),(146,'Стивен Кинг','«Зеленая миля»','Электронный'),(147,'Анри Шарьер','«Мотылек»','Электронный'),(148,'Терри Пратчетт','«К оружию! К оружию!»','Электронный'),(149,'Патрик О’Брайан','«Хозяин морей: Командир и штурман»','Электронный'),(150,'Энтони Горовиц','«Ключ от всех дверей»','Электронный'),(151,'Терри Пратчетт','«Роковая музыка»','Электронный'),(152,'Терри Пратчетт','«Вор времени»','Электронный'),(153,'Терри Пратчетт','«Пятый элефант»','Электронный'),(154,'Иэн Макьюэн','«Искупление»','Электронный'),(155,'Жаклин Уилсон','«Секреты»','Электронный'),(156,'Ян Серрэйлер','«Серебряный меч»','Электронный'),(157,'Кен Кизи','«Пролетая над гнездом кукушки»','Электронный'),(158,'Джозеф Конрад','«Сердце тьмы»','Электронный'),(159,'Редьярд Киплинг','«Ким»','Электронный'),(160,'Диана Гэблдон','«Чужестранка»','Электронный'),(161,'Герман Мелвилл','«Моби Дик»','Электронный'),(162,'Льюис Грессик Гиббон','«Песнь на закате»','Электронный'),(163,'Энни Пру','«Корабельные новости»','Электронный'),(164,'Уилбур Смит','«Божество реки»','Электронный'),(165,'Джон Ирвинг','«Мир глазами Гарпа»','Электронный'),(166,'Ричард Блэкмор','«Лорна Дун»','Электронный'),(167,'Жаклин Уилсон','«Девчонки гуляют допоздна»','Электронный'),(168,'Мэри Маргарет Кей','«Далекие шатры»','Электронный'),(169,'Роальд Даль','«Ведьмы»','Электронный'),(170,'Элвин Брукс Уайт','«Паутина Шарлотты»','Электронный'),(171,'Мэри Шелли','«Франкенштейн»','Электронный'),(172,'Терри Венейблс и Гордон М. Уильямс','«Они играли на траве»','Электронный'),(173,'Эрнест Хемингуэй','«Старик и море»','Электронный'),(174,'Владимир Набоков','«Лолита»','Электронный'),(175,'Умберто Эко','«Имя розы»','Электронный'),(176,'Йостейн Гордер','«Мир Софии»','Электронный'),(177,'Жаклин Уилсон','«Девочка-находка»','Электронный'),(178,'Роальд Даль','«Фантастический мистер Фокс»','Электронный'),(179,'Ричард Бах','«Чайка по имени Джонатан Ливингстон»','Электронный'),(180,'Антуан де Сент-Экзюпери','«Маленький принц»','Электронный'),(181,'Жаклин Уилсон','«Ребенок из чемодана»','Электронный'),(182,'Чарльз Диккенс','«Оливер Твист»','Электронный'),(183,'Брюс Куртеней','«Сила единства»','Электронный'),(184,'Джордж Элиот','«Сайлес Марнер»','Электронный'),(185,'Брет Истон Эллис','«Американский психопат»','Электронный'),(186,'Джордж и Уидон Гроссмит','«Ничей дневник»','Электронный'),(187,'Ирвин Уэлш','«На игле»','Электронный'),(188,'Иоганна Шпири','«Хайди»','Электронный'),(189,'Дэвид Герберт Лоуренс','«Сыновья и любовники»','Электронный'),(190,'Милан Кундера','«Невыносимая лёгкость бытия»','Электронный'),(191,'Тони Парсонс','«Мужчина и мальчик»','Электронный'),(192,'Терри Пратчетт','«Правда»','Электронный'),(193,'Герберт Уэллс','«Война миров»','Электронный'),(194,'Роберт Лоуренс Стайн','«Ужастики»','Электронный'),(195,'Николас Эванс','«Заклинатель лошадей»','Электронный'),(196,'Рохинтон Мистри','«Хрупкое равновесие»','Электронный'),(197,'Терри Пратчетт','«Ведьмы за границей»','Электронный'),(198,'Теренс Уайт','«Король былого и грядущего»','Электронный'),(199,'Эрик Карл','«Очень голодная гусеница»','Электронный'),(200,'Вирджиния Эндрюс','«Цветы на чердаке»','Электронный');
/*!40000 ALTER TABLE `book_catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_data`
--

DROP TABLE IF EXISTS `user_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  `user_access` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_data`
--

LOCK TABLES `user_data` WRITE;
/*!40000 ALTER TABLE `user_data` DISABLE KEYS */;
INSERT INTO `user_data` VALUES (1,'Admin','Admin@gmail.com','698D51A19D8A121CE581499D7B701668',1),(2,'User','User@gmail.com','084E0343A0486FF05530DF6C705C8BB4',0),(3,'User1','User1@gmail.com','084E0343A0486FF05530DF6C705C8BB4',0),(4,'Test','projecttest500@gmail.com','084E0343A0486FF05530DF6C705C8BB4',0);
/*!40000 ALTER TABLE `user_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'library'
--

--
-- Dumping routines for database 'library'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-08 20:01:55