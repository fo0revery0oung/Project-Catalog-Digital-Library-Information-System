package app;

import model.Admin;
import model.Book;
import service.AdminService;
import service.BookService;
import service.ReportService;
import util.InputUtil;

import java.util.List;
import java.util.Map;

public class KaprodiApp {
    private final AdminService adminService;
    private final BookService bookService;
    private final ReportService reportService;

    public KaprodiApp(AdminService adminService, BookService bookService, ReportService reportService) {
        this.adminService = adminService;
        this.bookService = bookService;
        this.reportService = reportService;
    }

    public void run() {
        System.out.println("=== Login Kaprodi ===");
        String username = InputUtil.readLine("Username: ");
        String password = InputUtil.readLine("Password: ");
        Admin a = adminService.login(username, password);
        if (a == null || a.getRole() != Admin.Role.KAPRODI) {
            System.out.println("Login gagal. Hanya Kaprodi yang boleh masuk.");
            return;
        }
        System.out.println("Login berhasil. Selamat datang, " + a.getName());

        while (true) {
            System.out.println("\n-- Menu Kaprodi --");
            System.out.println("1. Lihat daftar seluruh buku");
            System.out.println("2. Cari buku");
            System.out.println("3. Laporan koleksi (jumlah buku per kategori)");
            System.out.println("0. Kembali");
            int c = InputUtil.readInt("Pilih: ");
            switch (c) {
                case 1 -> listAll();
                case 2 -> search();
                case 3 -> report();
                case 0 -> { return; }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void listAll() {
        List<Book> list = bookService.getAllBooks();
        list.forEach(b -> System.out.println(b.toString()));
    }

    private void search() {
        String k = InputUtil.readLine("Masukkan kata kunci: ");
        List<Book> res = bookService.searchBooks(k);
        if (res.isEmpty()) System.out.println("Tidak ditemukan.");
        else res.forEach(b -> System.out.println(b.toString()));
    }

    private void report() {
        Map<String, Integer> map = reportService.booksCountPerCategory();
        System.out.println("Laporan koleksi:");
        map.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}
