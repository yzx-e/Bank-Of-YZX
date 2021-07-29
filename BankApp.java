import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class BankApp 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the the bank of YZX");
        System.out.println("Please enter your name: ");
        String yourName = scanner.next();
        Account em = new Account(yourName, "A0001");
        em.showMenu();
    }
}

class Account 
{   //class variables
    int balance;
    int perviousTransaction;
    String customerName;
    String customerID;
    int transRecNumber = 0;
    Date date = new Date();
    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat dateForm = new SimpleDateFormat("dd:MM:yyyy");
    int savingsBalance = 0;

    //class constructor
    Account(String cname, String cid) 
    {
        customerName = cname;
        customerID = cid;
    }

    // function for depositing money
    void deposit(int amount) //where amounts declared
    {
        if (amount != 0)
        {
            balance = balance + amount;
            perviousTransaction = amount;
        }
    }
    // function for withdrawing money
    void withdraw(int amount) //where amounts declared
    {
        if (amount != 0)
        {
            balance = balance - amount;
            perviousTransaction = -amount;
        }
    }
    
    // function that calculates interest
    void calculateInterest (int years)
    {
        double interestRate = 0.185;
        double newBalance = (balance * interestRate * years) + balance;
        System.out.println("The current interest rate is " + (100 * interestRate));
        System.out.println("After " + years + "years, your balance will be: " + newBalance );
    }
    void showMenuAgain ()
    {
        System.out.println("Welcome" + customerName + ".");
        System.out.println("Your customer ID is " + customerID);
        System.out.println("");
        System.out.println("What would you like to do?");
        System.out.println("");
        System.out.println("A. Check balance");
        System.out.println("B. Make a deposit");
        System.out.println("C. Withdrawal");
        System.out.println("D. View previous transactions");
        System.out.println("E. Calculate interest");
        System.out.println("F. Transfer between accounts");
        System.out.println("G. Show menu again");
        System.out.println("Q. Exit");
    }



    void checkingTransfer (int amount)
    {
        if (amount != 0)
        {
            savingsBalance = savingsBalance - amount;
            balance = balance + amount;
            perviousTransaction = amount;
        }   
    }

    void savingsTransfer (int amount)
    {
        if (amount != 0)
        {
            savingsBalance = savingsBalance + amount;
            balance = balance - amount;
            perviousTransaction = amount;
        }   
    }

    // function that show menu
    void showMenu ()
    {
        char option = '\0';
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome " + customerName + ".");
        System.out.println("Your customer ID is " + customerID);
        System.out.println("");
        System.out.println("What would you like to do?");
        System.out.println("");
        System.out.println("A. Check balance");
        System.out.println("B. Make a deposit");
        System.out.println("C. Withdrawal");
        System.out.println("D. View previous transactions");
        System.out.println("E. Calculate interest");
        System.out.println("F. Transfer between accounts");      
        System.out.println("Q. Exit");

        String getPreviousTransaction = "";
        int transactionId = transRecNumber;
        String createdAt = time.format(date) + ", " + dateForm.format(date);
        int amount = 0;
        String transactionType = "";
        String filepath = "BankData.txt";


        do
        {
            System.out.println();
            System.out.println("Enter an option: ");
            System.out.println("Enter 'G' to show menu again: ");      
            char option1 = scanner.next().charAt(0);
            option = Character.toUpperCase(option1);
            System.out.println();
            
            switch (option)
            {
                // check bal
                case 'A':
                System.out.println("View checkings account or savings account?(1/2): ");
                int whichAcc = scanner.nextInt();
                if (whichAcc == 1)
                { 
                System.out.println("========");
                System.out.println("Checking account balance = $" + balance);
                System.out.println("========");
                System.out.println();
                }
                else if(whichAcc == 2)
                {
                System.out.println("========");
                System.out.println("Savings account balance = $" + savingsBalance);
                System.out.println("========");
                System.out.println();
                }
                else 
                {
                    System.out.println("Error: press 'G' to show menu.");
                }
                break;

                // deposit
                case 'B':
                System.out.println("How much would you like to deposit?: ");
                int amount1 = scanner.nextInt();
                deposit(amount1);
                System.out.println();
                transRecNumber = transRecNumber + 1;
                transactionId = transRecNumber;
                amount = amount1;
                transactionType = "DEPOSIT";
                System.out.println("Succsesfully deposited $" + amount1 + " into your checking account. ");
                getPreviousTransaction = "Succsesfully deposited $" + amount1 + " into your checking account. ";
                saveToCsv(transactionId, createdAt, amount, transactionType, filepath);       
                break;

                // withdraw
                case 'C':
                System.out.println("How much would you like to withdraw?: ");
                int amount2 = scanner.nextInt();
                withdraw(amount2);
                System.out.println();
                transRecNumber = transRecNumber + 1;
                transactionId = transRecNumber;
                amount = amount2;  
                transactionType = "WITHDRAWAL";
                saveToCsv(transactionId, createdAt, amount, transactionType, filepath);   
                System.out.println("Succsesfully withdrew $" + amount2 + " from your checking account.");   
                getPreviousTransaction = "Succsesfully withdrew $" + amount2 + " into your checking account. ";   
                break;

                // view prev
                case 'D':
                System.out.println();
                System.out.println(getPreviousTransaction);
                System.out.println();
                break;

                // calc interest
                case 'E':
                System.out.println("Enter how many years of accured interest: ");
                int years = scanner.nextInt();
                calculateInterest(years);
                break;

                // menu again
                case 'F':
                System.out.println("Please select account to transfer from(C = Checking, S = Savings): ");
                char fromAcc = scanner.next().charAt(0);
                fromAcc = Character.toUpperCase(fromAcc);

                System.out.println("Please enter amount you'd like to transfer: ");
                int transferAmount = scanner.nextInt();
                if (fromAcc == 'S')
                {
                    checkingTransfer(transferAmount);
                    System.out.println("Succsesfully transfered $" + transferAmount + " from savings into your checking account.");
                    transRecNumber = transRecNumber + 1;
                    transactionId = transRecNumber;
                    amount = transferAmount;
                    transactionType = "TRANSFER";
                    getPreviousTransaction = "Succsesfully transfered $" + transferAmount+ " from savings into your checking account. ";   
                    saveToCsv(transactionId, createdAt, amount, transactionType, filepath);

                }
                else if (fromAcc == 'C')
                { 
                    savingsTransfer(transferAmount);
                    System.out.println("Succsesfully transfered $" + transferAmount + " into your savings account.");
                    transRecNumber = transRecNumber + 1;
                    transactionId = transRecNumber;
                    amount = transferAmount;
                    transactionType = "TRANSFER";
                    getPreviousTransaction = "Succsesfully transfered $" + transferAmount+ " from checking into your savings account. ";
                    saveToCsv(transactionId, createdAt, amount, transactionType, filepath);
                }
                else
                {
                    System.out.println("Error! ");

                }
                break;

                // menu again
                case 'G':
                showMenuAgain();
                break;     
                
                // exit
                case 'Q':
                System.out.println("========");
                break;                   

                default:
                System.out.println("Error: invalid option. Please select A, B, C, D, E or F.");
                break;


            }
        }
        while (option != 'Q'); 
        System.out.println("Thank you.");

    }
    void saveToCsv(int transactionId, String createdAt, double amount, String transactionType, String filepath) 
    {


        try 
        {
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            //fromAccountId, toAccountId, createdAt, amount, transactionType, relatedTransaction
            String transactionIdCode = "TXID";

            pw.println(transactionIdCode + transactionId);
            pw.println(createdAt);
            pw.println(amount);
            pw.println(transactionType);
            pw.println("");
            pw.flush();
            pw.close();
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Record not saved");
        }
    }

}
