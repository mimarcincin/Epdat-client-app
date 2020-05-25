package sk.upjs.micma.epdat_client_app.models;

import java.io.Serializable;
import java.util.Objects;

public class Plant implements Serializable {

    private long id;
    private String family;
    private String genus;
    private String species;
    private String authority;
    private String notice;
    private String createdAt;
    private String updatedAt;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Plant() {

    }

    public Plant(long id, String family, String genus, String species, String authority, String notice, String createdAt, String updatedAt) {
        this.id = id;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.authority = authority;
        this.notice = notice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant spec = (Plant) o;
        return id == spec.id && family.equals(spec.family) && genus.equals(spec.genus)
                && species.equals(spec.species) && authority.equals(spec.authority);
    }

    @Override
    public String toString() {
        return (getId() + "_" + getFamily() + "_" + getGenus() + "_" + getSpecies() + "_" + getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, family, genus, species, authority);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
