
package si.fri.rso.polnilnice.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import si.fri.rso.polnilnice.lib.Polnilnica;
import si.fri.rso.polnilnice.lib.Termin;
import si.fri.rso.polnilnice.models.converters.PolnilnicaConverter;
import si.fri.rso.polnilnice.models.converters.TerminConverter;
import si.fri.rso.polnilnice.models.entities.PolnilnicaEntity;
import si.fri.rso.polnilnice.models.entities.TerminEntity;


@RequestScoped
public class TerminBean {

    private Logger log = Logger.getLogger(PolnilnicaBean.class.getName());

    @Inject
    private EntityManager em;


    /**  GET BY USER ID **/
    public List<Termin> getTerminiByUserId(Integer userId) {
        TypedQuery<TerminEntity> query = em.createNamedQuery(
                "TerminEntity.getByUserId",  TerminEntity.class);
        query.setParameter("userId", userId);

        List<TerminEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return  null;
        }
        return resultList.stream().map(TerminConverter::toDto).collect(Collectors.toList());
    }
    /**  GET BY POLNILNICA ID **/
    public List<Termin> getTerminiByPolnilnicaId(Integer polnilnicaId) {
        TypedQuery<TerminEntity> query = em.createNamedQuery(
                "TerminEntity.getByPolnilnicaId",  TerminEntity.class);
        query.setParameter("polnilnicaId", polnilnicaId);

        List<TerminEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return  null;
        }
        return resultList.stream().map(TerminConverter::toDto).collect(Collectors.toList());
    }

    /** POST **/
    /** Create a new "termin" for "polnilnica" with provided ID **/
    public Termin createTerminForPolnilnica(Integer polnilnicaId, Termin termin) {

        TerminEntity terminEntity = TerminConverter.toEntity(termin);
        PolnilnicaEntity polnilnicaEntity= em.find(PolnilnicaEntity.class, polnilnicaId);
        terminEntity.setPolnilnica(polnilnicaEntity);

        try {
            beginTx();
            em.persist(terminEntity);
            commitTx();
            // Refresh polnilnica entity so it shows latest data
            em.refresh(polnilnicaEntity);
        }
        catch (Exception e) {
            rollbackTx();
        }
        if (terminEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return TerminConverter.toDto(terminEntity);
    }

    /** DELETE **/
    public boolean deleteTermin(Integer terminId) {

        TerminEntity terminEntity = em.find(TerminEntity.class, terminId);

        if (terminEntity != null) {
            try {
                beginTx();
                em.remove(terminEntity);
                commitTx();
                // Refresh the entity so it dissapears from the termini list in this polnilnica
                PolnilnicaEntity polnilnicaEntity = em.find(PolnilnicaEntity.class, terminEntity.getPolnilnica().getId());
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