package ESERCIZIO.Entities;

import jakarta.persistence.Entity;

@Entity
public class Libro extends ElementoCatalogo {
    private String author;
    private String genre;

    public Libro() {
        super();
    }
    public Libro(String isbn, String title, int yearOfPublication, int numberOfPages, String author, String genre) {
        super(isbn, title, yearOfPublication, numberOfPages);
        this.author = author;
        this.genre = genre;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override


    public String toString() {
        return "Libro{" +

                "author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}'+super.toString();
    }
}
