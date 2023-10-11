package be.condorcet.api3projet2_2.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Adresse {
    private int id;
    private int cp;
    private String localite;
    private String rue;
    private String num;

    public Adresse(int id, int cp, String localite, String rue, String num) {
        this.id = id;
        this.cp = cp;
        this.localite = localite;
        this.rue = rue;
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return cp == adresse.cp && Objects.equals(localite, adresse.localite) && Objects.equals(rue, adresse.rue) && Objects.equals(num, adresse.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cp, localite, rue, num);
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", cp=" + cp +
                ", localite='" + localite + '\'' +
                ", rue='" + rue + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
