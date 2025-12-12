package app;

import model.Author;
import model.Book;
import model.Category;
import model.Admin;
import service.AdminService;
import service.BookService;
import service.ReportService;
import util.InputUtil;

import java.util.List;
import java.util.Map;

public class AdminApp {
    private final AdminService adminService;
    private final BookService bookService;
    private final ReportService reportService;

    public AdminApp(AdminService adminService, BookService bookService, ReportService reportService) {
        this.adminService = adminService;
        this.bookService = bookService;
        this.reportService = reportService;
    }

    public void run() {
        System.out.println("=== Admin Login ===");
        String username = InputUtil.readLine("Username: ");
        String password = InputUtil.readLine("Password: ");
        Admin a = adminService.login(username, password);
        if (a == null || a.getRole() != Admin.Role.ADMIN) {
            System.out.println("Login gagal. Hanya admin yang boleh masuk.");
            return;
        }
        System.out.println("Login berhasil. Selamat datang, " + a.getName());

        while (true) {
            System.out.println("\n--- Menu Admin ---");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Edit Buku");
            System.out.println("3. Hapus Buku");
            System.out.println("4. Lihat Semua Buku");
            System.out.println("5. Laporan Koleksi (Kaprodi)");
            System.out.println("0. Logout");
            int choice = InputUtil.readInt("Pilih: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> editBook();
                case 3 -> deleteBook();
                case 4 -> listAll();
                case 5 -> showReport();
                case 0 -> { System.out.println("Logout..."); return; }
                default -> System.out.println("Pilihan tidak dikenal.");
            }
        }
    }

    private void addBook() {
        System.out.println("=== Tambah Buku ===");
        String title = InputUtil.readLine("Judul: ");
        String authorName = InputUtil.readLine("Pengarang: ");
        String categoryName = InputUtil.readLine("Kategori: ");
        int year = InputUtil.readInt("Tahun Terbit: ");
        String location = InputUtil.readLine("Lokasi rak: ");
        String desc = InputUtil.readLine("Deskripsi singkat: ");
        boolean available = InputUtil.readYesNo("Tersedia?");
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(new Author(authorName));
        b.setCategory(new Category(categoryName));
        b.setYear(year);
        b.setLocation(location);
        b.setDescription(desc);
        b.setAvailable(available);
        boolean ok = bookService.addBook(b);
        System.out.println(ok ? "Buku berhasil ditambahkan." : "Gagal menambah buku.");
    }

    private void editBook() {
        int id = InputUtil.readInt("Masukkan ID buku yang akan diedit: ");
        Book existing = bookService.getBookById(id);
        if (existing == null) { System.out.println("Buku tidak ditemukan."); return; }
        System.out.println(existing.toDetailedString());
        String title = InputUtil.readLine("Judul (" + existing.getTitle() + "): ");
        String author = InputUtil.readLine("Pengarang (" + (existing.getAuthor()!=null?existing.getAuthor().getName():"-") + "): ");
        String category = InputUtil.readLine("Kategori (" + (existing.getCategory()!=null?existing.getCategory().getName():"-") + "): ");
        int year = InputUtil.readInt("Tahun (" + existing.getYear() + "): ");
        String location = InputUtil.readLine("Lokasi (" + existing.getLocation() + "): ");
        String desc = InputUtil.readLine("Deskripsi (enter untuk tetap): ");
        boolean available = InputUtil.readYesNo("Tersedia? (" + (existing.isAvailable() ? "y" : "n") + ")");
        Book updated = new Book();
        updated.setId(existing.getId());
        if (!title.isEmpty()) updated.setTitle(title);
        if (!author.isEmpty()) updated.setAuthor(new Author(author));
        if (!category.isEmpty()) updated.setCategory(new Category(category));
        updated.setYear(year);
        if (!location.isEmpty()) updated.setLocation(location);
        if (!desc.isEmpty()) updated.setDescription(desc);
        updated.setAvailable(available);
        boolean ok = bookService.updateBook(updated);
        System.out.println(ok ? "Update berhasil." : "Update gagal.");
    }

    private void deleteBook() {
        int id = InputUtil.readInt("Masukkan ID buku yang akan dihapus: ");
        boolean ok = bookService.deleteBook(id);
        System.out.println(ok ? "Buku dihapus." : "Gagal menghapus/ID tidak ditemukan.");
    }

    private void listAll() {
        List<Book> list = bookService.getAllBooks();
        System.out.println(String.format("%-3s | %-36s | %-16s | %-12s | %-4s | %s", "ID", "Title", "Author", "Category", "Year", "Avail"));
        System.out.println("-----------------------------------------------------------------------------------------------");
        list.forEach(b -> System.out.println(b.toString()));
    }

    private void showReport() {
        Map<String, Integer> map = reportService.booksCountPerCategory();
        System.out.println("=== Laporan: Jumlah Buku per Kategori ===");
        map.forEach((cat, cnt) -> System.out.println(cat + " : " + cnt));
    }
}
