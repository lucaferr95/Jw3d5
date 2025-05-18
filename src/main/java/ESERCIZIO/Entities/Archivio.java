package ESERCIZIO.Entities;

import exception.DuplicateItemException;
import java.util.ArrayList;
import java.util.List;

public class Archivio {
    private List<ElementoCatalogo> catalogoList;

    public Archivio() {
        this.catalogoList = new ArrayList<>();
    }

    public Archivio(List<ElementoCatalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }

    public void setCatalogoList(List<ElementoCatalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }

    @Override
    public String toString() {
        return "Archivio{catalogoList=" + catalogoList + "}";
    }

    //------------------1.METODO PER AGGIUNGERE CON ECCEZIONE DUPLICATE ----------------------------------//
    public void add(ElementoCatalogo e) throws DuplicateItemException {
        boolean exists = catalogoList.stream()
                .anyMatch(elementoCatalogo -> elementoCatalogo.getIsbn().equals(e.getIsbn()));

        if (exists) throw new DuplicateItemException(e.getIsbn());

        catalogoList.add(e);
    }


    //------------2.METODO FIND PER CERCARE UN ISBN CON LANCIO ECCEZIONE----//
    public ElementoCatalogo findByIsbn(String isbn) {
        return catalogoList.stream()
                .filter(elementoCatalogo -> elementoCatalogo.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    //--------------- 3. METODO REMOVE CON ISBN ----------------//
    public void removeCatalogueElement(String isbn) {
        if (catalogoList.isEmpty()) {
            System.out.println("Il tuo archivio è vuoto!");
        } else {
            ElementoCatalogo e = findByIsbn(isbn);
            catalogoList.remove(e);
        }
    }

    //------- 4. METODO DI RICERCA PER ANNO PUBB ----------------//
    public List<ElementoCatalogo> searchByYear(int year) {
        if (catalogoList.isEmpty()) {
            System.out.println("Il tuo archivio è vuoto!");
            return List.of();
        } else {
            List<ElementoCatalogo> elementoCatalogoList = catalogoList.stream()
                    .filter(e -> e.getYearOfPublication() == year)
                    .toList();

            if (elementoCatalogoList.isEmpty()) {
                System.out.println("Nessun elemento uscito nel: " + year + " è presente nella tua lista Archivio.");
            }

            return elementoCatalogoList;
        }
    }

    //------------- 5 METODO DI RICERCA PER AUTORE ----------------//
    public List<ElementoCatalogo> searchByAuthor(String author) {
        if (catalogoList.isEmpty()) {
            System.out.println("Il tuo archivio è vuoto!");
            return List.of();
        }

        List<ElementoCatalogo> searchByAuthorList = catalogoList.stream()
                .filter(e -> e instanceof Libro)
                .map(e -> (Libro) e)
                .filter(libro -> libro.getAuthor().equalsIgnoreCase(author))
                .map(l -> (ElementoCatalogo) l)
                .toList();

        if (searchByAuthorList.isEmpty()) {
            System.out.println("Nessun libro di: " + author + " è presente nella tua lista Archivio.");
        }

        return searchByAuthorList;
    }

    //-------------- 6. AGGIORNAMENTO DI UN ELEMENTO ESISTENTE DATO L’ISBN ---------------//
    public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) {
        for (int i = 0; i < catalogoList.size(); i++) {
            if (catalogoList.get(i).getIsbn().equals(isbn)) {
                catalogoList.set(i, nuovoElemento);
                System.out.println("Elemento con ISBN " + isbn + " aggiornato con successo.");
                return;
            }
        }
        System.out.println("Nessun elemento trovato con ISBN: " + isbn);
    }

    //------------------ 7. STATISTICHE DEL CATALOGO ---------------------------//
    public void stampaStatisticheCatalogo() {
        long numLibri = catalogoList.stream().filter(e -> e instanceof Libro).count();
        long numRiviste = catalogoList.stream().filter(e -> e instanceof Rivista).count();

        ElementoCatalogo elementoMax = null;
        int max = 0;
        for (ElementoCatalogo e : catalogoList) {
            if (e.getNumberOfPages() > max) {
                max = e.getNumberOfPages();
                elementoMax = e;
            }
        }

        double media = catalogoList.stream()
                .mapToInt(ElementoCatalogo::getNumberOfPages)
                .average()
                .orElse(0);

        System.out.println("Numero di libri: " + numLibri);
        System.out.println("Numero di riviste: " + numRiviste);
        if (elementoMax != null) {
            System.out.println("Elemento con più pagine: " + elementoMax.getTitle() + " (" + max + " pagine)");
        }
        System.out.println("Media pagine: " + String.format("%.2f", media));
    }
}
