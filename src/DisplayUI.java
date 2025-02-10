import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
public  class DisplayUI {
        public static final String RESET = "\u001B[0m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String CYAN = "\u001B[36m";
        static Scanner scanner = new Scanner(System.in);
            private static CheckingAccount checkingAccount;
            private static SavingAccount savingsAccount;
//            private static Scanner sc = new Scanner(System.in);

         static void displayMenu() {

        int choice = 0;
        do {
            printMenu();
            choice = getMenuChoice();
            handleMenuChoice(choice);
        } while (choice != 7);
      }
      static  void printMenu() {
        System.out.println("\n========= Online Baking System  =========");
        System.out.println("1- Create Account");
        System.out.println("2- Deposit Money");
        System.out.println("3- Withdraw Money ");
        System.out.println("4- Transfer Money");
        System.out.println("5- Display Account Information");
        System.out.println("6- Deleted Account");
        System.out.println("7- Exit Program");
        System.out.println("".repeat(20));

    }
     static void handleMenuChoice(int choice) {
        switch (choice){
            case 1-> createAccount();
            case 2-> depositMoney();
            case 3 -> System.out.println("Wihre");
            case 4 -> System.out.println("4");
            case 5 -> System.out.println("5");
            case 6 -> System.out.println("6");
            case 7 -> exitSystem();
        }
    }

     static int getMenuChoice(){
        while (true){
            String input = getValidInput(
                    "=> Choose option (1-7): ",
                    "[1-7]",
                    RED + "Invalid option! Please enter 1-7." + RESET
            );
            return Integer.parseInt(input);
        }
    }

     static void createAccount() {
        String accountType = getValidInput(
                "1. Checking Account\n2. Saving Account\n3. Back\nChoose: ",
                "^[1-3]$",
                RED + "Invalid option! Please enter 1-7." + RESET);

        if (accountType.equals("3")) return;

        String name = getValidInput("Enter username: ", "^[a-zA-Z ]+$", RED + "Invalid option! Please enter 1-7." + RESET);
        String dob = getValidInput("Enter date of birth (dd-mm-yyyy): ",
                "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$", RED + "Invalid option! Please enter 1-7." + RESET);
        String gender = getValidInput("Enter gender: ", "^(Male|Female)$", RED + "Invalid option! Please enter 1-7." + RESET);
        String phone = getValidInput("Enter phone number: ", "^\\d{9,15}$", RED + "Invalid option! Please enter 1-7." + RESET);

        LocalDate parsedDob = parseDate(dob);
        if (parsedDob == null) {
            System.out.println("\u001B[31mInvalid date format!\u001B[0m");
            return;
        }
        if (accountType.equals("1")) {
            checkingAccount = new CheckingAccount(name, parsedDob, gender, phone);
        } else {
            savingsAccount = new SavingAccount(name,parsedDob,gender,phone);
        }

        System.out.println("\u001B[32mAccount created successfully!\u001B[0m");
    }
      static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    private static void depositMoney() {
        String accountType = getValidInput(
                "1. Checking Account\n2. Savings Account\n3. Back\nChoose: ",
                "^[1-3]$",
                RED + "Invalid option! Please enter 1-7." + RESET);

        if (accountType.equals("3")) return;

        String amount = getValidInput("Enter money to deposit: ", "^\\d+(\\.\\d{1,2})?$", RED + "Invalid option! Please enter 1-7." + RESET);
        double depositAmount = Double.parseDouble(amount);

        if (accountType.equals("1") && checkingAccount != null) {
            checkingAccount.deposit(depositAmount);
        } else if (savingsAccount != null) {
            savingsAccount.deposit(depositAmount);
        }

        System.out.println("\u001B[32mDeposit successful!\u001B[0m");
    }


     static String getValidInput(String prompt, String regex, String s) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner .nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("\u001B[31mInvalid input! Please try again.\u001B[0m");
        }
    }
     static void exitSystem() {
        System.out.println("\u001B[32m\nThank you for using our service!");
        System.out.println("Exiting the system...\u001B[0m");
        System.exit(0);
    }

}
