package ESERCIZIO.Dao;

import ESERCIZIO.Entities.Utente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class UtenteDAO {
    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void salvaUtente(Utente u) {
        em.getTransaction().begin();

        em.persist(u);
        em.getTransaction().commit();
    }

    public Utente cercaPerTessera(int numeroTessera) {
        return em.find(Utente.class, numeroTessera);
    }

    public List<Utente> trovaTutti() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).
                getResultList();
    }

    public void eliminaUtente(int numeroTessera) {
        Utente u = cercaPerTessera(numeroTessera);
        if (u != null) em.remove(u);
        em.getTransaction().begin();
        em.remove(u);
        em.getTransaction().commit();
    }
}
