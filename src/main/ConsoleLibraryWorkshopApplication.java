package main;

import main.controller.SystemController;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class ConsoleLibraryWorkshopApplication {

    SystemController mainController;
    LinkedHashMap<String, Runnable> menus;
    Scanner scanner = new Scanner(System.in);

    ConsoleLibraryWorkshopApplication() {
        mainController = SystemController.INSTANCE;
        menus = new LinkedHashMap<>() {{
//            put("Login", this::login);
//            put("Checkout Book", this::checkoutBook);
//            put("Add New Book", this::addNewBook);
//            put("Add Book Copy", this::addBookCopy);
//            put("Add New Member", this::addNewMember);
//            put("Update Existing Member", this::updateExistingMember);
//            put("Checkout Record", this::checkoutRecord);
        }};
        run();
    }

    private void run() {
        while (true) {
            System.out.println("Select an option:");
            int i = 1;
            for (String key : menus.keySet()) {
                System.out.println(i + ". " + key);
                i++;
            }
            int choice = scanner.nextInt();
            ((Runnable) menus.values().toArray()[choice - 1]).run();
        }
    }

    private void login() {
        // Implement login functionality here
    }

    private void checkoutBook() {
        // Implement checkout book functionality here
    }

    private void addNewBook() {
        // Implement add new book functionality here
    }

    private void addBookCopy() {
        // Implement add book copy functionality here
    }

    private void addNewMember() {
        // Implement add new member functionality here
    }

    private void updateExistingMember() {
        // Implement update existing member functionality here
    }

    private void checkoutRecord() {
        // Implement checkout record functionality here
    }

    public static void main(String[] args) {
        new ConsoleLibraryWorkshopApplication();
    }
}
