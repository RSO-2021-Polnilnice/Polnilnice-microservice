package si.fri.rso.polnilnice.models.converters;


import si.fri.rso.polnilnice.lib.Polnilnica;
import si.fri.rso.polnilnice.models.entities.PolnilnicaEntity;

import java.util.stream.Collectors;

public class PolnilnicaConverter {

    public static Polnilnica toDto(PolnilnicaEntity entity) {

        Polnilnica dto = new Polnilnica();
        // Map dto into entity
        dto.setId(entity.getId());
        dto.setIme(entity.getIme());
        dto.setLokacijaLat(entity.getLokacijaLat());
        dto.setLokacijaLng(entity.getLokacijaLng());
        dto.setOcene(entity.getOcene());
        dto.setTermini(entity.getTermini());
        return dto;

    }

    public static PolnilnicaEntity toEntity(Polnilnica dto) {

        PolnilnicaEntity entity = new PolnilnicaEntity();
        // Map dto into entity
        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getIme() != null )  entity.setIme(dto.getIme());
        if (dto.getLokacijaLat() != null) entity.setLokacijaLat(dto.getLokacijaLat());
        if (dto.getLokacijaLng() != null) entity.setLokacijaLng(dto.getLokacijaLng());
        if (dto.getOcene() != null) entity.setOcene(dto.getOcene().stream().map(OcenaConverter::toEntity).collect(Collectors.toList()));
        if (dto.getTermini() != null) entity.setTermini(dto.getTermini().stream().map(TerminConverter::toEntity).collect(Collectors.toList()));

        return entity;

    }

}
