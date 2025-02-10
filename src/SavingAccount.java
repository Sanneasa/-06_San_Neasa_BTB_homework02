import java.time.LocalDate;

class SavingAccount extends BankAccount {
    private static final double RATE = 0.05;

    public SavingAccount(String name, LocalDate dob, String gender, String phone) {
        super(name, dob, gender, phone);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount >= balance * 0.2) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void transfer(double amount, Account targetAccount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
        }
    }

    @Override
    public void displayAccountInfo() {
        System.out.println(DisplayUI.YELLOW + "\n>> Savings Account <<" + DisplayUI.RESET);
        printCommonInfo();
        System.out.printf("Interest Rate: %.0f%%\n", RATE * 100);
        System.out.println(DisplayUI.YELLOW + "======================================" + DisplayUI.RESET);
    }
}