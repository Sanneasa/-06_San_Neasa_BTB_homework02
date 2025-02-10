import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

abstract class BankAccount implements Account {
    protected String accountNumber;
    protected String userName;
    protected LocalDate dateOfBirth;
    protected String gender;
    protected String phoneNumber;
    protected double balance;

    public BankAccount(String name, LocalDate dob, String gender, String phone) {
        this.accountNumber = generateAccountNumber();
        this.userName = name;
        this.dateOfBirth = dob;
        this.gender = gender;
        this.phoneNumber = phone;
        this.balance = 0.0;
    }

    private String generateAccountNumber() {
        return String.format("%09d", new Random().nextInt(1000000000));
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    protected void printCommonInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("User Name: " + userName);
        System.out.println("Date of Birth: " + dateOfBirth.format(formatter));
        System.out.println("Gender: " + gender);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.printf("Balance: %.1f $\n", balance);
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
