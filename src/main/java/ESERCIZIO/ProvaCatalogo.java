package ESERCIZIO;

import exception.DuplicateItemException;

import java.util.*;

public class ProvaCatalogo {
    private static final Archivio archivio = new Archivio();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

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
                    case 6 -> aggiorna();
                    case 7 -> statistiche();
                }
            } catch (Exception e) {
                System.out.println("Input errore");
            }
        }
    }

    private static void showMenu() {
        System.out.println("""
        *** MENU ***
        1. Aggiungi elemento
        2. Ricerca per ISBN
        3. Rimuovi per ISBN
        4. Ricerca per anno
        5. Ricerca per autore
        6. Aggiorna
        7. Statistiche
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

            if (tipo.equals("L")) {
                System.out.println("Autore:");
                String autore = scanner.nextLine();
                System.out.println("Genere:");
                String genere = scanner.nextLine();
                archivio.add(new Libro(isbn, titolo, anno, pag, autore, genere));
            } else if (tipo.equals("R")) {
                System.out.println("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE):");
                Periodicity periodicity = Periodicity.valueOf(scanner.nextLine().toUpperCase());
                archivio.add(new Rivista(isbn, titolo, anno, pag, periodicity));
            } else {
                System.out.println("Tipo non valido");
            }

        } catch (DuplicateItemException e) {
            System.out.println(e.getMessage());
        }
    }

    //----- 2   METODO VOID RICERCA PER ISBN ----------//
    public static void ricerca() {
        System.out.println("ISBN da cercare");
        try {
            ElementoCatalogo e = archivio.findByIsbn(scanner.nextLine());
            System.out.println("TROVATO" + e);
        } catch (Exception e) {
            System.out.println("Errore");
        }
    }

    // -------------3 METODO PER RIMUOVERE ELEMENTO CON ISBN -----------
    public static void rimuovi() {
        System.out.println("ISBN da cercare");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo e = archivio.findByIsbn(isbn);
            System.out.println("TROVATO ISBN LIBRO DA RIMUOVERE" + e);
            archivio.removeCatalogueElement(isbn);
        } catch (Exception e) {
            System.out.println("Errore");
        }
    }

    // --------- 4. METODO RICERCA PER ANNO ----------
    public static void ricercaPerAnno() {
        System.out.println("Ricerca per anno di pubblicazione");

        try {
            int year = scanner.nextInt();
            scanner.nextLine();
            List<ElementoCatalogo> risultati = archivio.searchByYear(year);
            if (risultati.isEmpty()) {
                System.out.println("nessun elemento trovato per l'anno cercato ");
            } else {
                System.out.println("I seguenti libri corrispondo all'anno di pubblicazione cercato_");
                for (ElementoCatalogo elementoCatalogo : risultati) {
                    System.out.println(elementoCatalogo);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore durante ricerca");
        }
    }

    //------------5. METODO RICERCA PER AUTORE -----------
    public static void ricercaPerAutore() {
        System.out.println("Ricerca per autore");

        try {
            String author = scanner.nextLine();
            List<ElementoCatalogo> risultati = archivio.searchByAuthor(author);
            if (risultati.isEmpty()) {
                System.out.println("nessun elemento trovato per l'autore cercato ");
            } else {
                System.out.println("I seguenti libri corrispondo all'autore cercato_");
                for (ElementoCatalogo elementoCatalogo : risultati) {
                    System.out.println(elementoCatalogo);
                }
            }
        } catch (Exception e) {
            System.out.println("Errore durante ricerca");
        }
    }

    //--------6 AGGIORNA ELEMENTO DATO ISBN ------------
    public static void aggiorna() {
        System.out.println("ISBN da cercare per aggiornare elemento:");
        String isbn = scanner.nextLine();

        try {
            ElementoCatalogo trovato = archivio.findByIsbn(isbn);
            System.out.println("TROVATO ELEMENTO: " + trovato);

            System.out.println("Nuovo titolo:");
            String nuovoTitolo = scanner.nextLine();

            if (trovato instanceof Libro l) {
                System.out.println("Nuovo autore:");
                String nuovoAutore = scanner.nextLine();
                Libro aggiornato = new Libro(
                        isbn,
                        nuovoTitolo,
                        l.getYearOfPublication(),
                        l.getNumberOfPages(),
                        nuovoAutore,
                        l.getGenre()
                );
                archivio.aggiornaElemento(isbn, aggiornato);
            } else {
                System.out.println("Autore non modificabile: non è un libro.");
                Rivista r = (Rivista) trovato;
                Rivista aggiornata = new Rivista(
                        isbn,
                        nuovoTitolo,
                        r.getYearOfPublication(),
                        r.getNumberOfPages(),
                        r.getPeriodicity()
                );
                archivio.aggiornaElemento(isbn, aggiornata);
            }

        } catch (Exception ex) {
            System.out.println("Errore durante l'aggiornamento.");
        }
    }

    // ----------- 7 STATISTICHE ----------------
    public static void statistiche() {
        archivio.stampaStatisticheCatalogo();
    }
}
