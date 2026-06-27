package cz.hornakova.barbora.tennisclub.dao.impl;

import cz.hornakova.barbora.tennisclub.dao.SurfaceTypeDao;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SurfaceTypeDaoImpl implements SurfaceTypeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SurfaceType> getAll() {
        return em.createQuery(
                        """
                           SELECT st
                           FROM SurfaceType st
                           WHERE st.deleted = false
                           """,
                        SurfaceType.class)
                .getResultList();
    }

    @Override
    public Optional<SurfaceType> getById(Long id) {
        SurfaceType surfaceType = em.find(SurfaceType.class, id);

        if (surfaceType == null || surfaceType.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(surfaceType);
    }

    @Override
    public SurfaceType save(SurfaceType surfaceType) {
        if (surfaceType.getId() == 0) {
            em.persist(surfaceType);
            return surfaceType;
        }

        return em.merge(surfaceType);
    }

    @Override
    public void delete(SurfaceType surfaceType) {
        surfaceType.setDeleted(true);

        em.merge(surfaceType);
    }
}
