package si.fri.rso.polnilnice.models.converters;

import si.fri.rso.polnilnice.lib.Ocena;
import si.fri.rso.polnilnice.models.entities.OcenaEntity;

public class OcenaConverter {

    public static Ocena toDto(OcenaEntity entity) {

        Ocena dto = new Ocena();
        // Map entity into dto
        dto.setId(entity.getId());
        dto.setOcena(entity.getOcena());
        dto.setBesedilo(entity.getBesedilo());
        dto.setUserId(entity.getUserId());
        return dto;

    }

    public static OcenaEntity toEntity(Ocena dto) {

        OcenaEntity entity = new OcenaEntity();
        // Map dto into entity
        if (dto.getId() != null) entity.setId(dto.getId());
        entity.setOcena(dto.getOcena());
        entity.setBesedilo(dto.getBesedilo());
        entity.setUserId(dto.getUserId());

        return entity;

    }

}
