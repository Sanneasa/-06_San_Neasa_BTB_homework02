import java.time.LocalDate;

class CheckingAccount extends BankAccount {
    public CheckingAccount(String name, LocalDate dob, String gender, String phone) {
        super(name, dob, gender, phone);
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
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
        System.out.println(DisplayUI.CYAN + "================= Online Banking System =================" + DisplayUI.RESET);
        printCommonInfo();
        System.out.println(DisplayUI.CYAN + "======================================================" + DisplayUI.RESET);
    }
}