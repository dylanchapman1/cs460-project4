/*
 * Author(s): Dylan Chapman
 * Class:     CSC460
 * Date:      5/6/2025
 * Program:   Prog4.java
 */

import java.sql.*;
import java.util.*;

public class Prog4 {
    public static int getMenuChoice(Scanner scanner) {
        while (true) {
            System.out.println("""
                Select a query:
                Add, Update, or Delete a(n):
                    Member (1), Ski Pass (2), Equipment Inventory Record (3),
                    Equipment Rental Record (4), Lesson Purchase Record (5)
                
                6. Get Member Ski Lesson Details
                7. Get Ski Pass Usage Details
                8. Get Open Intermediate Trails
                9. Query 4 (Custom Query)
                10. Quit
                """);

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

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

    public static void Member() {

    }

    public static void SkiPass() {


    }

    public static void EquipmentInventoryRecord() {

    }

    public static void EquipmentRentalRecord() {

    }

    public static void LessonPurchaseRecord() {

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
        /*final String oracleURL = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";

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
        Connection dbconn = null;

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
                //statement = dbconn.createStatement();
                switch (decision) {
                    case 1:
                        Member();
                        break;

                    case 2:
                        SkiPass();
                        break;

                    case 3:
                        EquipmentInventoryRecord();
                        break;

                    case 4:
                        EquipmentRentalRecord();
                        break;

                    case 5:
                        LessonPurchaseRecord();
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
                //statement.close();
                decision = getMenuChoice(scanner);
            }
        }
        catch (Exception e) {System.out.println(e.getMessage());}
        /*catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        } */


    }
}