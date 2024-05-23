package net.cactusthorn.helidonmp.demo.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

/**
 * A Pokemon entity class. A Pokemon is represented as a triple of an
 * ID, a name and a type.
 *
 * Pokemon, and Pokemon character names are trademarks of Nintendo.
 */
@Entity
@Table(name = "POKEMON")
public class Pokemon {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private int id;

    @Basic(optional = false)
    @Column(name = "NAME", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinFetch(JoinFetchType.INNER)
    private PokemonType pokemonType;

    @Transient
    private int type;

    @PostLoad
    void postLoad() {
      type = pokemonType.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @ManyToOne
    @JoinFetch(JoinFetchType.INNER)
    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemonType = pokemonType;
        this.type = pokemonType.getId();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
      return "Pokemon [id=" + id + ", name=" + name + ", pokemonType=" + pokemonType + ", type=" + type + "]";
    }
}
