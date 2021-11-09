package si.fri.rso.polnilnice.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "termini")
@NamedQueries(value =
        {
                @NamedQuery(name = "TerminEntity.getAll",
                        query = "SELECT termin FROM TerminEntity termin"),

                @NamedQuery(name = "TerminEntity.getByPolnilnicaId",
                        query = "SELECT termin FROM TerminEntity termin WHERE termin.polnilnica.id = :polnilnicaId"),

                @NamedQuery(name = "TerminEntity.getByUserId",
                        query = "SELECT termin FROM TerminEntity termin WHERE termin.userId = :userId")
        })

public class TerminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "dateFrom")
    private Long dateFrom;

    @Column(name = "dateTo")
    private Long dateTo;

    @ManyToOne
    @JoinColumn(name = "polnilnica_id")
    private PolnilnicaEntity polnilnica;


    // ============= Getters and Setters =============

    public PolnilnicaEntity getPolnilnica() {
        return polnilnica;
    }

    public void setPolnilnica(PolnilnicaEntity polnilnica) {
        this.polnilnica = polnilnica;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {this.userId = userId;}

    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
