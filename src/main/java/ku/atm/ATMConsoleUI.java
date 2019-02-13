package ku.atm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
   A text-based simulation of an automatic teller machine.
 */
public class ATMConsoleUI {
	public void start() {
		ATM theATM;
        Bank theBank = new Bank();
		//readCustomers("customers.txt", theBank);
		theBank.addCustomer(new Customer(1, 111, 100));
        theBank.addCustomer(new Customer(2, 222, 200));
        theBank.addCustomer(new Customer(3, 333, 300));
		theATM = new ATM(theBank);

		Scanner in = new Scanner(System.in);

		while (true) {
			int state = theATM.getState();
			if (state == ATM.START) {
				System.out.print("Enter customer number: ");
				int number = in.nextInt();
				System.out.print("Enter PIN: ");
				int pin = in.nextInt();
				theATM.validateCustomer(number, pin);
			}
			else if (state == ATM.TRANSACT) {
				System.out.println("Balance=" + theATM.getBalance());
				System.out.print("A=Deposit, B=Withdrawal, C=Transfer, D=Exit: ");
				String command = in.next();
				if (command.equalsIgnoreCase("A")) {
					System.out.print("Amount: ");
					double amount = in.nextDouble();
					theATM.deposit(amount);
				}
				else if (command.equalsIgnoreCase("B")) {
					System.out.print("Amount: ");
					double amount = in.nextDouble();
					try {
						theATM.withdraw(amount);
					} catch (NotEnoughBalanceException e) {
						System.out.print(e.getMessage());
					}
				}
				else if (command.equalsIgnoreCase("C")) {
					System.out.print("Transfer To: ");
					int transferTo = in.nextInt();
					System.out.print("Amount: ");
					double amount = in.nextDouble();
					try {
						theATM.transfer(transferTo, amount);
					} catch (NotEnoughBalanceException e) {
						System.out.print(e.getMessage());
					}
				}
				else if (command.equalsIgnoreCase("D"))
					System.exit(0);
				else
					System.out.println("Illegal input!");                                    
			}         
		}
	}

	/**
	 Reads the customer numbers and pins
	 and initializes the bank accounts.
	 @param filename the name of the customer file
	 */
	public void readCustomers(String filename, Bank bank) {
		Scanner in;

        try {
            in = new Scanner(new FileReader(filename));
        } catch (FileNotFoundException e1) {
            System.out.println("Error opening accounts file.");
            return;
        }

        while (in.hasNext())
		{
			int number = in.nextInt();
			int pin = in.nextInt();
			double currentBalance = in.nextDouble();
			Customer c = new Customer(number, pin, currentBalance);
			bank.addCustomer(c);
		}
		in.close();
	}

}

