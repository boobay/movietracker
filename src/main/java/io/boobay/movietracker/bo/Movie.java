package io.boobay.movietracker.bo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Created by boobay on 7/11/15.
 */
@Entity(name="movies")
public class Movie  {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="movies_id_seq")
    @SequenceGenerator(name="movies_id_seq", sequenceName="movies_id_seq", allocationSize=1)
    private Long id;
    @NotEmpty
    private String name;
    private Boolean owned;

    public Boolean getOwned() {
        return owned;
    }

    public void setOwned(Boolean owned) {
        this.owned = owned;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
