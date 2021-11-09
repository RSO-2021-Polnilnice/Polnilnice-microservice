
package si.fri.rso.polnilnice.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import si.fri.rso.polnilnice.lib.Ocena;
import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.models.converters.OcenaConverter;
import si.fri.rso.polnilnice.models.converters.TerminConverter;
import si.fri.rso.polnilnice.models.entities.OcenaEntity;
import si.fri.rso.polnilnice.models.entities.PolnilnicaEntity;
import si.fri.rso.polnilnice.models.entities.TerminEntity;


@RequestScoped
public class OcenaBean {

    private Logger log = Logger.getLogger(PolnilnicaBean.class.getName());

    @Inject
    private EntityManager em;


    /**  GET BY USER ID **/
    public List<Ocena> getOceneByUserId(Integer userId) {
        TypedQuery<OcenaEntity> query = em.createNamedQuery(
                "OcenaEntity.getByUserId",  OcenaEntity.class);
        query.setParameter("userId", userId);

        List<OcenaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return  null;
        }
        return resultList.stream().map(OcenaConverter::toDto).collect(Collectors.toList());
    }
    /**  GET BY POLNILNICA ID **/
    public List<Ocena> getOceneByPolnilnicaId(Integer polnilnicaId) {
        TypedQuery<OcenaEntity> query = em.createNamedQuery(
                "OcenaEntity.getByPolnilnicaId",  OcenaEntity.class);
        query.setParameter("polnilnicaId", polnilnicaId);

        List<OcenaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return  null;
        }
        return resultList.stream().map(OcenaConverter::toDto).collect(Collectors.toList());
    }

    /** POST **/
    /** Create a new "ocena" for "polnilnica" with provided ID **/
    public Ocena createOcenaForPolnilnica(Integer polnilnicaId, Ocena ocena) {

        OcenaEntity ocenaEntity = OcenaConverter.toEntity(ocena);
        PolnilnicaEntity polnilnicaEntity= em.find(PolnilnicaEntity.class, polnilnicaId);
        ocenaEntity.setPolnilnica(polnilnicaEntity);

        try {
            beginTx();
            em.persist(ocenaEntity);
            commitTx();
            // Refresh polnilnica entity so it shows latest data
            em.refresh(polnilnicaEntity);
        }
        catch (Exception e) {
            rollbackTx();
        }
        if (ocenaEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return OcenaConverter.toDto(ocenaEntity);
    }

    /** DELETE **/
    public boolean deleteOcena(Integer ocenaId) {

        OcenaEntity ocenaEntity = em.find(OcenaEntity.class, ocenaId);

        if (ocenaEntity != null) {
            try {
                beginTx();
                em.remove(ocenaEntity);
                commitTx();
                // Refresh the entity so it dissapears from the termini list in this polnilnica
                PolnilnicaEntity polnilnicaEntity = em.find(PolnilnicaEntity.class, ocenaEntity.getPolnilnica().getId());
                em.refresh(polnilnicaEntity);
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    /**  TRANSACTION METHODS **/
    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}