package org.example.tables;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "movie", name = "film_text")
public class FIlmText {
    @Id
    @Column(name = "film_id")
    private Short id;

    @Column
    private String title;

    @Column(columnDefinition = "text")
    @Type(type = "text")
    private String description;

    @OneToOne
    @JoinColumn(name = "film_id")
    private Film film;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
