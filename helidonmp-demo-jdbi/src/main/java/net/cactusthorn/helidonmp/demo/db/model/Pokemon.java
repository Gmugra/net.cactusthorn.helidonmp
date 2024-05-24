package net.cactusthorn.helidonmp.demo.db.model;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Pokemon {

  private int id;
  private String name;
  private int type;

  @JsonCreator
  @ConstructorProperties({"ID", "NAME", "POKEMONTYPE_ID"}) //JDBI
  public Pokemon(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("type") int type) {
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
