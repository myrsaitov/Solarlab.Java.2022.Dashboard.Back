-- Removes all rows from a table or specified partitions of a table, without logging the individual row deletions.
TRUNCATE TABLE advertisement CASCADE;

-- Insert Data into columns
INSERT INTO advertisement (id, created_at, title, body, price, status) VALUES (1, now(), 'FromMigrationAdvertisement', 'NULL', 0, 0);


-- Функция для привязки объявления к категории
CREATE OR REPLACE FUNCTION add_to_category(category_id BIGINT)
    RETURNS SETOF BIGINT -- Здесь SET размером 1x1, но он нужен,
                         -- чтобы можно было использовать SELECT
    LANGUAGE plpgsql AS
$func$
BEGIN
    RETURN QUERY
        SELECT id
        FROM  category WHERE id = category_id;
END
$func$;

-- Сидирование демонстрационных объявлений
INSERT INTO advertisement (id, title, body, price, created_at, status, category_id)
SELECT
    -- id
    unnest(array[2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26]),
    -- title
    unnest(array[
        'Продам транспорт',--2
        'Продам автомобиль',--2
        'Продам мотоцикл',--3
        'Продам мототехнику б/у',--3
        'Продам грузовик б/у',--4
        'Продам спецтехнику',--4
        'Продам недвижимость',--6
        'Продам квартиру',--6
        'Продам комнату',--7
        'Продам дом',--8
        'Продам дачу',--8
        'Продам котедж',--8
        'Продам электронику',--10
        'Продам аудиотехнику б/у',--10
        'Продам видеотехнику б/у',--10
        'Продам игру',--11
        'Продам приставку',--11
        'Продам программу',--11
        'Продам настольный компьютер б/у',--12
        'Продам ноутбук',--13
        'Продам планшет',--14
        'Продам электронную книгу',--14
        'Продам телефон',--15
        'Продам товары для компьютера', --16
        'Продам фотоаппарат б/у' --17
        ]),
    -- body
    unnest(array[
        'Совокупность всех видов путей сообщения, транспортных средств, технических устройств и сооружений на путях сообщения, обеспечивающих процесс перемещения людей и грузов различного назначения из одного места в другое',--1
        'Основное назначение автомобиля заключается в совершении транспортной работы. Автомобильный транспорт в промышленно развитых странах занимает ведущее место по сравнению с другими видами транспорта по объёму перевозок пассажиров. Современный автомобиль состоит из 15—20 тысяч деталей, из которых 150—300 являются наиболее важными и требующими наибольших затрат в эксплуатации',--2
        'Классические мотоциклы включают в себя двухколёсные, двухколёсные с боковой коляской, и трёхколёсные; в начале XXI века стали набирать популярность квадроциклы. Мотоциклы также подазделяются по своей конструкции и размерам: мопеды, мокики (имеют небольшой размер двигателя, как правило до 50 см³)',--3
        'Классические мотоциклы включают в себя двухколёсные, двухколёсные с боковой коляской, и трёхколёсные; в начале XXI века стали набирать популярность квадроциклы. Мотоциклы также подазделяются по своей конструкции и размерам: мопеды, мокики (имеют небольшой размер двигателя, как правило до 50 см³)',--3
        'Грузовой автомобиль (разг. грузовик) — автомобиль, предназначенный для перевозки грузов в кузове или на грузовой платформе. Для обобщённого обозначения машин, созданных на базе грузового автомобиля, используется термин грузовая техника.',--4
        'За считанные месяцы были разработаны, изготовлены и подготовлены к участию во Всесоюзном мотопробеге пять мотоциклов пяти различных моделей. Наиболее удачными были мотоциклы — колоссы «Иж-1» и «Иж-2» с двухцилиндровыми V-образными двигателями рабочим объёмом 1200 см³ и максимальной мощностью 24 л. с. Для своего времени это были чрезвычайно оригинальные и передовые конструкции. Коленчатый вал двигателя располагался продольно, крутящий момент на заднее колесо передавался от трёхступенчатой коробки передач,',--4
        'Недви́жимость — вид имущества, признаваемого в законодательном порядке недвижимым. К недвижимости по происхождению относятся земельные участки, участки недр и все, что прочно связано с землёй, то есть объекты, перемещение которых без несоразмерного ущерба их назначению невозможно, в том числе здания, сооружения, объекты незавершённого строительства.',--5
        'Кварти́ра (от нем. Quartier[1]) — один из видов жилого помещения, состоящий из одной или нескольких смежных комнат а также в отдельных случаях с отдельным наружным выходом, составляющее отдельную часть дома.',--6
        'Кварти́ра (от нем. Quartier[1]) — один из видов жилого помещения, состоящий из одной или нескольких смежных комнат а также в отдельных случаях с отдельным наружным выходом, составляющее отдельную часть дома.',--7
        'Котте́дж (от англ. cottage) — индивидуальный городской или сельский малоэтажный (обычно двухэтажный) жилой дом с небольшим участком прилегающей земли[1] для постоянного или временного проживания одной нуклеарной семьи. Первый этаж занимают такие помещения как гостиная, кухня, санузел, котельная, часто гараж для легкового автомобиля;',--8
        'Котте́дж (от англ. cottage) — индивидуальный городской или сельский малоэтажный (обычно двухэтажный) жилой дом с небольшим участком прилегающей земли[1] для постоянного или временного проживания одной нуклеарной семьи. Первый этаж занимают такие помещения как гостиная, кухня, санузел, котельная, часто гараж для легкового автомобиля;',--8
        'Котте́дж (от англ. cottage) — индивидуальный городской или сельский малоэтажный (обычно двухэтажный) жилой дом с небольшим участком прилегающей земли[1] для постоянного или временного проживания одной нуклеарной семьи. Первый этаж занимают такие помещения как гостиная, кухня, санузел, котельная, часто гараж для легкового автомобиля;',--8
        'Возникновению электроники предшествовало открытие и изучение электричества, электромагнетизма, а далее изобретение радио. Поскольку радиопередатчики сразу же нашли применение (в первую очередь на кораблях и в военном деле), для них потребовалась элементная база, созданием и изучением которой и занялась электроника. Элементная база первого поколения была основана на электронных лампах. Соответственно получила развитие вакуумная электроника. Её развитию способствовало также изобретение телевидения и радаров, которые нашли широкое применение во время Второй мировой войны[2][3].Но электронные лампы обладали существенными недостатками. ',--9
        'Аудиотехника (звуковая техника, звукотехника, аудиоэлектроника) — аппаратура (магнитофоны, ревербераторы, микшеры, усилители, ресиверы и пр.) и устройства (микрофоны, динамики и пр.), предназначенные для записи и воспроизведения аудио (звука).',--10
        'В середине 1980-х годов в СССР начали выпускать первые бытовые VHS- видеомагнитофоны «Электроника ВМ-12», которые стоили 1200 рублей (7-10 средних зарплат того времени[13]), но были дефицитным товаром и продавались по предварительной записи. Существовало даже такое понятие, как очередь на видеомагнитофон.',--10
        'Игрова́я приста́вка (игровая консоль) — специализированное электронное устройство, предназначенное для видеоигр; для таких устройств, в отличие от персональных компьютеров, запуск и воспроизв',--11
        'Игрова́я приста́вка (игровая консоль) — специализированное электронное устройство, предназначенное для видеоигр; для таких устройств, в отличие от персональных компьютеров, запуск и воспроизв',--11
        'Игрова́я приста́вка (игровая консоль) — специализированное электронное устройство, предназначенное для видеоигр; для таких устройств, в отличие от персональных компьютеров, запуск и воспроизв',--11
        'Насто́льный (стационарный) компью́тер, дескто́п (англ. desktop computer) — стационарный персональный компьютер, предназначенный для работы в офисе и дома. Термин обычно используется для того, чтобы обозначить вид компьютера и отличить его от компьютеров других типов, например портативного компьютера, карманного компьютера, встроенного компьютера или сервера. Как правило, состоит из монитора, системного блока, мыши, клавиатуры и звукогарнитуры',--12
        'Переносной компьютер, в корпусе которого объединены типичные компоненты ПК, включая дисплей, клавиатуру и устройство указания (обычно сенсорная панель или тачпад), а также аккумуляторные батареи. Ноутбуки отличаются небольшими размерами и весом, время автономной работы ноутбуков варьируется в пределах от 2 до 15 часов.',--13
        'Интернет-планшет (англ. Internet tablet или Web tablet — Веб-планшет, или Pad tablet — Pad-планшет (Блокнотный планшет), или Web-pad — Веб-блокнот, или Surfpad — Веб-сёрфинг-блокнот) — мобильный компьютер, относящийся к типу планшетных компьютеров с диагональю экрана от 7 до 12 дюймов, построенный на аппаратной платформе того же класса, что и платформа для смартфонов.',--14
        'Электро́нная кни́га (Electronic book; e-book; eBook) — версия книги, хранящаяся в электронном виде, и показываемая на экране, в цифровом формате. Данное понятие применяется как для произведений, представленных в цифровой форме, так и в отношении устройств, используемых для их прочтения.',--14
        'С точки зрения экономики и общества возможность осуществления телефонных переговоров рассматривается как благо и важное условие комфортной жизни человека. Существует область науки и техники, связанная с изучением направлений развития телефонной связи, она получила название телефонии.',--15
        'Комплементарные блага (взаимодополняющие товары) — блага, совместное потребление которых является для агента более предпочтительным, чем потребление каждого', --16
        'Фотоаппара́т (фотографи́ческий аппара́т, фотока́мера) — устройство для регистрации неподвижных изображений (получения фотографий). Запись изображения в фотоаппарате осуществляется фотохимическим способом при воздействии света на светочувствительный фотоматериал. Получаемое таким способом скрытое изображение преобразуется в видимое при лабораторной обработке. В цифровом фотоаппарате фотофиксация происходит путём фотоэлектрического преобразования оптического изображения в электрический сигнал, цифровые данные о котором сохраняются на энергонезависимом носителе.' --17
        ]),
    -- price
    unnest(array[200.2,3432.65,45.756,56554.87,687.4,7432.5,8432.6,9213.32,10.432,11423.32,12432.23,13432.543,14.543,15543.6,16543.4,17453.54,18.543,19543.543,20543.4,16543.4,17453.54,18.543,19543.543,20543.4,19543.543]),
    -- created_at
    now(),
    -- status
    0,
    -- category_id
    add_to_category(unnest(array[2,2,3,3,4,4,6,6,7,8,8,8,10,10,10,11,11,11,12,13,14,14,15,16,17]))
;

