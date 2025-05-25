package ESERCIZIO.Entities;

import jakarta.persistence.Entity;

@Entity
public class Rivista extends ElementoCatalogo {
    private Periodicity periodicity;

    public Rivista(String title, String isbn, int yearOfPublication, int numberOfPages, Periodicity periodicity) {
        super(title, isbn, yearOfPublication, numberOfPages);
        this.periodicity = periodicity;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "titolo='" + getTitle() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", anno di pubblicazione=" + getYearOfPublication() +
                ", pagine=" + getNumberOfPages() +
                ", periodicità=" + periodicity +
                '}';
    }

}
