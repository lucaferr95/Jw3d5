package ESERCIZIO.Dao;

import ESERCIZIO.Entities.ElementoCatalogo;
import ESERCIZIO.Entities.Libro;
import ESERCIZIO.Entities.Prestito;

import exception.DuplicateItemException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;



public class ArchivioDAO {
    private final EntityManager em;

    public ArchivioDAO(EntityManager em) {
        this.em = em;
    }

    // 1. Aggiunta elemento al catalogo
    public void aggiungiElemento(ElementoCatalogo elemento) throws DuplicateItemException {
        if (em.find(ElementoCatalogo.class, elemento.getIsbn()) != null) {
            throw new DuplicateItemException(elemento.getIsbn());
        }
        em.persist(elemento);
    }


    // 2. Rimozione elemento per ISBN
    public void rimuoviElemento(String isbn) {
        ElementoCatalogo e = em.find(ElementoCatalogo.class, isbn);
        if (e != null) em.remove(e);
    }

    // 3. Ricerca per ISBN
    public ElementoCatalogo ricercaPerIsbn(String isbn) {
        return em.find(ElementoCatalogo.class, isbn);
    }

    // 4. Ricerca per anno pubblicazione
    public List<ElementoCatalogo> ricercaPerAnno(int anno) {
       TypedQuery<ElementoCatalogo> query= em.createQuery(
               "select e from ElementoCatalogo e WHERE e.yearOfPublication= :anno", ElementoCatalogo.class);
       query.setParameter("anno", anno);
       return query.getResultList();

    }

    // 5. Ricerca per autore (solo su Libri)
    public List<Libro> ricercaPerAutore(String autore) {
        TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE l.author = :autore", Libro.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }



    // 6. Ricerca per titolo o parte di esso

    public List<ElementoCatalogo> ricercaPerTitolo(String titoloParziale) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.title LIKE :titolo", ElementoCatalogo.class);
        query.setParameter("titolo", "%" + titoloParziale + "%");
        return query.getResultList();
    }

    // 7. Ricerca degli elementi in prestito dato il numero tessera
    public List<Prestito> prestitiPerUtente(int numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numero", Prestito.class);
        query.setParameter("numero", numeroTessera);
        return query.getResultList();
    }

    // 8. Ricerca prestiti scaduti e non restituiti
    public List<Prestito> prestitiScadutiNonRestituiti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.finePrestito < CURRENT_DATE AND p.restituzione IS NULL", Prestito.class);
        return query.getResultList();
    }}
