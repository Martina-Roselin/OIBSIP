import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    String type;
    double amount;

    Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

class BankAccount {
    private double balance;
    private ArrayList<Transaction> transactions;

    BankAccount() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
        System.out.println("Deposited: $" + amount);
    }

    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            System.out.println("Withdrawn: $" + amount);
        }
    }

    void transfer(BankAccount recipient, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance for transfer!");
        } else {
            this.withdraw(amount);
            recipient.deposit(amount);
            System.out.println("Transferred: $" + amount);
        }
    }

    void showTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    double getBalance() {
        return balance;
    }
}

class User {
    private String userId;
    private String userPin;

    User(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
    }

    boolean authenticate(String id, String pin) {
        return this.userId.equals(id) && this.userPin.equals(pin);
    }
}

public class ATM {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User("12345", "6789");
        BankAccount account = new BankAccount();

        System.out.println("Welcome to the ATM!");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPin = scanner.nextLine();

        if (!user.authenticate(userId, userPin)) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }
        
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    account.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    BankAccount recipient = new BankAccount(); // New recipient account
                    account.transfer(recipient, transferAmount);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
