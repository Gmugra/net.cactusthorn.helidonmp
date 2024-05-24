package net.cactusthorn.helidonmp.demo.db.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import com.fasterxml.jackson.annotation.JsonGetter;

public final class PokemonType {

  private int id;
  private String name;

  public PokemonType(@ColumnName("ID") int id, @ColumnName("NAME") String name) {
    this.id = id;
    this.name = name;
  }

  @JsonGetter("id")
  public int id() {
    return id;
  }

  @JsonGetter("name")
  public String name() {
    return name;
  }

  @Override
  public String toString() {
    return "PokemonType [id=" + id + ", name=" + name + "]";
  }

}
