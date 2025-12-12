package service;

import model.Book;
import model.Author;
import model.Category;

import java.util.*;
import java.util.stream.Collectors;

public class BookService {
    private final List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public BookService() {
        // optional: seed some sample books
        seedSampleData();
    }

    private void seedSampleData() {
        addBook(build("Algoritma & Struktur Data", "Budi", "Textbook", 2018, "Rack A1", "Buku dasar algoritma", true));
        addBook(build("Sistem Informasi", "Siti", "Textbook", 2019, "Rack A2", "Materi SI", true));
        addBook(build("Machine Learning", "Andi", "Reference", 2021, "Rack B1", "Publikasi ML", true));
        addBook(build("Basis Data Lanjut", "Yuni", "Textbook", 2020, "Rack A3", "DB advanced", false));
        addBook(build("Penelitian Sistem Informasi", "Rina", "Publication", 2023, "Rack C1", "Jurnal penelitian", true));
    }

    private Book build(String title, String authorName, String catName, int year, String location, String desc, boolean avail) {
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(new Author(authorName));
        b.setCategory(new Category(catName));
        b.setYear(year);
        b.setLocation(location);
        b.setDescription(desc);
        b.setAvailable(avail);
        return b;
    }

    public boolean addBook(Book b) {
        b.setId(nextId++);
        // ensure createdAt set
        books.add(b);
        return true;
    }

    public List<Book> getAllBooks() {
        // newest first by createdAt
        return books.stream()
                .sorted(Comparator.comparingLong(Book::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Book getBookById(int id) {
        return books.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.isBlank()) return Collections.emptyList();
        String k = keyword.toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if ((b.getTitle()!=null && b.getTitle().toLowerCase().contains(k)) ||
                    (b.getAuthor()!=null && b.getAuthor().getName().toLowerCase().contains(k)) ||
                    (b.getCategory()!=null && b.getCategory().getName().toLowerCase().contains(k)) ||
                    (String.valueOf(b.getYear()).equals(k))) {
                results.add(b);
            }
        }
        results.sort(Comparator.comparingLong(Book::getCreatedAt).reversed());
        return results;
    }

    public List<Book> filterByCategory(String categoryName) {
        if (categoryName == null) return Collections.emptyList();
        String c = categoryName.toLowerCase();
        return books.stream()
                .filter(b -> b.getCategory()!=null && b.getCategory().getName().toLowerCase().equals(c))
                .sorted(Comparator.comparingLong(Book::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public boolean updateBook(Book updated) {
        Book existing = getBookById(updated.getId());
        if (existing == null) return false;
        existing.copyFrom(updated);
        return true;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }

    public List<Book> getLatestBooks(int limit) {
        return books.stream()
                .sorted(Comparator.comparingLong(Book::getCreatedAt).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
