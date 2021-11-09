package si.fri.rso.polnilnice.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "ocene")
@NamedQueries(value =
        {
                @NamedQuery(name = "OcenaEntity.getAll",
                        query = "SELECT ocena FROM OcenaEntity ocena"),

                @NamedQuery(name = "OcenaEntity.getByPolnilnicaId",
                        query = "SELECT ocena FROM OcenaEntity ocena WHERE ocena.polnilnica.id = :polnilnicaId"),

                @NamedQuery(name = "OcenaEntity.getByUserId",
                        query = "SELECT ocena FROM OcenaEntity ocena WHERE ocena.userId = :userId")

        })

public class OcenaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "ocena")
    private Integer ocena;

    @Column(name = "besedilo")
    private String besedilo;

    @ManyToOne
    @JoinColumn(name = "polnilnica_id")
    private PolnilnicaEntity polnilnica;

    // ============= Getters and Setters =============

    public void setPolnilnica(PolnilnicaEntity polnilnica) {
        this.polnilnica = polnilnica;
    }

    public PolnilnicaEntity getPolnilnica() {
        return polnilnica;
    }

    public void setPolnilnice(PolnilnicaEntity polnilnice) {
        this.polnilnica = polnilnica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getBesedilo() {
        return besedilo;
    }

    public void setBesedilo(String besedilo) {
        this.besedilo = besedilo;
    }
}
