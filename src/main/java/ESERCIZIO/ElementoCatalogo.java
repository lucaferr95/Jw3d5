package ESERCIZIO;
import java.util.Objects;
    public abstract class ElementoCatalogo {
        private String isbn;
        private String title;
        private int yearOfPublication;
        private int numberOfPages;

        public ElementoCatalogo(String title, String isbn, int yearOfPublication, int numberOfPages) {
            this.title = title;
            this.isbn = isbn;
            this.yearOfPublication = yearOfPublication;
            this.numberOfPages = numberOfPages;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        public int getYearOfPublication() {
            return yearOfPublication;
        }

        public int getNumberOfPages() {
            return numberOfPages;
        }

        @Override
        public String toString() {
            return "ElementoCatalogo{" +
                    "isbn='" + isbn + '\'' +
                    ", title='" + title + '\'' +
                    ", yearOfPublication=" + yearOfPublication +
                    ", numberOfPages=" + numberOfPages +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            ElementoCatalogo that = (ElementoCatalogo) o;
            return Objects.equals(isbn, that.isbn);
        }

        @Override
        public int hashCode() {
            return Objects.hash(isbn);
        }
    }


