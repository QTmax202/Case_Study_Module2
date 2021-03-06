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
                bufferedWriter.write(contact.getPhoneNumber() + "," +
                        contact.getGroup() + "," +
                        contact.getName() + "," +
                        contact.getGender() + "," +
                        contact.getAddress() + "," +
                        contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+ "," +
                        contact.getEmail() + "\n");
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
                contacts.add(new PhoneBook(strings[0],
                        strings[1],
                        strings[2],
                        strings[3],
                        strings[4],
                        LocalDate.parse(strings[5], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        strings[6]));
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
            System.out.println("Nh???p gi???i t??nh:");
            System.out.println("??? 1. Male");
            System.out.println("??? 2. Female");
            System.out.println("??? 3. Orther");
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
            System.out.println("Nh???p s??? ??i???n tho???i: ");
            String phone = input.nextLine();
            if (!validate.validatePhone(phone)) {
                System.out.println("S??? ??i???n tho???i kh??ng h???p l??? !");
//                System.out.println("S??? ??i???n tho???i ph???i c?? 10 s??? (0 - 9) ?????nh d???ng: (+84)-12345678");
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
            System.out.println("Nh???p email: ");
            String inputEmail = input.nextLine();
            if (!validate.validateEmail(inputEmail)) {
                System.out.println("Email kh??ng h???p l??? !");
//                System.out.println("Email ph???i c?? d???ng: abc.abc@yahoo.com/abc12.abc@gmail.vn/...");
                System.out.println();
            } else {
                emailCheck = inputEmail;
                break;
            }
        }
        return emailCheck;
    }

    public void createPhoneBook() {
        System.out.println("Nh???p th??ng tin:");
        String phoneNumber = getPhoneNumber();
        System.out.println("Nh???p t??n nh??m:");
        String group = input.nextLine();
        System.out.println("Nh???p H??? t??n:");
        String name = input.nextLine();
        String gender = getGender();
        input.nextLine();
        System.out.println("Nh???p ?????a ch???:");
        String address = input.nextLine();
        System.out.println("Nh???p ng??y sinh(dd/mm/yyyy):");
        LocalDate dateOfBirth = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String email = getEmail();
        for (PhoneBook phone : phoneBooks) {
            if (phone.getPhoneNumber().equals(phoneNumber)) {
                System.out.println("S??? ??i???n tho???i b??? tr??ng, m???i ki???m tra l???i !");
                System.out.println();
                createPhoneBook();
            }
        }
        phoneBooks.add(new PhoneBook(phoneNumber, group, name, gender, address, dateOfBirth, email));
        writeFile(phoneBooks,PATH_NAME);
        System.out.println("Th??m " + phoneNumber + " v??o danh b??? th??nh c??ng !");
    }

    public void deletePhoneBook(String phoneNumber) {
        PhoneBook deleteContact = null;
        for (PhoneBook contact : phoneBooks) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                deleteContact = contact;
            }
        }
        if (deleteContact != null) {
            System.out.println("??? Nh???p x??c nh???n:");
            System.out.println("??? Y: X??a");
            System.out.println("??? K?? t??? kh??c: Tho??t");
            String confirm = input.next();
            if (confirm.equalsIgnoreCase("y")) {
                phoneBooks.remove(deleteContact);
                writeFile(phoneBooks, PATH_NAME);
                System.out.println("X??a " + phoneNumber + " th??nh c??ng !");
                System.out.println();
            }
        } else {
            System.out.println("Kh??ng t??m th???y danh b??? v???i s??? ??i???n tho???i tr??n !");
            System.out.println();
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
            System.out.println("Nh???p t??n nh??m m???i:");
            editContact.setGroup(input.nextLine());
            System.out.println("Nh???p H??? t??n m???i:");
            editContact.setName(input.nextLine());
            editContact.setGender(getGender());
            input.nextLine();
            System.out.println("Nh???p ?????a ch??? m???i:");
            editContact.setAddress(input.nextLine());
            System.out.println("Nh???p ng??y sinh m???i(dd/mm/yyyy):");
            LocalDate dateOfBirth = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            editContact.setDateOfBirth(dateOfBirth);
            editContact.setEmail(getEmail());
            phoneBooks.set(index, editContact);
            writeFile(phoneBooks, PATH_NAME);
            System.out.println("S???a " + phoneNumber + " th??nh c??ng !");
            System.out.println();
        } else {
            System.out.println("Kh??ng t??m ???????c danh b??? v???i s??? ??i???n tho???i tr??n !");
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
            System.out.println("Kh??ng t??m th???y danh b??? v???i t??? kh??a tr??n !");
            System.out.println();
        } else {
            System.out.println("Danh b??? c???n t??m:");
            phoneBook.forEach(System.out::println);
            System.out.println();
        }
    }

    public void displayAll() {
//        System.out.printf("%-12s%-10s%-10s%-10s%-10s%-10s%-10s%-10s\n","", "S??? ??i???n tho???i", "Nh??m", "H??? t??n", "Gi???i t??nh", "?????a ch???", "Ng??y sinh" , "Email");
        for (PhoneBook contact : phoneBooks) {
            System.out.println(contact);
        }
    }
}

