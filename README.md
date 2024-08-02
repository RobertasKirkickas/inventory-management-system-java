# Inventory Management System
![IMS](https://github.com/user-attachments/assets/07f0b960-a5f9-4f92-b4fe-d1762ca58f90)

The Inventory Management System (IMS) is a Java-based application designed to help businesses efficiently control and track their inventory. It provides real-time management of inventory data, allowing users to maintain up-to-date information about stock levels, item availability, and movement. The system also supports adding, editing, deleting, and viewing records, as well as generating daily transaction reports.

## Features

- Real-time inventory tracking
- Add, edit, and delete inventory items
- Generate transaction reports
- User-friendly interface
- Error handling and validation for user inputs
- Database connectivity using MySQL

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- A Java IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/RobertasKirkickas/inventory-management-system-java.git
Navigate to the Project Directory:

bash
Copy code
cd inventory-management-system-java
Configure the Database:

Create a MySQL database named inventory_db.
Update the database connection settings in the FileManager class.
Build and Run the Project:

Open the project in your preferred Java IDE.
Build and run the Store class to start the application.
Usage
Main Menu:


The main menu provides options to view, add, edit, and delete inventory items.

Viewing Inventory:


This feature allows users to see a list of all current inventory items.

Adding New Items:


Users can add new items to the inventory by entering item details such as description, unit price, and quantity.

Editing Existing Items:


Modify the details of existing items, including updating the quantity and price.

Deleting Items:


Remove items from the inventory by entering the item's ID.

Generating Reports:


Generate reports of all transactions, including item additions, updates, and deletions.

Design and Architecture
The application is structured into four main classes:

Store: Manages program logic and user interactions.
FileManager: Handles database operations for items and transactions.
InputValidator: Ensures valid user inputs.
DateTimeManager: Provides current date and time functionalities.


Future Enhancements
Implement user authentication for enhanced security.
Introduce a graphical user interface (GUI) for improved usability.
Develop advanced reporting features, such as monthly summaries and sales analysis.
Implement automatic transaction report deletion after a set period to save storage space.
Contributing
Contributions are welcome! If you'd like to contribute to the project, please fork the repository and create a pull request with your changes.

License
This project is licensed under the MIT License. See the LICENSE file for details.

Contact
For questions or suggestions, please contact [RobertasKirkickas@gmail.com].

### Program Screenshots:

#### Items list:
![items-list](https://github.com/user-attachments/assets/f633e79b-a066-4e4a-b68f-d28bf5c2e614)

#### Add new item:
![add new item](https://github.com/user-attachments/assets/75df9652-cfad-4b29-a3fe-9fb36a9aacd0)

#### Edit item:
![edit item](https://github.com/user-attachments/assets/d0de1e46-2425-41af-9cb2-586d8d108144)

#### Delete item:
![delete item](https://github.com/user-attachments/assets/91451beb-543b-43e8-b885-687b7158b3b4)

#### Daily transactions:
![transactions](https://github.com/user-attachments/assets/704fcc1c-4040-497d-bff4-9e3d5c2fb54b)



