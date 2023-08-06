package com.epam.esm.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@DynamicUpdate
@Entity
@Table(name = "GIFT_CERTIFICATE")
public class Certificate {
    @Id
    @Column(name = "CERTIFICATE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CERTIFICATE_NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Timestamp lastUpdateDate;

    @Column(name = "DURATION")
    private Duration duration;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "CERTIFICATE_TAG",
            joinColumns = {@JoinColumn(name = "CERTIFICATE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    private List<Tag> tags;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Double.compare(that.price, price) == 0 &&
                duration == that.duration &&
                id.equals(that.id) &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                createDate.equals(that.createDate) &&
                lastUpdateDate.equals(that.lastUpdateDate) &&
                tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, createDate, lastUpdateDate, duration, tags);
    }

}
