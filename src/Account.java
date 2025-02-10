interface Account {
     void deposit(double amount);
     boolean withdraw(double amount);
     void transfer(double amount, Account targetAccount);
     void displayAccountInfo();
     double getBalance();
     String getAccountNumber();
}