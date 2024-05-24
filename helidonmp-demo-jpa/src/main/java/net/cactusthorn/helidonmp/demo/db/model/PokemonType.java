package net.cactusthorn.helidonmp.demo.db.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "POKEMONTYPE")
public class PokemonType {

  @Id
  @Column(name = "ID", nullable = false, updatable = false)
  private int id;

  @Basic(optional = false)
  @Column(name = "NAME")
  private String name;

  public PokemonType() {
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

  @Override
  public String toString() {
    return "PokemonType [id=" + id + ", name=" + name + "]";
  }
}
