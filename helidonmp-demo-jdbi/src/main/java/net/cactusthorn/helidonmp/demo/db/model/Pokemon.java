package net.cactusthorn.helidonmp.demo.db.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Pokemon {

  private int id;
  private String name;
  private int type;

  @JsonCreator
  public Pokemon(
  // @formatter:off
      @ColumnName("ID") @JsonProperty("id") int id,
      @ColumnName("NAME") @JsonProperty("name") String name,
      @ColumnName("POKEMONTYPE_ID") @JsonProperty("type") int type) {
  // @formatter:on
    this.id = id;
    this.name = name;
    this.type = type;
  }

  @JsonGetter("id")
  public int id() {
    return id;
  }

  @JsonGetter("name")
  public String name() {
    return name;
  }

  @JsonGetter("type")
  public int type() {
    return type;
  }

  @Override
  public String toString() {
    return "Pokemon [id=" + id + ", name=" + name + ", type=" + type + "]";
  }

}
