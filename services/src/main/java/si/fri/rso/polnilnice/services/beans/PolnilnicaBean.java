
package si.fri.rso.polnilnice.services.beans;

        import javax.enterprise.context.RequestScoped;
        import javax.inject.Inject;
        import javax.persistence.EntityManager;
        import javax.persistence.TypedQuery;
        import java.util.List;
        import java.util.logging.Logger;
        import java.util.stream.Collectors;

        import si.fri.rso.polnilnice.lib.Polnilnica;
        import si.fri.rso.polnilnice.models.converters.PolnilnicaConverter;
        import si.fri.rso.polnilnice.models.entities.PolnilnicaEntity;


@RequestScoped
public class PolnilnicaBean {

    private Logger log = Logger.getLogger(PolnilnicaBean.class.getName());

    @Inject
    private EntityManager em;


    /**  GET BY ID **/
    public Polnilnica getPolnilnicaById(Integer id) {
        TypedQuery<PolnilnicaEntity> query = em.createNamedQuery(
                "PolnilnicaEntity.getById", PolnilnicaEntity.class);
        query.setParameter("id", id);

        List<PolnilnicaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return  null;
        }

        return PolnilnicaConverter.toDto(resultList.get(0));
    }

    /**  GET ALL (LIST) **/
    public List<Polnilnica> getPolnilnice() {
        TypedQuery<PolnilnicaEntity> query = em.createNamedQuery(
                "PolnilnicaEntity.getAll", PolnilnicaEntity.class);

        List<PolnilnicaEntity> resultList = query.getResultList();

        return resultList.stream().map(PolnilnicaConverter::toDto).collect(Collectors.toList());
    }

    /**  POST **/
    public Polnilnica createPolnilnica(Polnilnica polnilnica) {

        PolnilnicaEntity polnilnicaEntity = PolnilnicaConverter.toEntity(polnilnica);

        try {
            beginTx();
            em.persist(polnilnicaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }
        if (polnilnicaEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return PolnilnicaConverter.toDto(polnilnicaEntity);
    }

    /**  PUT **/
    public Polnilnica putPolnilnica(Integer polnilnicaId, Polnilnica polnilnica) {

        // Find existing polnilnica by id in persistance layer
        PolnilnicaEntity polnilnicaEntity = em.find(PolnilnicaEntity.class, polnilnicaId);

        // If it doesn't exist cancel
        if (polnilnicaEntity == null) {
            return null;
        }

        PolnilnicaEntity updatedPolnilnicaEntity = PolnilnicaConverter.toEntity(polnilnica);
        Double lng = updatedPolnilnicaEntity.getLokacijaLng();
        Double lat = updatedPolnilnicaEntity.getLokacijaLat();

        // Swap the fields
        if (updatedPolnilnicaEntity.getIme() != null) {
            polnilnicaEntity.setIme(updatedPolnilnicaEntity.getIme());
        }
        // If lat/lng doesn't come in, it gets converted from null to 0.0 somewhere..
        // We check if its 0.0, cus there won't be a polnilnica attached to Null island anyway
        if (lat != null && lat != 0.0) {
            polnilnicaEntity.setLokacijaLat(lat);
        }

        if (lng != null && lng != 0.0) {
            polnilnicaEntity.setLokacijaLng(lng);
        }

        try {
            beginTx();
            polnilnicaEntity = em.merge(polnilnicaEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return PolnilnicaConverter.toDto(polnilnicaEntity);
    }

    /**  DELETE **/
    public boolean deletePolnilnica(Integer polnilnicaId) {
        PolnilnicaEntity polnilnicaEntity = em.find(PolnilnicaEntity.class, polnilnicaId);

        if (polnilnicaEntity != null) {
            try {
                beginTx();
                em.remove(polnilnicaEntity);
                commitTx();
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