package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bundle", schema = "db2_project")
@NamedQueries({
        @NamedQuery(name = "BundleEntity.findBundleByTitle", query = "SELECT b FROM BundleEntity b WHERE b.title = :title"),
        @NamedQuery(name = "BundleEntity.retrieveAll", query = "SELECT b FROM BundleEntity b")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "BundleEntity.findServicesById", query = "SELECT serviceId FROM servicesinbundle s WHERE s.bundleId = ?1"),
        @NamedNativeQuery(name = "BundleEntity.findValidityPeriodsById", query = "SELECT validityPeriodId FROM validityperiodsperbundle vp WHERE vp.bundleId = ?1"),
        @NamedNativeQuery(name = "BundleEntity.findAvailableOptionalsById", query = "SELECT optionalId FROM availableoptionalsforbundle ao WHERE ao.bundleId = ?1")
})
public class BundleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(name = "availableoptionalsforbundle",
            joinColumns = @JoinColumn(name = "bundleId"),
            inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private Collection<OptionalProductEntity> availableOptionals;

    @ManyToMany
    @JoinTable(name = "validityperiodsperbundle",
            joinColumns = @JoinColumn(name = "bundleId"),
            inverseJoinColumns = @JoinColumn(name = "validityPeriodId"))
    private Collection<ValidityPeriodEntity> availableValidityPeriods;

    @ManyToMany
    @JoinTable(name = "servicesinbundle",
            joinColumns = @JoinColumn(name = "bundleId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private Collection<ServiceEntity> servicesInBundle;

    public BundleEntity(String title) {
        this.title = title;
    }

    public BundleEntity(String title, List<ServiceEntity> services, List<ValidityPeriodEntity> periods, List<OptionalProductEntity> optionals) {
        this.title = title;
        this.servicesInBundle = services;
        this.availableValidityPeriods = periods;
        this.availableOptionals = optionals;
    }

    public BundleEntity() {

    }

    public Collection<OptionalProductEntity> getAvailableOptionals() {
        return availableOptionals;
    }

    public void setAvailableOptionals(Collection<OptionalProductEntity> availableOptionals) {
        this.availableOptionals = availableOptionals;
    }

    public Collection<ValidityPeriodEntity> getAvailableValidityPeriods() {
        return availableValidityPeriods;
    }

    public void setAvailableValidityPeriods(Collection<ValidityPeriodEntity> availableValidityPeriods) {
        this.availableValidityPeriods = availableValidityPeriods;
    }

    public Collection<ServiceEntity> getServicesInBundle() {
        return servicesInBundle;
    }

    public void setServicesInBundle(Collection<ServiceEntity> servicesInBundle) {
        this.servicesInBundle = servicesInBundle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {

        BundleEntity that = (BundleEntity) o;

        assert availableOptionals != null && that.availableOptionals != null;

        return title.equals(that.title)
                && servicesInBundle.containsAll(that.servicesInBundle)
                && that.servicesInBundle.containsAll(servicesInBundle)
                && availableValidityPeriods.containsAll(that.availableValidityPeriods)
                && that.availableValidityPeriods.containsAll(availableValidityPeriods)
                && availableOptionals.containsAll(that.availableOptionals)
                && that.availableOptionals.containsAll(availableOptionals);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BundleEntity{" +
                "title='" + title + '\'' +
                '}';
    }
}
