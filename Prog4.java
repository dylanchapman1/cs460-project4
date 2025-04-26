/*
 * Author(s): Dylan Chapman
 * Class:     CSC460
 * Date:      5/6/2025
 * Program:   Prog4.java
 */

import java.sql.*;
import java.util.*;
// export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}

public class Prog4 {
    // Will increment each time a new Member is added
    public static int memberID = 0;


    /*
    Name: getMenuChoice

    Purpose: Captures a user's input to figure out what query to run

    Pre-Conditions: None

    Post-Conditions: The (final) returned value will always be 1-10

    Parameters:
    scanner (Scanner): Scanner object to capture user's input

    Returns:
    integer detailing what query a user wants to run.
     */
    public static int getMenuChoice(Scanner scanner) {
        while (true) {
            System.out.println("""
                Select a query!
                Add, Update, or Delete a(n):
                    Member (1), Ski Pass (2), Equipment Inventory Record (3),
                    Equipment Rental Record (4), Lesson Purchase Record (5),
                
                6. Get Member Ski Lesson Details
                7. Get Ski Pass Usage Details
                8. Get Open Intermediate Trails
                9. Query 4 (Custom Query)
                10. Quit""");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // We need this for some reason


                switch (choice) {
                    case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 -> {return choice;}
                    default -> System.out.println("Please choose a number 1-10!");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Please choose a number 1-5!");
                scanner.nextLine();
            }
        }
    }

    public static void Member(Scanner scanner, Connection dbconn) {
        String query;
        String specifier;
        while (true) {
            System.out.println("Would you like to ADD, UPDATE, or DELETE a Member?");
            specifier = scanner.nextLine().trim().toUpperCase();

            if (specifier.matches("ADD|UPDATE|DELETE"))
                break;
            else
                System.err.println("Invalid query specifier! Please try again.\n");
        }

        switch (specifier) {
            case "ADD":
                System.out.println("""
                        Please add all necessary fields, and SEPARATE THEM WITH COMMAS
                        <Name (String)>, <Phone (int)>, <Email (String)>, <DOB (YYYY-MM-DD)>, <Emergency Contact (int)>
                        """);

                String input  = scanner.nextLine().trim();
                String[] attributes = input.split(",");
                for (String attribute : attributes) attribute = attribute.trim();

                query = String.format(
                        "INSERT INTO dylanchapman.Member VALUES(%d, '%s', %d, '%s', TO_DATE('%s', 'YYYY-MM-DD'), %d)",
                        memberID,
                        attributes[0].trim(), // Name
                        Integer.parseInt(attributes[1].trim()), // Phone
                        attributes[2].trim(), // Email
                        attributes[3].trim(), // DOB
                        Integer.parseInt(attributes[4].trim()) // Emergency Contact
                );


                try {
                    Statement statement = dbconn.createStatement();
                    statement.executeQuery(query);
                    System.out.println("Member added successfully! Your member ID is " + memberID);
                    memberID++;
                }

                catch (SQLException e) {
                    System.err.println("*** SQLException: Could not fetch query results.");
                    System.err.println("\tMessage:   " + e.getMessage());
                    System.err.println("\tSQLState:  " + e.getSQLState());
                    System.err.println("\tErrorCode: " + e.getErrorCode());
                }

                break;


            case "UPDATE":
                System.out.println("Please enter the MemberID of the member you wish to update:");
                query = scanner.nextLine().trim();
                break;


            case "DELETE":
                System.out.println("Please insert the MemberID of the member you wish to delete:");
                query = scanner.nextLine().trim();
                break;
        }

    }

    public static void SkiPass(Scanner scanner, Connection dbconn) {
        String specifier;
        while (true) {
            System.out.println("Would you like to ADD, UPDATE, or DELETE a Ski Pass?");
            specifier = scanner.nextLine().trim().toUpperCase();

            if (specifier.matches("ADD|UPDATE|DELETE"))
                break;
            else
                System.err.println("Invalid query specifier! Please try again.\n");
        }

    }

    public static void EquipmentInventoryRecord(Scanner scanner, Connection dbconn) {
        String specifier;
        while (true) {
            System.out.println("Would you like to ADD, UPDATE, or DELETE an Equipment Inventory Record?");
            specifier = scanner.nextLine().trim().toUpperCase();

            if (specifier.matches("ADD|UPDATE|DELETE"))
                break;
            else
                System.err.println("Invalid query specifier! Please try again.\n");
        }


    }

    public static void EquipmentRentalRecord(Scanner scanner, Connection dbconn) {
        String specifier;
        while (true) {
            System.out.println("Would you like to ADD, UPDATE, or DELETE an Equipment Rental Record?");
            specifier = scanner.nextLine().trim().toUpperCase();

            if (specifier.matches("ADD|UPDATE|DELETE"))
                break;
            else
                System.err.println("Invalid query specifier! Please try again.\n");
        }


    }

    public static void LessonPurchaseRecord(Scanner scanner, Connection dbconn) {
        String specifier;
        while (true) {
            System.out.println("Would you like to ADD, UPDATE, or DELETE a Lesson Purchase Record?");
            specifier = scanner.nextLine().trim().toUpperCase();

            if (specifier.matches("ADD|UPDATE|DELETE"))
                break;
            else
                System.err.println("Invalid query specifier! Please try again.\n");
        }


    }

    public static void GetMemberSkiLessonDetails() {

    }

    public static void GetSkiPassUsageDetails() {

    }

    public static void GetOpenIntermediateTrails() {

    }

    public static void CustomQuery() {

    }






    public static void main(String[] args) {
        final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

        String username = null; // Oracle DBMS username & password
        String password = null;

        if (args.length == 2) {
            username = args[0];
            password = args[1];
        }
        else {
            System.out.println("""
                    Usage: java JDBC <username> <password>
                           <username> is your Oracle DBMS username,
                           <password> is your Oracle password (not your system password)""");
            System.exit(-1);
        }

        // Load (Oracle) JDBC driver by initializing its base class, 'oracle.jdbc.OracleDriver'
        try {Class.forName("oracle.jdbc.OracleDriver");}
        catch (ClassNotFoundException e) {

            System.err.println("""
                    *** ClassNotFoundException: Error loading Oracle JDBC driver.
                    \tPerhaps the driver is not on the Classpath?""");
            System.exit(-1);
        }

        // Make & return a DB connection to user's Oracle database
        java.sql.Connection dbconn = null;

        try {
            dbconn = DriverManager.getConnection(oracleURL,username,password);
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not open JDBC connection.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }


        // Now that we've done the "pre-processing," let's get started with the queries!
        // Queries will be built by these String objects (multiple times) */
        Scanner scanner = new Scanner(System.in);
        Statement statement;

        try {
            int decision = getMenuChoice(scanner);

            while (decision != 10) {
                statement = dbconn.createStatement();
                switch (decision) {
                    case 1:
                        Member(scanner, dbconn);
                        break;

                    case 2:
                        SkiPass(scanner, dbconn);
                        break;

                    case 3:
                        EquipmentInventoryRecord(scanner, dbconn);
                        break;

                    case 4:
                        EquipmentRentalRecord(scanner, dbconn);
                        break;

                    case 5:
                        LessonPurchaseRecord(scanner, dbconn);
                        break;

                    case 6:
                        GetMemberSkiLessonDetails();
                        break;

                    case 7:
                        GetSkiPassUsageDetails();
                        break;

                    case 8:
                        GetOpenIntermediateTrails();
                        break;

                    case 9:
                        CustomQuery();
                        break;
                }
                statement.close();
                decision = getMenuChoice(scanner);
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }


    }
}