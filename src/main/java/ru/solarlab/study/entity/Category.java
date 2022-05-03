package ru.solarlab.study.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solarlab.study.dto.CategoryStatus;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Entity /* Указывает, что данный бин (класс) является сущностью */
@Table(name = "CATEGORY") /* указывает на имя таблицы, 
    которая будет отображаться в этой сущности */
@Schema(description = "Сущность категории")
public class Category {

    /**
     * Идентификатор сущности
     */
    @Id /* Является первичным ключом текущего объекта - 
        полем в таблице, которое однозначно идентифицирует 
        каждую строку/запись в таблице базы данных. */
    @Column(name = "ID", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @SequenceGenerator(
            allocationSize = 1, // = INCREMENT in SQL
            name = "hibernate_sequence_category_generator",
            sequenceName="HIBERNATE_SEQUENCE_CATEGORY")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hibernate_sequence_category_generator")
        /* Указывает, что данное свойство будет создаваться
           согласно указанной стратегии: TABLE, SEQUENCE, IDENTITY, AUTO.
           IDENTITY: используется встроенный в БД тип данных столбца -
               identity - для генерации значения первичного ключа;
           SEQUENCE: используется последовательность – специальный 
               объект БД для генерации уникальных значений;
           TABLE: для генерации уникального значения используется 
               отдельная таблица, которая эмулирует последовательность.
               Когда требуется новое значение, JPA провайдер блокирует
               строку таблицы, обновляет хранящееся там значение и
               возвращает его обратно в приложение. Эта стратегия – 
               наихудшая по производительности и ее желательно избегать.
           AUTO: JPA провайдер решает, как генерировать 
               уникальные ID для сущности:
               - Попробует использовать стратегию SEQUENCE (производительность).
               - Если БД не поддерживает последовательности (например, MySQL),
                 то будет использоваться стратегия TABLE 
                 (или IDENTITY, в версии до 5.0). */
    @Schema(description = "Идентификатор")
    public Long id;  //SQL: MAXVALUE 9223372036854775807 -- Long.MAX_VALUE

    /**
     * Дата и время создания категории
     */
    @Column(name = "CREATED_AT", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Дата и время создания категории")
    public OffsetDateTime createdAt;

    /**
     * Дата и время обновления категории
     */
    @Column(name = "UPDATED_AT")
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Дата и время обновления категории")
    public OffsetDateTime updatedAt;

    /**
     * Имя категории
     */
    @Column(name = "NAME", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Имя категории")
    public String name;

    /**
     * Статус категории
     */
    @Column(name = "STATUS", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Статус")
    public CategoryStatus status;

    /**
     * Коллекция объявлений, принадлежащих данной категории
     */
    @JsonManagedReference /* Для предотвращения StackOverFlow Error */
    @OneToMany(
            mappedBy = "category", /* Указывает на поле объекта, которым владеет,
                которое указывает на объект-владелец */
            fetch = FetchType.LAZY, /* LAZY: Запись извлекается
                только по требованию, т.е. когда нам нужны данные */
            //cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            cascade = CascadeType.ALL, /* CascadeType.ALL означает,
                что необходимо выполнять каскадно сразу все операции:
                    CascadeType.PERSIST
                    CascadeType.MERGE
                    CascadeType.REMOVE
                    CascadeType.REFRESH
                    CascadeType.DETACH */
            orphanRemoval = true) /* true = при удалении сущности из списка,
                она удаляется и из базы. */
    //@JsonIgnoreProperties("category")
    //@JsonIgnore
    public List<Advertisement> advertisements = new ArrayList<>();

    /**
     * Метод, добавляет объявление в категорию
     * @param advertisement
     */
    public void addAdvertisement(Advertisement advertisement) {

        advertisements.add(advertisement);
        advertisement.setCategory(this);

    }

    /**
     * Метод, удаляет объявление из категории
     * @param advertisement
     */
    public void removeAdvertisement(Advertisement advertisement) {

        advertisements.remove(advertisement);
        advertisement.setCategory(null);

    }

}

// https://habr.com/ru/post/254773/ (Нормализация отношений, 6 нормальных форм)
// https://www.baeldung.com/java-zoneddatetime-offsetdatetime
// https://javastudy.ru/spring-data-jpa/annotation-persistence/
// https://habr.com/ru/company/haulmont/blog/653843/
// https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#identifiers-generators-table
// https://www.baeldung.com/hibernate-one-to-many
// https://sysout.ru/otnoshenie-onetomany-v-hibernate-i-spring/
// https://andreyex.ru/bazy-dannyx/uchebnoe-posobie-po-sql/sql-primary-key-pervichnyj-klyuch/
// https://webformyself.com/sql-vneshnij-klyuch/
// https://sysout.ru/kak-rabotaet-orphanremoval/
// https://sysout.ru/tipy-cascade-primer-na-hibernate-i-spring-boot/
// https://ask-dev.ru/info/18433/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api