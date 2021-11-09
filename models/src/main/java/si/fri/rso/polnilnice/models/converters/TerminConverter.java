package si.fri.rso.polnilnice.models.converters;

import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.models.entities.TerminEntity;

public class TerminConverter {

    public static Termin toDto(TerminEntity entity) {

        Termin dto = new Termin();
        // Map entity into dto
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setDateFrom(entity.getDateFrom());
        dto.setDateTo(entity.getDateTo());
        return dto;

    }

    public static TerminEntity toEntity(Termin dto) {

        TerminEntity entity = new TerminEntity();
        // Map dto into entity
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setDateFrom(dto.getDateFrom());
        entity.setDateTo(dto.getDateTo());

        return entity;

    }

}
