import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Store {

	private static final Scanner scanner = new Scanner(System.in); // User input
	static Connection con = null;
	private static FileManager fileManager = new FileManager(); // Handling file operations
	private InputValidator inputValidator = new InputValidator(); // Validating user input
	private static DateTimeManager dateTimeManager = new DateTimeManager(); // Managing date and time operations


	public void start() {
		System.out.println("I N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
		System.out.println("-------------------------------------------------------");
		System.out.println("1. ITEMS LIST");
		System.out.println("2. ADD NEW ITEM");
		System.out.println("3. UPDATE QUANTITY OF EXISTING ITEM");
		System.out.println("4. REMOVE ITEM");
		System.out.println("5. VIEW DAILY TRANSACTION REPORT");
		System.out.println("-------------------------------------------------------");
		System.out.println("6. Exit");

		while (true) {
			System.out.print("\n Enter a choice and Press ENTER to continue[1-6]:");
			int userInput = inputValidator.getIntInput(scanner); // checks user's input

			if (userInput > 6 || userInput < 1) {
				System.out.println("This doesn't appear to be a valid option!");
				continue;
			}

			switch (userInput) {
				case 1:
					fileManager.printRecords(); // Print the records of items
					break;
				case 2:
					addRecord(); // Add a new record
					break;
				case 3:
					editRecord(); // Edit existing records
					break;
				case 4:
					deleteRecord(); // Delete an existing record
					break;
				case 5:
					fileManager.printTransactions(); // Print daily transaction report
					break;
				case 6:
					// Exit the program
					System.out.println("\n\nThanks for using this program...!");
					scanner.close();
					System.exit(0);
			}
		}
	}

	private void addRecord() {
		Scanner numbers = new Scanner(System.in);
		Scanner text = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("Enter Product Description:");
				String description = text.next();

				System.out.println("Enter Unit Price:");
				double unit_price = numbers.nextDouble();

				System.out.println("Enter Quantity:");
				double qty = numbers.nextDouble();

				double total_value = unit_price * qty;

				if (qty <= 10000 && unit_price <= 1000) {
					try (Connection con = DriverManager.getConnection(FileManager.url, FileManager.username, FileManager.password)) {
						con.setAutoCommit(false); // Start transaction

						// Insert into items table
						PreparedStatement addItem = con.prepareStatement("INSERT INTO items(description, Unit_Price, qty, Total_Value) VALUES(?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
						addItem.setString(1, description);
						addItem.setDouble(2, unit_price);
						addItem.setDouble(3, qty);
						addItem.setDouble(4, total_value);
						int itemUpdateCount = addItem.executeUpdate();

						// Check if item insertion was successful
						if (itemUpdateCount > 0) {
							ResultSet rs = addItem.getGeneratedKeys();
							if (rs.next()) {
								int itemId = rs.getInt(1);

								String currentDate = dateTimeManager.getCurrentDate(); // DateTimeManager class to get date
								String currentTime = dateTimeManager.getCurrentTime();// DateTimeManager class to get time

								// Insert into transactions table
								PreparedStatement addTransaction = con.prepareStatement("INSERT INTO transactions(date, time, Item_Id, description, Unit_price, qty, Total_Value, activity) VALUES(?,?,?,?,?,?,?,?)");
								addTransaction.setString(1, currentDate); // Extract date part
								addTransaction.setString(2, currentTime); // Extract time part
								addTransaction.setInt(3, itemId);
								addTransaction.setString(4, description);
								addTransaction.setDouble(5, unit_price);
								addTransaction.setDouble(6, qty);
								addTransaction.setDouble(7, total_value);
								addTransaction.setString(8, "++Added");

								int transUpdateCount = addTransaction.executeUpdate();
								if (transUpdateCount > 0) {
									System.out.println("Record [" + description + "] is added successfully!");
								}
							}
						} else {
							System.out.println("Record not added. Please check the entered value.");
						}

						// Commit transaction
						con.commit();
						fileManager.printRecords();
						break; // Exit loop if successful

					} catch (SQLException e) {
						e.printStackTrace();
						try {
							// Rollback transaction if exception occurs
							if (con != null) {
								con.rollback();
							}
						} catch (SQLException se) {
							se.printStackTrace();
						}
					}
				} else {
					System.out.println("Unit price or quantity exceeds allowed limit. Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a valid number.");
				numbers.next(); // Skip the invalid input to prevent an infinite loop
			}
		}
	}

	private void editRecord() {
		fileManager.printRecords();
		Scanner numbers = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("Enter the ID number to enter new price and quantity: ");
				int idNum = numbers.nextInt();
				System.out.println("Enter new price: ");
				double priceNew = numbers.nextDouble();
				System.out.println("Enter new quantity: ");
				double qtyNew = numbers.nextDouble();
				double totalNew = priceNew * qtyNew;

				if (qtyNew <= 10000 && priceNew <= 1000) {
					try (Connection con = DriverManager.getConnection(FileManager.url, FileManager.username, FileManager.password)) {
						con.setAutoCommit(false); // Start transaction

						// Update the record in the items table
						PreparedStatement updateRecord = con.prepareStatement("UPDATE items SET Unit_Price = ?, qty = ?, Total_Value = ? WHERE Item_Id = ?");
						updateRecord.setDouble(1, priceNew);
						updateRecord.setDouble(2, qtyNew);
						updateRecord.setDouble(3, totalNew);
						updateRecord.setInt(4, idNum);
						int checkUpdate = updateRecord.executeUpdate();

						// If the update was successful, log the transaction
						if (checkUpdate > 0) {
							// Fetch the item description based on the item ID
							String description = "";
							PreparedStatement fetchDescription = con.prepareStatement("SELECT description FROM items WHERE Item_Id = ?");
							fetchDescription.setInt(1, idNum);
							ResultSet rs = fetchDescription.executeQuery();
							if (rs.next()) {
								description = rs.getString("description");
							}

							String currentDate = dateTimeManager.getCurrentDate();
							String currentTime = dateTimeManager.getCurrentTime();

							// Insert into transactions table
							PreparedStatement addTransaction = con.prepareStatement("INSERT INTO transactions(date, time, Item_Id, description, Unit_price, qty, Total_Value, activity) VALUES(?,?,?,?,?,?,?,?)");
							addTransaction.setString(1, currentDate);
							addTransaction.setString(2, currentTime);
							addTransaction.setInt(3, idNum);
							addTransaction.setString(4, description);
							addTransaction.setDouble(5, priceNew);
							addTransaction.setDouble(6, qtyNew);
							addTransaction.setDouble(7, totalNew);
							addTransaction.setString(8, "->Updated");

							int transUpdateCount = addTransaction.executeUpdate();

							// If both updates were successful, commit the transaction
							if (transUpdateCount > 0) {
								con.commit();
								System.out.println("Record updated successfully!");
							} else {
								con.rollback(); // Rollback if transaction logging fails
								System.out.println("Couldn't log transaction");
							}
						} else {
							System.out.println("Couldn't update the record.");
						}
						con.close();
						fileManager.printRecords();
						break; // Exit the loop if successful
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Unit price or quantity exceeds allowed limit. Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter valid numbers.");
				numbers.next(); // Skip the invalid input to prevent an infinite loop
			}
		}
	}


	private void deleteRecord() {
		fileManager.printRecords();
		Scanner numbers = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("Enter the ID Number you want to delete: ");
				int IdNo = numbers.nextInt();

				try (Connection con = DriverManager.getConnection(FileManager.url, FileManager.username, FileManager.password)) {
					con.setAutoCommit(false); // Start transaction

					// Fetch item details before deleting
					String description = "";
					double price = 0;
					int qty = 0;
					double totalValue = 0;

					PreparedStatement fetchItemDetails = con.prepareStatement("SELECT description, Unit_Price, qty, Total_Value FROM items WHERE Item_Id = ?");
					fetchItemDetails.setInt(1, IdNo);
					ResultSet rs = fetchItemDetails.executeQuery();

					if (rs.next() && IdNo < 100000) {
						description = rs.getString("description");
						price = rs.getDouble("Unit_Price");
						qty = rs.getInt("qty");
						totalValue = rs.getDouble("Total_Value");

						// Delete the item record
						PreparedStatement deleteRecord = con.prepareStatement("DELETE FROM items WHERE Item_Id = ?");
						deleteRecord.setInt(1, IdNo);
						int checkUpdate = deleteRecord.executeUpdate();

						if (checkUpdate > 0) {
							String currentDate = dateTimeManager.getCurrentDate();
							String currentTime = dateTimeManager.getCurrentTime();

							// Insert into transactions table
							PreparedStatement addTransaction = con.prepareStatement("INSERT INTO transactions(date, time, Item_Id, description, Unit_price, qty, Total_Value, activity) VALUES(?,?,?,?,?,?,?,?)");
							addTransaction.setString(1, currentDate);
							addTransaction.setString(2, currentTime);
							addTransaction.setInt(3, IdNo);
							addTransaction.setString(4, description);
							addTransaction.setDouble(5, price);
							addTransaction.setInt(6, qty);
							addTransaction.setDouble(7, totalValue);
							addTransaction.setString(8, "--Deleted");

							addTransaction.executeUpdate();
							System.out.println("Record deleted successfully!");
						} else {
							System.out.println("Unable to delete the record.");
						}

						con.commit();
					} else {
						System.out.println("Record not found or ID number exceeds limit.");
					}

					fileManager.printRecords();
					break; // Exit the loop if successful
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter valid numbers");
				numbers.next(); // Skip the invalid input to prevent an infinite loop
			}
		}
	}


	 //Main method to start the inventory management system.
	public static void main(String[] args) {
		Store store = new Store(); //Creates a new Store object and starts the system.
		store.start();
	}
}
