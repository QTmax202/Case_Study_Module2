package System;

import Manage.PhoneBookManage;
import Model.PhoneBook;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class RunSystem {
    Scanner scanner = new Scanner(System.in);
    PhoneBookManage phoneBookManage = new PhoneBookManage();

    public void runSystem() {
        try {
            do {
                System.out.println();
                System.out.println("❀______꧁☆☬CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ☬☆꧂_______❀");
                System.out.println("|Chọn chức năng theo số (để tiếp tục):               |");
                System.out.println("| 1. Xem danh sách                                   |");
                System.out.println("| 2. Thêm mới                                        |");
                System.out.println("| 3. Cập nhật                                        |");
                System.out.println("| 4. Xóa                                             |");
                System.out.println("| 5. Tìm kiếm                                        |");
                System.out.println("| 6. Đọc từ file                                     |");
                System.out.println("| 7. Ghi vào file                                    |");
                System.out.println("| 8. Thoát                                           |");
                System.out.println("+----------------------------------------------------+");
                System.out.print("Nhập lựa chọn: ");
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 8) {
                    System.out.println();
                    System.out.println("Lựa chọn không tồn tại, mời bạn nhập lại !");
                    System.out.println();
                }
                switch (choice) {
                    case 1:
                        phoneBookManage.displayAll();
                        break;
                    case 2:
                        phoneBookManage.createPhoneBook();
                        break;
                    case 3:
                        System.out.print("Nhập số điện thoại cần sửa (+84)-12345678: ");
                        String phoneEdit = scanner.nextLine();
                        if (phoneEdit.equals("")) {
                            runSystem();
                        } else {
                            phoneBookManage.updatePhoneBook(phoneEdit);
                        }
                        break;
                    case 4:
                        System.out.print("Nhập số điện thoại cần sửa (+84)-12345678: ");
                        String phoneDelete = scanner.nextLine();
                        if (phoneDelete.equals("")) {
                            runSystem();
                        } else {
                            phoneBookManage.deletePhoneBook(phoneDelete);
                        }
                        break;
                    case 5:
                        System.out.println("Nhập từ tên or SDT cần tìm:");
                        String keyword = scanner.nextLine();
                        phoneBookManage.searchPhoneBookByNameOrPhone(keyword);
                        break;
                    case 6:
                        ArrayList<PhoneBook> phoneBooks = phoneBookManage.readFile(phoneBookManage.PATH_NAME);
                        System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n","", "Số điện thoại", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ", "Ngày sinh" , "Email");
                        phoneBooks.forEach(System.out::println);
                        System.out.println();
                        break;
                    case 7:
                        phoneBookManage.writeFile(phoneBookManage.getPhoneBooks(), phoneBookManage.PATH_NAME);
                        break;
                    case 8:
                        System.exit(8);
                }
            } while (true);
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println();
            System.out.println("Bạn nhập sai dữ liệu, mời nhập lại !");
            System.out.println();
            runSystem();
        }
    }
}
