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
        System.out.println("Select a query");
        System.out.println("1. Add, Update or Delete a Member");
        System.out.println("2. Add Update, or Delete a Ski Pass");
        System.out.println("3. Add, Update, or Delete an Equipment Inventory Record");
        System.out.println("4. Add, Update, or Delete an Equipment Rental Record");
        System.out.println("5. Add, Update, or Delete a Lesson Purchase Record");

        System.out.println("6. Get Member Ski Lesson Details");
        System.out.println("7. Get Ski Pass Usage Details");
        System.out.println("8. Get Open Intermediate Trails");
        System.out.println("9. Query 4 (Custom Query)");
        System.out.println("10. Quit");
        return 0;
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
        // Queries will be built by these String objects (multiple times)
        Scanner scanner = new Scanner(System.in);
        Statement statement;

        try {
            int decision = getMenuChoice(scanner);
            scanner.nextLine();
            System.out.println();

            while (decision != 10) {
                statement = dbconn.createStatement();
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