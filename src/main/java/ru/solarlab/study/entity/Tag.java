package ru.solarlab.study.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.solarlab.study.dto.TagStatus;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data /* @Data - это удобная сокращённая аннотация,
    которая содержит в себе возможности из @ToString, 
    @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Entity /* Указывает, что данный бин (класс) является сущностью */
@Table(name = "TAG") /* указывает на имя таблицы, 
    которая будет отображаться в этой сущности */
@Schema(description = "Сущность тага")
public class Tag {

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
            name = "hibernate_sequence_tag_generator",
            sequenceName="HIBERNATE_SEQUENCE_TAG")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hibernate_sequence_tag_generator")
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
     * Дата и время создания тага
     */
    @Column(name = "CREATED_AT", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Дата и время создания тага")
    public OffsetDateTime createdAt;

    /**
     * Дата и время обновления тага
     */
    @Column(name = "UPDATED_AT")
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Дата и время обновления тага")
    public OffsetDateTime updatedAt;

    /**
     * Текст тага
     */
    @Column(name = "TEXT", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Текст тага")
    public String text;

    /**
     * Статус тага
     */
    @Column(name = "STATUS", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Статус")
    public TagStatus status;

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