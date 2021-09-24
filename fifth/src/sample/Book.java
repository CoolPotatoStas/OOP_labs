package sample;

import java.util.Objects;

public class Book {
    public String language;
    public String author;
    public String year;

    public Book(String l, String a, String y){
        language = l;
        author = a;
        year = y;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(language, book.language) && Objects.equals(author, book.author) && Objects.equals(year, book.year);
    }

}
