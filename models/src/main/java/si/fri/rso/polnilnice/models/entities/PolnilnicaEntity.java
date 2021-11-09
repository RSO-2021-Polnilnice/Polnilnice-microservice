package si.fri.rso.polnilnice.models.entities;

import si.fri.rso.polnilnice.lib.Ocena;
import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.models.converters.OcenaConverter;
import si.fri.rso.polnilnice.models.converters.TerminConverter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "polnilnice")
@NamedQueries(value =
        {
                @NamedQuery(name = "PolnilnicaEntity.getAll",
                        query = "SELECT polnilnica FROM PolnilnicaEntity polnilnica"),

                @NamedQuery(name = "PolnilnicaEntity.getById",
                        query = "SELECT polnilnica FROM PolnilnicaEntity polnilnica WHERE polnilnica.id = :id"),

        })
public class PolnilnicaEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;

        @Column(name = "ime")
        private String ime;

        @Column(name = "lokacijaLat")
        private double lokacijaLat;

        @Column(name = "lokacijaLng")
        private double lokacijaLng;

        @JsonbTransient
        @OneToMany(mappedBy = "polnilnica", cascade = CascadeType.ALL)
        private List<OcenaEntity> ocene;

        @JsonbTransient
        @OneToMany(mappedBy = "polnilnica", cascade = CascadeType.ALL)
        private List<TerminEntity> termini;


        // ============= Getters and Setters =============


        public Integer getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getIme() {
                return ime;
        }

        public void setIme(String ime) {
                this.ime = ime;
        }

        public Double getLokacijaLat() {
                return lokacijaLat;
        }

        public void setLokacijaLat(Double lokacijaLat) {
                this.lokacijaLat = lokacijaLat;
        }

        public Double getLokacijaLng() {
                return lokacijaLng;
        }

        public void setLokacijaLng(Double lokacijaLng) {
                this.lokacijaLng = lokacijaLng;
        }

        public List<Ocena> getOcene() {
                // Loop over list and convert from entity
                return ocene.stream().map(OcenaConverter::toDto).collect(Collectors.toList());
        }

        public void setOcene(List<OcenaEntity> ocene) {
                this.ocene = ocene;
        }

        public List<Termin> getTermini() {
                return termini.stream().map(TerminConverter::toDto).collect(Collectors.toList());
        }

        public void setTermini(List<TerminEntity> termini) {
                this.termini = termini;
        }


}
