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

    static void displayMenu() {
        int choice;
        do {
            printMenu();
            choice = getMenuChoice();
            handleMenuChoice(choice);
        } while (choice != 7);
    }

    static void printMenu() {
        System.out.println("\n================= Online Banking System =================");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Display Account Information");
        System.out.println("6. Delete Account");
        System.out.println("7. Exit Program");
        System.out.println("=".repeat(60));
    }

    static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> createAccount();
            case 2 -> depositMoney();
            case 3 -> withdrawMoney();
            case 4 -> transferMoney();
            case 5 -> displayAccountInfo();
            case 6 -> deleteAccount();
            case 7 -> exitSystem();
        }
    }

    static int getMenuChoice() {
        String input = getValidInput(
                "=> Choose option (1-7): ",
                "^[1-7]$",
                RED + "Invalid option. Please enter a number between 1 and 7." + RESET
        );
        return Integer.parseInt(input);
    }

    private static void createAccount() {
        System.out.println(">".repeat(20) + "   Creating Account  "+"<".repeat(20));
        String accountType = getValidInput(
                "1. Checking Account\n2. Savings Account\n3. Back\n What type do you want to create ? ",
                "^[1-3]$",
                RED + "Invalid choice. Please enter 1, 2, or 3." + RESET
        );
        if (accountType.equals("3")) return;

        String name = getValidInput(
                "Enter username: ",
                "^[a-zA-Z ]+$",
                RED + "Invalid name. Use letters and spaces only." + RESET
        );
        String dob = getValidInput(
                "Enter date of birth (dd-mm-yyyy): ",
                "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$",
                RED + "Invalid date format. Use dd-mm-yyyy." + RESET
        );
        String gender = getValidInput(
                "Enter gender (Male/Female): ",
                "^(Male|Female)$",
                RED + "Invalid gender. Enter Male or Female." + RESET
        );
        String phone = getValidInput(
                "Enter phone number: ",
                "^\\d{9,15}$",
                RED + "Invalid phone number. Use 9-15 digits." + RESET
        );

        LocalDate parsedDob = parseDate(dob);
        if (parsedDob == null) {
            System.out.println(RED + "Invalid date!" + RESET);
            return;
        }

        if (accountType.equals("1")) {
            checkingAccount = new CheckingAccount(name, parsedDob, gender, phone);
        } else {
            savingsAccount = new SavingAccount(name, parsedDob, gender, phone);
        }
        System.out.println(GREEN + "Your checking account has been created successfully!" + RESET);
    }

    private static void depositMoney() {
        System.out.println(">".repeat(20) + "   Deposit Money  "+"<".repeat(20));
        String accountType = getValidInput(
                "1. Checking Account\n2. Savings Account\n3. Back\n => Choose an account type that you want to deposit ? ",
                "^[1-3]$",
                RED + "Invalid choice ? Please try again " + RESET
        );
        if (accountType.equals("3")) return;

        Account account = accountType.equals("1") ? checkingAccount : savingsAccount;
        if (account == null) {
            System.out.println(RED + "Account not found." + RESET);
            return;
        }

        String amountStr = getValidInput(
                "Enter amount to deposit: ",
                "^\\d+(\\.\\d{1,2})?$",
                RED + "Invalid amount. Use up to two decimal places." + RESET
        );
        account.deposit(Double.parseDouble(amountStr));
        System.out.println("                 Checking Account ");
        System.out.println("Received $:"+ amountStr);
        System.out.println("Total Amount  $:" +account.getBalance() );
        System.out.println(GREEN + "Deposit successful!" + RESET);
    }

    private static void withdrawMoney() {
        String accountType = getValidInput(
                "1. Checking Account\n2. Savings Account\n3. Back\n Choose an account type that you want to draw ?: ",
                "^[1-3]$",
                RED + "Invalid choice." + RESET
        );
        if (accountType.equals("3")) return;

        Account account = accountType.equals("1") ? checkingAccount : savingsAccount;
        if (account == null) {
            System.out.println(RED + "Account number not been found ? " + RESET);
            return;
        }

        String amountStr = getValidInput(
                "Enter amount to withdraw: ",
                "^\\d+(\\.\\d{1,2})?$",
                RED + "Invalid amount." + RESET
        );
        double amount = Double.parseDouble(amountStr);

        if (account.withdraw(amount)) {
            System.out.println("Withdraw $: "+ amountStr);
            System.out.println("Total Amount  $:" + amount );
            System.out.println(GREEN + "Withdrawal successful!" + RESET);
        } else {
            System.out.println(RED + "Withdrawal failed. Insufficient balance." + RESET);
        }
    }

    private static void transferMoney() {
        String sourceType = getValidInput(
                "Select source account:\n1. Checking\n2. Savings\n3. Back\nPlease enter type an account that you want to transfer ?: ",
                "^[1-3]$",
                RED + "Invalid choice." + RESET
        );
        if (sourceType.equals("3")) return;
        Account source = sourceType.equals("1") ? checkingAccount : savingsAccount;
        if (source == null) {
            System.out.println(RED + "Source account not found." + RESET);
            return;
        }

        String targetType = getValidInput(
                "Select target account:\n1. Checking\n2. Savings\n3. Back\n Please choose an account type to transfer ?",
                "^[1-3]$",
                RED + "Invalid choice." + RESET
        );
        if (targetType.equals("3")) return;
        Account target = targetType.equals("1") ? checkingAccount : savingsAccount;
        if (target == null) {
            System.out.println(RED + "Target account not found." + RESET);
            return;
        }

        String amountStr = getValidInput(
                "Enter transfer amount: ",
                "^\\d+(\\.\\d{1,2})?$",
                RED + "Invalid amount." + RESET
        );
        double amount = Double.parseDouble(amountStr);

        source.transfer(amount, target);
        System.out.println(GREEN + "Transfer completed  "+ amount + " to " +target.getBalance() + RESET);
    }

    private static void displayAccountInfo() {
        String accountType = getValidInput(
                "1. Checking Account\n2. Savings Account\n3. Back\n Please choose type of account that you want to display ?: ",
                "^[1-3]$",
                RED + "Invalid choice? Please try again" + RESET
        );
        if (accountType.equals("3")) return;

        Account account = accountType.equals("1") ? checkingAccount : savingsAccount;
        if (account == null) {
            System.out.println(RED + "Account not found." + RESET);
        } else {
            account.displayAccountInfo();
        }
    }

    private static void deleteAccount() {
        String accountType = getValidInput(
                "1. Checking Account\n2. Savings Account\n3. Back\nChoose an account type that you want to deleted ? : ",
                "^[1-3]$",
                RED + "Invalid choice? Please try gain" + RESET
        );
        if (accountType.equals("3")) return;

        if (accountType.equals("1")) {
            checkingAccount = null;
        } else {
            savingsAccount = null;
        }
        System.out.println(GREEN + "Account deleted." + RESET);
    }

    private static void exitSystem() {
        System.out.println(GREEN + "Exiting..." + RESET);
        System.exit(0);
    }

    private static String getValidInput(String prompt, String regex, String errorMsg) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    private static LocalDate parseDate(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(dob, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
