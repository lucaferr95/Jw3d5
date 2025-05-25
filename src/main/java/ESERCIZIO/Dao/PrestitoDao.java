package ESERCIZIO.Dao;

import ESERCIZIO.Entities.Prestito;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PrestitoDao {
        private EntityManager em;

        public PrestitoDao(EntityManager em) {
            this.em = em;
        }

        public void salvaPrestito(Prestito p) {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }

        public List<Prestito> prestitiPerUtente(int numeroTessera) {
            return em.createQuery("""
            SELECT p FROM Prestito p
            WHERE p.utente.numeroTessera = :num
        """, Prestito.class)
                    .setParameter("num", numeroTessera)
                    .getResultList();
        }

        public List<Prestito> prestitiScadutiNonRestituiti() {
            return em.createQuery("""
            SELECT p FROM Prestito p
            WHERE p.finePrestito < CURRENT_DATE AND p.restituzione IS NULL
        """, Prestito.class).getResultList();
        }


    }
