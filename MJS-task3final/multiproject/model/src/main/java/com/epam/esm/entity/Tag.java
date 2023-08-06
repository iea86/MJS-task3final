package com.epam.esm.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TAG")
public class Tag {
    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OPERATION")
    private String operation;

    @Column(name = "TIMESTAMP")
    private Long timestamp;

    @Column(name = "TAG_NAME")
    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "CERTIFICATE_TAG",
            joinColumns = {@JoinColumn(name = "TAG_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CERTIFICATE_ID")})
    private List<Certificate> certificates;

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(operation, tag.operation) &&
                Objects.equals(timestamp, tag.timestamp) &&
                Objects.equals(name, tag.name) &&
                Objects.equals(certificates, tag.certificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, timestamp, name, certificates);
    }

    @PrePersist
    public void onPrePersist() {
        audit("INSERT");
    }

    @PreRemove
    public void onPreRemove() {
        audit("DELETE");
    }

    private void audit(String operation) {
        setOperation(operation);
        setTimestamp((new Date()).getTime());
    }

}
