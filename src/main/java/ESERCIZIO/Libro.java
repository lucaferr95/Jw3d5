package ESERCIZIO;

public class Libro extends ElementoCatalogo {
    private String author;
    private String genre;

    public Libro(String title, String isbn, int yearOfPublication, int numberOfPages, String author, String genre) {
        super(title, isbn, yearOfPublication, numberOfPages);
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
                "titolo='" + getTitle() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", anno di pubblicazione=" + getYearOfPublication() +
                ", pagine=" + getNumberOfPages() +
                ", autore='" + author + '\'' +
                ", genere='" + genre + '\'' +
                '}';
    }
}
