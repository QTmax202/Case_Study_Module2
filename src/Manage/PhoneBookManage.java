package Manage;

import Model.PhoneBook;
import Regex.Validate;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBookManage {
    public static ArrayList<PhoneBook> phoneBooks;
    Scanner input = new Scanner(System.in);
    public static Validate validate = new Validate();
    public static final String PATH_NAME = "file_Data/phoneBook.csv";

    public void writeFile(ArrayList<PhoneBook> contactList, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            for (PhoneBook contact : contactList) {
                bufferedWriter.write(contact.getPhoneNumber() + "," + contact.getGroup() + "," + contact.getName() + "," + contact.getGender() + ","
                        + contact.getAddress() + "," + contact.getDateOfBirth() + "," + contact.getEmail() + "\n");
            }
            bufferedWriter.close();
            System.out.println("Write file successfully !");
            System.out.println();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public ArrayList<PhoneBook> readFile(String path) {
        ArrayList<PhoneBook> contacts = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(",");
                contacts.add(new PhoneBook(strings[0], strings[1], strings[2], strings[3], strings[4], LocalDate.parse(strings[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")), strings[6]));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return contacts;
    }

    public PhoneBookManage() {
        if (new File(PATH_NAME).length() == 0) {
            phoneBooks = new ArrayList<>();
        } else {
            phoneBooks = readFile(PATH_NAME);
        }
    }

    public ArrayList<PhoneBook> getPhoneBooks() {
        return phoneBooks;
    }

    public String getGender() {
        String genderCheck = "";
        int choice;
        do {
            System.out.println("▹ Nhập giới tính:");
            System.out.println("▹ 1. Male");
            System.out.println("▹ 2. Female");
            System.out.println("▹ 3. Orther");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    genderCheck = "Male";
                    break;
                case 2:
                    genderCheck = "Female";
                    break;
                case 3:
                    genderCheck = "Orther";
                    break;
            }
            return genderCheck;
        } while (choice < 0 | choice > 3);
    }

    public String getPhoneNumber() {
        String phoneNumbercheck;
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            String phone = input.nextLine();
            if (!validate.validatePhone(phone)) {
                System.out.println("Số điện thoại không hợp lệ !");
                System.out.println("Số điện thoại phải có 10 số (0 - 9) định dạng: (+84)-12345678");
                System.out.println();
            } else {
                phoneNumbercheck = phone;
                break;
            }
        }
        return phoneNumbercheck;
    }

    public String getEmail() {
        String emailCheck;
        while (true) {
            System.out.print("▹ Nhập email: ");
            String inputEmail = input.nextLine();
            if (!validate.validateEmail(inputEmail)) {
                System.out.println("Email không hợp lệ !");
                System.out.println("Email phải có dạng: abc.abc@yahoo.com/abc12.abc@gmail.vn/...");
                System.out.println();
            } else {
                emailCheck = inputEmail;
                break;
            }
        }
        return emailCheck;
    }

    public void createPhoneBook() {
        System.out.println("Nhập thông tin:");
        String phoneNumber = getPhoneNumber();
        System.out.println("Nhập tên nhóm:");
        String group = input.nextLine();
        System.out.println("Nhập Họ tên:");
        String name = input.nextLine();
        String gender = getGender();
        input.nextLine();
        System.out.println("Nhập địa chỉ:");
        String address = input.nextLine();
        System.out.println("Nhập ngày sinh(dd/mm/yyyy):");
        LocalDate dateOfBirth = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String email = getEmail();
        for (PhoneBook phone : phoneBooks) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("Số điện thoại bị trùng, mời kiểm tra lại !");
                System.out.println();
                createPhoneBook();
            }
        }
        phoneBooks.add(new PhoneBook(phoneNumber, group, name, gender, address, dateOfBirth, email));
        writeFile(phoneBooks,PATH_NAME);
        System.out.println("Thêm " + phoneNumber + " vào danh bạ thành công !");
    }

    public void deletePhoneBook(String phoneNumber) {
        PhoneBook deleteContact = null;
        for (PhoneBook contact : phoneBooks) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                deleteContact = contact;
            }
        }
        if (deleteContact != null) {
            System.out.println("▹ Nhập xác nhận:");
            System.out.println("▹ Y: Xóa");
            System.out.println("▹ Ký tự khác: Thoát");
            String confirm = input.next();
            if (confirm.equalsIgnoreCase("y")) {
                phoneBooks.remove(deleteContact);
                writeFile(phoneBooks, PATH_NAME);
                System.out.println("Xóa " + phoneNumber + " thành công !");
                System.out.println();
            }
        } else {
            System.out.println("Không tìm thấy danh bạ với số điện thoại trên !");
            System.out.println("--------------------");
        }
    }

    public void updatePhoneBook(String phoneNumber) {
        PhoneBook editContact = null;
        for (PhoneBook contact : phoneBooks) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                editContact = contact;
            }
        }
        if (editContact != null) {
            int index = phoneBooks.indexOf(editContact);
            input.nextLine();
            System.out.println("Nhập tên nhóm mới:");
            editContact.setGroup(input.nextLine());
            System.out.println("Nhập Họ tên mới:");
            editContact.setName(input.nextLine());
            editContact.setGender(getGender());
            input.nextLine();
            System.out.println("Nhập địa chỉ mới:");
            editContact.setAddress(input.nextLine());
            System.out.println("Nhập ngày sinh mới(dd/mm/yyyy):");
            LocalDate dateOfBirth = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            editContact.setDateOfBirth(dateOfBirth);
            editContact.setEmail(getEmail());
            phoneBooks.set(index, editContact);
            writeFile(phoneBooks, PATH_NAME);
            System.out.println("Sửa " + phoneNumber + " thành công !");
            System.out.println();
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên !");
            System.out.println();
        }
    }

    public void searchPhoneBookByNameOrPhone(String keyword) {
        ArrayList<PhoneBook> phoneBook = new ArrayList<>();
        for (PhoneBook contact : phoneBooks) {
            if (validate.validateSearch(keyword, contact.getPhoneNumber()) || validate.validateSearch(keyword, contact.getName())) {
                phoneBook.add(contact);
            }
        }
        if (phoneBook.isEmpty()) {
            System.out.println("Không tìm thấy danh bạ với từ khóa trên !");
            System.out.println();
        } else {
            System.out.println("Danh bạ cần tìm:");
            phoneBook.forEach(System.out::println);
            System.out.println();
        }
    }

    public void displayAll() {
//        System.out.printf("%-12s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n","", "Số điện thoại", "Nhóm", "Họ tên", "Giới tính", "Địa chỉ", "Ngày sinh" , "Email");
        for (PhoneBook contact : phoneBooks) {
            System.out.println(contact);
        }
    }
}

