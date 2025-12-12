package service;

import model.Book;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final BookService bookService;

    public ReportService(BookService bs) {
        this.bookService = bs;
    }

    public Map<String, Integer> booksCountPerCategory() {
        List<Book> list = bookService.getAllBooks();
        Map<String, Integer> map = new LinkedHashMap<>();
        for (Book b : list) {
            String cat = (b.getCategory() != null && b.getCategory().getName() != null) ? b.getCategory().getName() : "Uncategorized";
            map.put(cat, map.getOrDefault(cat, 0) + 1);
        }
        return map;
    }
}
