package sk.upjs.micma.epdat_client_app.models;

import java.io.Serializable;
import java.util.Objects;

public class Record implements Serializable {
    private Long id;
    private int endopolyploidy;
    private String chromosomeNumber;
    private int ploidyLevel;
    private float number;
    private String source;
    private String indexType;
    private String tissue;
    private String createdAt;
    private String updatedAt;


    public Record(Long id, int endopolyploidy, String chromosomeNumber, int ploidyLevel, float number, String indexType, String tissue,  String source, String createdAt, String updatedAt) {
        this.id = id;
        this.endopolyploidy = endopolyploidy;
        this.chromosomeNumber = chromosomeNumber;
        this.ploidyLevel = ploidyLevel;
        this.number = number;
        this.indexType = indexType;
        this.tissue = tissue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.source = source;

    }

    public Record() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record rec = (Record) o;
        return id == rec.id && endopolyploidy == rec.endopolyploidy && chromosomeNumber.equals(rec.chromosomeNumber)
                && ploidyLevel == rec.ploidyLevel && number == rec.number && indexType.equals(rec.indexType) && tissue.equals(rec.tissue) && source.equals(rec.source);
    }

    @Override
    public String toString() {
        return (getId() + "_" + getEndopolyploidy() + "_" + getNumber() + "_" + getPloidyLevel() + "_" + getNumber() + " T: "+ getTissue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endopolyploidy, chromosomeNumber, ploidyLevel, number, indexType, tissue);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEndopolyploidy() {
        return endopolyploidy;
    }

    public void setEndopolyploidy(int endopolyploidy) {
        this.endopolyploidy = endopolyploidy;
    }

    public String getChromosomeNumber() {
        return chromosomeNumber;
    }

    public void setChromosomeNumber(String chromosomeNumber) {
        this.chromosomeNumber = chromosomeNumber;
    }

    public int getPloidyLevel() {
        return ploidyLevel;
    }

    public void setPloidyLevel(int ploidyLevel) {
        this.ploidyLevel = ploidyLevel;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getTissue() {
        return tissue;
    }

    public void setTissue(String tissue) {
        this.tissue = tissue;
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
    public  String getSource(){return source;}
    public void setSource(){this.source = source;}
}
