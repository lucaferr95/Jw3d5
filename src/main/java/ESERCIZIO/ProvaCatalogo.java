package ESERCIZIO;

import ESERCIZIO.Dao.ArchivioDAO;
import ESERCIZIO.Dao.PrestitoDao;
import ESERCIZIO.Dao.UtenteDAO;
import ESERCIZIO.Entities.*;
import exception.DuplicateItemException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProvaCatalogo {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ArchivioDAO archivioDAO;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean exit = false;
        emf = Persistence.createEntityManagerFactory("Postgres");
        em = emf.createEntityManager();
        archivioDAO = new ArchivioDAO(em);

        inizializzaUtentiEPrestiti(); // <-- Creo utenti e prestiti

        while (!exit) {
            showMenu();
            try {
                int scelta = Integer.parseInt(scanner.nextLine());
                switch (scelta) {
                    case 1 -> aggiungi();
                    case 2 -> ricerca();
                    case 3 -> rimuovi();
                    case 4 -> ricercaPerAnno();
                    case 5 -> ricercaPerAutore();
                    case 6 -> ricercaPerTitolo();
                    case 7 -> prestitiPerUtente();
                    case 8 -> prestitiScaduti();
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Hai scelto di uscire dal ciclo");
            }
        }

        em.close();
        emf.close();
    }

    //------CREAZIONE UTENTI E PRESTITI-----
    private static void inizializzaUtentiEPrestiti() {
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDao prestitoDao = new PrestitoDao(em);

        Utente u1 = new Utente("topo", "gigio", LocalDate.of(1960, 1, 1));
        Utente u2 = new Utente("topo", "lino", LocalDate.of(1980, 1, 1));
        Utente u3 = new Utente("banda", "bassotti", LocalDate.of(1990, 1, 1));

        utenteDAO.salvaUtente(u1);
        utenteDAO.salvaUtente(u2);
        utenteDAO.salvaUtente(u3);

        ElementoCatalogo libro1 = archivioDAO.ricercaPerIsbn("001");
        ElementoCatalogo libro2 = archivioDAO.ricercaPerIsbn("002");
        ElementoCatalogo libro3 = archivioDAO.ricercaPerIsbn("003");

        Prestito p1 = new Prestito(u1, libro1, LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 2, 4), LocalDate.of(2025, 1, 3));
        Prestito p2 = new Prestito(u2, libro2, LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 4, 4), LocalDate.of(2025, 3, 28));
        Prestito p3 = new Prestito(
                u3,
                libro3,
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 5, 1),
                null
        );

        prestitoDao.salvaPrestito(p1);
        prestitoDao.salvaPrestito(p2);
        prestitoDao.salvaPrestito(p3);
    }

    private static void showMenu() {
        System.out.println("""
                *** MENU ***
                1. Aggiungi elemento
                2. Ricerca per ISBN
                3. Rimuovi per ISBN
                4. Ricerca per anno
                5. Ricerca per autore
                6. Ricerca per titolo o parte di esso
                7. Ricerca elementi in prestito
                8. Ricerca prestiti scaduti
                Seleziona il metodo da eseguire:
                """);
    }

    //----------------- 1 AGG ------------------//
    private static void aggiungi() {
        System.out.println("Tipo (L= LIBRO, R= RIVISTA):");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            System.out.println("ISBN:");
            String isbn = scanner.nextLine();
            System.out.println("Titolo:");
            String titolo = scanner.nextLine();
            System.out.println("Anno:");
            int anno = Integer.parseInt(scanner.nextLine());
            System.out.println("Pagine:");
            int pag = Integer.parseInt(scanner.nextLine());

            ElementoCatalogo nuovoElemento = null;

            if (tipo.equals("L")) {
                System.out.println("Autore:");
                String autore = scanner.nextLine();
                System.out.println("Genere:");
                String genere = scanner.nextLine();
                nuovoElemento = new Libro(isbn, titolo, anno, pag, autore, genere);

            } else if (tipo.equals("R")) {
                System.out.println("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE):");
                Periodicity periodicity = Periodicity.valueOf(scanner.nextLine().toUpperCase());
                nuovoElemento = new Rivista(isbn, titolo, anno, pag, periodicity);

            } else {
                System.out.println("Tipo non valido");
                return;
            }

            em.getTransaction().begin();
            archivioDAO.aggiungiElemento(nuovoElemento);
            em.getTransaction().commit();

            System.out.println("Elemento aggiunto con successo.");

        } catch (DuplicateItemException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore durante inserimento: " + e.getMessage());
        }
    }

    //----- 2   METODO VOID RICERCA PER ISBN ----------//
    private static void ricerca() {
        System.out.println("ISBN da cercare:");
        try {
            String isbn = scanner.nextLine();
            ElementoCatalogo e = archivioDAO.ricercaPerIsbn(isbn);
            if (e != null) {
                System.out.println("TROVATO: " + e);
            } else {
                System.out.println("Elemento non trovato.");
            }
        } catch (Exception e) {
            System.out.println("Errore nella ricerca.");
        }
    }

    // -------------3 METODO PER RIMUOVERE ELEMENTO CON ISBN -----------
    public static void rimuovi() {
        System.out.println("ISBN da cercare");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo e = archivioDAO.ricercaPerIsbn(isbn);
            System.out.println("TROVATO ISBN LIBRO DA RIMUOVERE" + e);
            archivioDAO.rimuoviElemento(isbn);
        } catch (Exception e) {
            System.out.println("Errore");
        }
    }

    // --------- 4. METODO RICERCA PER ANNO ----------
    private static void ricercaPerAnno() {
        System.out.println("Anno di pubblicazione:");
        try {
            int anno = Integer.parseInt(scanner.nextLine());
            List<ElementoCatalogo> risultati = archivioDAO.ricercaPerAnno(anno);
            risultati.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Errore nella ricerca.");
        }
    }

    //------------5. METODO RICERCA PER AUTORE -----------
    private static void ricercaPerAutore() {
        System.out.println("Autore da cercare:");
        try {
            String autore = scanner.nextLine();
            List<Libro> risultati = archivioDAO.ricercaPerAutore(autore);
            risultati.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Errore nella ricerca.");
        }
    }

    //-------------6. Ricerca per titolo o parte di esso---------
    private static void ricercaPerTitolo() {
        System.out.println("Titolo o parte di esso:");
        try {
            String titolo = scanner.nextLine();
            List<ElementoCatalogo> risultati = archivioDAO.ricercaPerTitolo(titolo);
            risultati.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Errore nella ricerca.");
        }
    }

    //------7. Ricerca elementi in prestito-------
    private static void prestitiPerUtente() {
        System.out.println("Numero tessera utente:");
        try {
            int numeroTessera = Integer.parseInt(scanner.nextLine());
            List<Prestito> risultati = archivioDAO.prestitiPerUtente(numeroTessera);
            risultati.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Errore nella ricerca.");
        }
    }

    //----------8.Ricerca prestiti scaduti//
    private static void prestitiScaduti() {
        System.out.println("Prestiti scaduti e non restituiti:");
        try {
            List<Prestito> risultati = archivioDAO.prestitiScadutiNonRestituiti();
            risultati.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Errore nella ricerca.");
        }
    }
}
