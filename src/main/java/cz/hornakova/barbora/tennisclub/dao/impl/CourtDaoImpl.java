package cz.hornakova.barbora.tennisclub.dao.impl;

import cz.hornakova.barbora.tennisclub.dao.CourtDao;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CourtDaoImpl implements CourtDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Court> getAll() {
        return em.createQuery(
                        """
                           SELECT c
                           FROM Court c
                           WHERE c.deleted = false
                           """,
                        Court.class)
                .getResultList();
    }

    @Override
    public Optional<Court> getById(Long id) {
        Court court = em.find(Court.class, id);

        if (court == null || court.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(court);
    }

    @Override
    public Court save(Court court) {
        //        if (customer.getId() == null) {
        em.persist(court);
        return court;
        //        }
    }

    @Override
    public void update(Court court) {
        em.merge(court);
    }

    @Override
    public void delete(Court court) {
        court.setDeleted(true);

        em.merge(court);
    }
}
