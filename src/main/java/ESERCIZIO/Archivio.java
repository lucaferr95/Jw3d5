package ESERCIZIO;

import exception.DuplicateItemException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Archivio {
    private List<ElementoCatalogo> catalogoList= new ArrayList<>();

    public Archivio(List<ElementoCatalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }



    public void setCatalogoList(List<ElementoCatalogo> catalogoList) {
        this.catalogoList = catalogoList;
    }

    @Override
    public String toString() {
        return "Archivio{" +
                "catalogoList=" + catalogoList +
                '}';
    }
    //------------------METODO PER AGGIUNGERE ----------------------------------//

    public void add(ElementoCatalogo e) throws DuplicateItemException {
        boolean exists=  catalogoList.stream().anyMatch(elementoCatalogo -> elementoCatalogo.getIsbn().equals(e.getIsbn()));
        if (exists) throw new DuplicateItemException.DuplicateItemException(e.getIsbn());
        catalogoList.add(e);}

         //------------METODO FIND PER CERCARE UN ISBN----//
    public ElementoCatalogo findByIsbn (String isbn) {
        return catalogoList.stream().filter(elementoCatalogo -> elementoCatalogo
                .getIsbn()
                .equals(isbn))
                .findFirst().orElseThrow(RuntimeException::new);
    }

 // ---------------      METODO REMOVE CON ISBN   ----------------

    public void removeCatalogueElement(String isbn) {
        if (catalogoList.isEmpty())
            System.out.println("Il tuo archivio è vuoto!");
        } else {
            ElementoCatalogo e= findByIsbn(isbn);
                   catalogoList.remove(e);
    }

   //        METODO DI RICERCA PER ANNO ---------------- //

    public ElementoCatalogo searchByYear(int year) {
        if (catalogoList.isEmpty()) {
            System.out.println("Il tuo archivio è vuoto!");
            return null;
        } else {
            return catalogoList.stream().filter(elementoCatalogo -> elementoCatalogo.getYearOfPublication();
            if (elementoCatalogoList.isEmpty()) {
                System.out.println("Nessun elemento uscito nel: " + year + " è presente nella tua lista Archivio.");
            }
            return elementoCatalogoList;
        }
    }
    //------------- METODO DI RICERCA PER AUTORE ----------------------------------

    public ElementoCatalogo searchByAuthor(String author) {
        if (catalogoList.isEmpty()) {
            System.out.println("Il tuo archivio è vuoto!");
            return null;
        } else {
           catalogoList.stream().filter(elementoCatalogo -> elementoCatalogo.getA)
            if (searchByAuthorList.isEmpty()) {
                System.out.println("Nessun libro di: " + author + " è presente nella tua lista Archivio.");
            }
            return searchByAuthorList;
        }
    }
}
