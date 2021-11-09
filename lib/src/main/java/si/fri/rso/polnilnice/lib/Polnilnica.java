package si.fri.rso.polnilnice.lib;

import java.util.List;

public class Polnilnica {

    private Integer id;
    private String ime;
    private Double lokacijaLat;
    private Double lokacijaLng;
    private List<Termin> termini;
    private List<Ocena> ocene;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return this.ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Double getLokacijaLat() {
        return this.lokacijaLat;
    }

    public void setLokacijaLat(Double lokacijaLat) {
        this.lokacijaLat = lokacijaLat;
    }

    public Double getLokacijaLng() {
        return this.lokacijaLng;
    }

    public void setLokacijaLng(Double lokacijaLng) {
        this.lokacijaLng = lokacijaLng;
    }

    public List<Termin> getTermini() {
        return termini;
    }

    public void setTermini(List<Termin> termini) {
        this.termini = termini;
    }

    public List<Ocena> getOcene() {
        return ocene;
    }

    public void setOcene(List<Ocena> ocene) {
        this.ocene = ocene;
    }



}
