package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Collection;

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
    private Collection<OptionalProductEntity> availableValidityPeriods;

    @ManyToMany
    @JoinTable(name = "servicesinbundle",
            joinColumns = @JoinColumn(name = "bundleId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private Collection<BundleEntity> servicesInBundle;

    public BundleEntity(String title) {
        this.title = title;
    }

    public BundleEntity() {

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BundleEntity that = (BundleEntity) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
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
