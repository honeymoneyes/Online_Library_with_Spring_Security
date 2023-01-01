package Library.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Book name should be not empty!")
    @Size(min = 2, max = 100, message = "Name should be greater 2 and 100 characters!")
    private String name;
    @Column(name = "author")
    @NotEmpty(message = "Author name should be not empty!")
    @Size(min = 2, max = 100, message = "Name should be greater 2 and 100 characters!")
    private String author;
    @Column(name = "year")
    private int year;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Transient
    private boolean checkDate;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;
}
