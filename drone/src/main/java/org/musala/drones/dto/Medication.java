package org.musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    @Pattern(regexp = "[A-Za-z0-9_-]*")
    private String name;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "code")
    @Pattern(regexp = "[A-Z0-9_]*")
    private String code;
    @Column(name = "image_url")
    private String imageUrl;
    @JsonIgnore
    @OneToMany(
            mappedBy = "medication",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DroneMedicationLink> droneMedicationLinks = new HashSet<>();

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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<DroneMedicationLink> getDroneMedicationLinks() {
        return droneMedicationLinks;
    }

    public void setDroneMedicationLinks(Set<DroneMedicationLink> droneMedicationLinks) {
        this.droneMedicationLinks = droneMedicationLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(weight, that.weight) && Objects.equals(code, that.code) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, code, imageUrl);
    }
}
