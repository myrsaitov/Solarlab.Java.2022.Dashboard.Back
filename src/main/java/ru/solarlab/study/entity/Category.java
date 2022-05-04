package ru.solarlab.study.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solarlab.study.dto.CategoryStatus;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Entity /* Указывает, что данный бин (класс) является сущностью */
@Table(name = "category") /* указывает на имя таблицы, 
    которая будет отображаться в этой сущности */
@Schema(description = "Сущность категории")
public class Category {

    /**
     * Идентификатор сущности
     */
    @Id /* Является первичным ключом текущего объекта - 
        полем в таблице, которое однозначно идентифицирует 
        каждую строку/запись в таблице базы данных. */
    @Column(name = "id", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @SequenceGenerator(
            allocationSize = 1, // = INCREMENT in SQL
            name = "hibernate_sequence_category_generator",
            sequenceName="hibernate_sequence_category")
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
    private Long id;  //SQL: MAXVALUE 9223372036854775807 -- Long.MAX_VALUE

    /**
     * Дата и время создания категории
     */
    @Column(name = "created_at", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Дата и время создания категории")
    private OffsetDateTime createdAt;

    /**
     * Дата и время обновления категории
     */
    @Column(name = "updated_at")
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Дата и время обновления категории")
    private OffsetDateTime updatedAt;

    /**
     * Имя категории
     */
    @Column(name = "name", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Имя категории")
    private String name;

    /**
     * Статус категории
     */
    @Column(name = "status", nullable = false)
        /* Указывает на имя колонки, в которой отображается свойство сущности. */
    @Schema(description = "Статус")
    private CategoryStatus status;

    /**
     * Родительская категория
     */
    @JsonBackReference /* Для предотвращения StackOverFlow Error */
    @ManyToOne(fetch = FetchType.LAZY) /* LAZY: Запись извлекается
        только по требованию, т.е. когда нам нужны данные */
    @Schema(description = "Статус")
    private Category parentCategory;

    /**
     * Коллекция дочерних категорий
     */
    @JsonManagedReference /* Для предотвращения StackOverFlow Error */
    @OneToMany(
            mappedBy = "parentCategory", /* Указывает на поле объекта,
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
    @Schema(description = "Коллекция дочерних категорий")
    private Set<Category> subCategories = new HashSet<>();

    /**
     * Метод, добавляет подкатегорию к категории
     * @param subCategory Подкатегория
     */
    public void addSubCategory(Category subCategory) {

        subCategories.add(subCategory);
        subCategory.setParentCategory(this);

    }

    /**
     * Метод, удаляет подкатегорию у категории
     * @param subCategory Подкатегория
     */
    public void removeSubCategory(Category subCategory) {

        subCategories.remove(subCategory);
        subCategory.setParentCategory(null);

    }

    /**
     * Коллекция объявлений, принадлежащих данной категории
     */
    @JsonManagedReference /* Для предотвращения StackOverFlow Error */
    @OneToMany(
            mappedBy = "category", /* Указывает на поле объекта,
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
    @Schema(description = "Коллекция объявлений, принадлежащих данной категории")
    private Set<Advertisement> advertisements = new HashSet<>();
        /* LIST is a type of ordered collection that maintains 
           the elements in insertion order while Set is a type 
           of unordered collection so elements are not maintained any order.

           LIST allows duplicates while SET doesn't allow duplicate elements.
           All the elements of a SET should be unique if you try to insert the
           duplicate element in SET it would replace the existing value.
        
           LIST is an ordered sequence of elements, 
           however SET is distinct list of element which 
           is unordered. So, use LIST for storing non-unique 
           objects as per insertion order and use SET for 
           storing unique objects in random order
           
           https://net-informations.com/java/cjava/list.htm */

    /**
     * Метод, добавляет объявление в категорию
     * @param advertisement Сущность объявления
     */
    public void addAdvertisement(Advertisement advertisement) {

        advertisements.add(advertisement);
        advertisement.setCategory(this);

    }

    /**
     * Метод, удаляет объявление из категории
     * @param advertisement Сущность объявления
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