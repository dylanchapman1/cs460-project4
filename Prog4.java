/*
 * Author(s): Dylan Chapman
 * Class:     CSC460
 * Date:      5/6/2025
 * Program:   Prog4.java
 */

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
// export CLASSPATH=/usr/lib/oracle/19.8/client64/lib/ojdbc8.jar:${CLASSPATH}

public class Prog4 {

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
                    default -> System.out.println("Please choose a number 1-10!\n");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Please choose a number 1-5!");
                scanner.nextLine();
            }
        }
    }

    public static ArrayList<Integer> getMemberIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT MEMBERID FROM dylanchapman.MEMBER";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next())
                    IDs.add(answer.getInt("MEMBERID"));
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);

        }

        return IDs;
    }

    public static ArrayList<Integer> getRentalIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT RENTALID FROM dylanchapman.EQUIPMENTRENTALRECORD";
    
        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);
    
            if(answer != null) {
                while(answer.next())
                    IDs.add(answer.getInt("RENTALID"));
            }
        }
        catch(SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
    
        return IDs;
    }    

    public static ArrayList<Integer> getPassIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT PASSID FROM dylanchapman.SKIPASS";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next())
                    IDs.add(answer.getInt("PASSID"));
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);

        }

        return IDs;
    }

    public static ArrayList<Integer> getEquipmentIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT EQUIPMENTID FROM dylanchapman.EQUIPMENT";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next())
                    IDs.add(answer.getInt("EQUIPMENTID"));
            }
        } catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);
        }

        return IDs;
    }

    public static void addMember(Scanner scanner, Connection dbconn) {
        String query;
        System.out.println("""
                        Please add all necessary fields, and SEPARATE THEM WITH COMMAS
                        <Name (String)>, <Phone (int)>, <Email (String)>, <DOB (YYYY-MM-DD)>, <Emergency Contact (int)>
                        """);

        String input  = scanner.nextLine().trim();
        String[] attributes = input.split(",");
        int currentID = Collections.max(getMemberIDs(dbconn)) + 1;

        query = String.format(
                "INSERT INTO dylanchapman.Member VALUES(%d, '%s', %d, '%s', TO_DATE('%s', 'YYYY-MM-DD'), %d)",
                currentID,
                attributes[0].trim(), // Name
                Long.parseLong(attributes[1].trim()), // Phone
                attributes[2].trim(), // Email
                attributes[3].trim(), // DOB
                Long.parseLong(attributes[4].trim()) // Emergency Contact
        );

        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            System.out.printf("Member added successfully! Your member ID is %s\n\n", currentID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    public static void updateMember(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the MemberID of the user you wish to update:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getMemberIDs(dbconn).contains(memberID)) {
            System.out.println("Member ID does not exist!\n");
            return;
        }

        String query;
        System.out.println("""
                        Please reenter all necessary fields, and SEPARATE THEM WITH COMMAS
                        <Phone (int)>, <Email (String)>, <Emergency Contact (int)>
                        """);

        String input  = scanner.nextLine().trim();
        String[] attributes = input.split(",");

        query = String.format(
                "UPDATE dylanchapman.Member SET PHONENO = %d, EMAIL = '%s', EMERGENCYCONTACTNO = %d WHERE MEMBERID = %d",
                Long.parseLong(attributes[0]),
                attributes[1].trim(),
                Long.parseLong(attributes[2].trim()),
                memberID
        );

        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            System.out.printf("Your information has been updated successfully! Your member ID is %d\n\n", memberID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    public static void deleteMember(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the MemberID of the user you wish to update:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this


    }

    public static void addSkiPass(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the MemberID of the user you wish to buy a Ski Pass for:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getMemberIDs(dbconn).contains(memberID)) {
            System.out.println("Member ID does not exist!\n");
            return;
        }

        System.out.println("""
                Which pass would you like to buy (enter the corresponding integer):
                    1. 1-day | 10 uses | $75
                    2. 2-day | 25 uses | $140
                    3. 4-day | 65 uses | $250
                    4. Season Pass | 1000 Uses | $750
                """);

        Map<String, Integer[]> skiPassMap = Map.of(
                "1", new Integer[]{10, 75},
                "2", new Integer[]{25, 140},
                "3", new Integer[]{65, 250},
                "4", new Integer[]{1000, 750}
        );

        // Will be 1,2,3, or 4
        String selectedPass = scanner.nextLine().trim();
        String query = String.format(
                "INSERT INTO dylanchapman.SkiPass VALUES(%d, %d, %d, %d, TO_DATE('2024-12-31', 'YYYY-MM-DD'), %d)",
                memberID * 2,
                memberID,
                skiPassMap.get(selectedPass)[0],
                skiPassMap.get(selectedPass)[0],
                skiPassMap.get(selectedPass)[1]
        );


        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            System.out.printf("Ski Pass purchased for member ID %s!\n\n", memberID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }

    }

    public static void updateSkiPass(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the PassID of the user you wish to update a Ski Pass for:");
        int passID = scanner.nextInt();

        System.out.println("Please enter the MemberID of the user you wish to update a skipass for:");
        int memberID = scanner.nextInt();
        scanner.nextLine();
        if(!getMemberIDs(dbconn).contains(memberID) || !getPassIDs(dbconn).contains(passID)) {
            System.out.println("PassID/MemberID foes not exist!\n");
            return;
        }

        ArrayList<String> cols = new ArrayList<String>();
        try(ResultSet answer = dbconn.getMetaData().getColumns(null, "DYLANCHAPMAN", "SKIPASS", null)) {
            while(answer.next()) {
                cols.add(answer.getString("COLUMN_NAME"));
            }
        }
        catch(SQLException e) {
            System.out.println("sql error");
            return;
        }
        cols.remove("PASSID");
        cols.remove("MEMBERID");

        String sql = "update dylanchapman.SKIPASS SET ";
        String[] vals = new String[cols.size()];

        int i = 0;
        while(i < cols.size()) {
            String col = cols.get(i);
            System.out.println("Enter a new value for " + col + ": ");
            vals[i] = scanner.nextLine();

            sql += col + " = ?";
            if(i < cols.size()-1) {
                sql += ", ";

            }
            i++;
        }

        sql += " where passid = ?  and memberid = ?";

        try {
            PreparedStatement prep = dbconn.prepareStatement(sql);
            i = 0;
            System.out.println(i + " " + vals.length);
            while(i < vals.length) {
                if (i == 2) {
                        try {
                                java.sql.Date sqlDate = java.sql.Date.valueOf(vals[i]); // YYYY-MM-DD
                                prep.setDate(i + 1, sqlDate);
                                i++;
                        }
                        catch(IllegalArgumentException e) {
                                System.out.println("Please use YYYY-MM-DD.");
                                return;
                        }
                }
                else{
                        prep.setString(i+1, vals[i]);
                        i++;
                }
            }
            prep.setInt(i+1, passID);
            prep.setInt(i+2, memberID);
            int count = prep.executeUpdate();
            if(count > 0) {
                System.out.println("Ski pass updated");
            }
            else {
                System.out.println("No record updated");
            }
        }
        catch(SQLException e) {
            System.out.println("error: "+ e.getMessage());
                System.out.println(e.getSQLState());
                System.out.println(e.getErrorCode());
        }
    }

    
    public static void deleteSkiPass(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the PassID of the user you wish to delete a Ski Pass for:");
        int passID = scanner.nextInt();

        System.out.println("Please enter the MemberID of the user you wish to delete a Ski Pass for");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getMemberIDs(dbconn).contains(memberID) || !getPassIDs(dbconn).contains(passID)) {
            System.out.println("PassID/MemberID does not exist!\n");
            return;
        }

        String query = String.format("SELECT REMAININGUSES, EXPIRATIONDATE FROM dylanchapman.SkiPass WHERE PASSID = %d AND MEMBERID = %d", passID, memberID);
        ResultSet answer = null;

        try {
            Statement statement = dbconn.createStatement();
            answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next()) {
                    int remaining = Integer.parseInt(answer.getString("REMAININGUSES"));
                    String expired = answer.getString("EXPIRATIONDATE").split(" ")[0];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate currentDate = LocalDate.now();
                    LocalDate expirationDate = LocalDate.parse(expired, formatter);

                    if (currentDate.isAfter(expirationDate) && remaining == 0) {
                        String deleteQuery = String.format("UPDATE dylanchapman.SkiPass SET ACTIVE = %d, WHERE PASSID = %d AND MEMBERID = %d", 0, passID, memberID);

                        try {
                            Statement deleteStatement = dbconn.createStatement();
                            deleteStatement.executeUpdate(deleteQuery);
                            System.out.printf("Ski Pass, with PassID %d, deleted for member ID %d!\n\n", passID, memberID);
                        }

                        catch (SQLException e) {
                            System.err.println("*** SQLException: Could not fetch query results.");
                            System.err.println("\tMessage:   " + e.getMessage());
                            System.err.println("\tSQLState:  " + e.getSQLState());
                            System.err.println("\tErrorCode: " + e.getErrorCode());
                        }
                    }
                    else {
                        System.out.println("Your ski pass has either not yet expired, or still has remaining uses!");
                        return;
                    }
                }
            }
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    public static void addEquipmentInventory(Scanner scanner, Connection dbconn) {
        System.out.println("""
            Please add all necessary fields, and SEPARATE THEM WITH COMMAS
            <Type (String: boots, poles, snowboard, skis, gear)>, <Size (XS, S, M, L, XL)>, <Status (String: available, rented, retired)>
            """);

        String input = scanner.nextLine().trim();
        String[] attributes = input.split(",");

        int currentID = Collections.max(getEquipmentIDs(dbconn)) + 1;

        String query = String.format(
                "INSERT INTO dylanchapman.EQUIPMENT VALUES(%d, '%s', '%s', '%s')",
                currentID,
                attributes[0].trim().toLowerCase(), // Type
                attributes[1].trim().toUpperCase(), // Size
                attributes[2].trim()  // Status
        );

        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            System.out.printf("Equipment added successfully! Equipment ID is %d\n\n", currentID);
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not add equipment.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    public static void updateEquipmentInventory(Scanner scanner, Connection dbconn) {

    }

    public static void deleteEquipmentInventory(Scanner scanner, Connection dbconn) {

    }

    public static void addEquipmentRental(Scanner scanner, Connection dbconn) {

    }

    public static void updateEquipmentRental(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the RentalID of the record you wish to update:");
        int rentalID = scanner.nextInt();
        scanner.nextLine();
    
        if (!getRentalIDs(dbconn).contains(rentalID)) {
            System.out.println("RentalID does not exist!\n");
            return;
        }
    
        ArrayList<String> cols = new ArrayList<>();
        try(ResultSet answer = dbconn.getMetaData().getColumns(null, "DYLANCHAPMAN", "EQUIPMENTRENTALRECORD", null)) {
            while(answer.next()) {
                cols.add(answer.getString("COLUMN_NAME"));
            }
        }
        catch(SQLException e) {
            System.out.println("SQL error while reading column metadata.");
            return;
        }
    
        cols.remove("RENTALID");
    
        String sql = "UPDATE dylanchapman.EQUIPMENTRENTALRECORD SET ";
        String[] vals = new String[cols.size()];
    
        int i = 0;
        while(i < cols.size()) {
            String col = cols.get(i);
            String input;
    
            while(true) {
                System.out.println("Enter a new value for " + col + ": ");
                input = scanner.nextLine();
    
                if(col.equalsIgnoreCase("MEMBERID")) {
                    try {
                        int memId = Integer.parseInt(input);
                        if(!getMemberIDs(dbconn).contains(memId)) {
                            System.out.println("That MEMBERID does not exist. Try again.");
                            continue;
                        }
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Please enter a valid MEMBERID.");
                        continue;
                    }
                }
    
                if(col.equalsIgnoreCase("PASSID")) {
                    try {
                        int passId = Integer.parseInt(input);
                        if(!getPassIDs(dbconn).contains(passId)) {
                            System.out.println("That PASSID does not exist. Try again.");
                            continue;
                        }
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Please enter a valid PASSID.");
                        continue;
                    }
                }
                break;
            }
    
            vals[i] = input;
            sql += col + " = ?";
            if(i < cols.size() - 1) {
                sql += ", ";
            }
            i++;
        }
    
        sql += " WHERE RENTALID = ?";
    
        try {
            PreparedStatement prep = dbconn.prepareStatement(sql);
            i = 0;
            while(i < vals.length) {
                if(i == 3) {
                    try {
                        java.sql.Date sqlDate = java.sql.Date.valueOf(vals[i]); //  YYYY-MM-DD
                        prep.setDate(i + 1, sqlDate);
                    } 
                    catch(IllegalArgumentException e) {
                        System.out.println("se YYYY-MM-DD format.");
                        return;
                    }
                } 
                else {
                    prep.setString(i + 1, vals[i]);
                }
                i++;
            }
    
            prep.setInt(i + 1, rentalID);
    
            int count = prep.executeUpdate();
            if(count > 0) {
                System.out.println("record updated");
            } 
            else {
                System.out.println("no record found");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());}
    }
    

    public static void deleteEquipmentRental(Scanner scanner, Connection dbconn) {

    }

    public static void addLessonPurchase(Scanner scanner, Connection dbconn) {

    }

    public static void updateLessonPurchase(Scanner scanner, Connection dbconn) {

    }

    public static void deleteLessonPurchase(Scanner scanner, Connection dbconn) {

    }

    public static void GetMemberSkiLessonDetails(Scanner scanner, Connection dbconn) {
        System.out.println("Enter a memberID:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getMemberIDs(dbconn).contains(memberID)) {
            System.out.println("Member ID does not exist!\n");
            return;
        }


        String query = String.format("""
                SELECT
                    LessonPurchase.lessonID,
                    LessonPurchase.remainingUses,
                    Employee.name,
                    Lesson.startTime
                FROM andrewhicks.LessonPurchase
                
                JOIN Lesson ON LessonPurchase.lessonID = Lesson.lessonID
                JOIN Employee ON Lesson.instructorID = Employee.employeeID
                WHERE LessonPurchase.memberID = %d
                ORDER BY Lesson.startTime;
                """, memberID);

        Statement statement;
        ResultSet answer;

        try {
            statement = dbconn.createStatement();
            answer = statement.executeQuery(query);

            if (answer != null) {
                ResultSetMetaData answerMetaData = answer.getMetaData();

                for (int i = 1; i <= answerMetaData.getColumnCount(); i++)
                    System.out.print(answerMetaData.getColumnName(i) + "\t");

                System.out.println();

                while (answer.next()) {
                    System.out.println(answer.getString("lessonID") + "\t" + answer.getInt("remainingUses") + "\t" + answer.getString("name") + "\t" + answer.getString("startTime") + "\t" + answer.getString("endTime"));
                }
            }

        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not add equipment.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
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

        try {dbconn = DriverManager.getConnection(oracleURL, username, password);}
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

            while (decision != 10) {
                statement = dbconn.createStatement();

                switch (decision) {
                    case 1 -> {
                        String specifier;
                        System.out.println("Would you like to ADD, UPDATE, or DELETE a Member");

                        while (true) {
                            specifier = scanner.nextLine().trim().toUpperCase();

                            if (specifier.equals("ADD")) {
                                addMember(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("UPDATE")) {
                                updateMember(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("DELETE")) {
                                deleteMember(scanner, dbconn);
                                break;
                            }

                            else System.err.println("Invalid query specifier! Please try again.\n");
                        }
                    }
                    case 2 -> {
                        String specifier;
                        System.out.println("Would you like to ADD, UPDATE, or DELETE a Ski Pass");

                        while (true) {
                            specifier = scanner.nextLine().trim().toUpperCase();

                            if (specifier.equals("ADD")) {
                                addSkiPass(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("UPDATE")) {
                                updateSkiPass(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("DELETE")) {
                                deleteSkiPass(scanner, dbconn);
                                break;
                            }

                            else System.err.println("Invalid query specifier! Please try again.\n");
                        }
                    }
                    case 3 -> {
                        String specifier;
                        System.out.println("Would you like to ADD, UPDATE, or DELETE an Equipment Inventory Record?");

                        while (true) {
                            specifier = scanner.nextLine().trim().toUpperCase();

                            if (specifier.equals("ADD")) {
                                addEquipmentInventory(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("UPDATE")) {
                                updateEquipmentInventory(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("DELETE")) {
                                deleteEquipmentInventory(scanner, dbconn);
                                break;
                            }

                            else System.err.println("Invalid query specifier! Please try again.\n");
                        }
                    }
                    case 4 -> {
                        String specifier;
                        System.out.println("Would you like to ADD, UPDATE, or DELETE an Equipment Rental Record?");

                        while (true) {
                            specifier = scanner.nextLine().trim().toUpperCase();

                            if (specifier.equals("ADD")) {
                                addEquipmentRental(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("UPDATE")) {
                                updateEquipmentRental(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("DELETE")) {
                                deleteEquipmentRental(scanner, dbconn);
                                break;
                            }

                            else System.err.println("Invalid query specifier! Please try again.\n");
                        }
                    }
                    case 5 -> {
                        String specifier;
                        System.out.println("Would you like to ADD, UPDATE, or DELETE a Lesson Purchase Record");

                        while (true) {
                            specifier = scanner.nextLine().trim().toUpperCase();

                            if (specifier.equals("ADD")) {
                                addLessonPurchase(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("UPDATE")) {
                                updateLessonPurchase(scanner, dbconn);
                                break;
                            }
                            else if (specifier.equals("DELETE")) {
                                deleteLessonPurchase(scanner, dbconn);
                                break;
                            }

                            else System.err.println("Invalid query specifier! Please try again.\n");
                        }
                    }

                    case 6 -> GetMemberSkiLessonDetails(scanner, dbconn);
                    case 7 -> GetSkiPassUsageDetails();
                    case 8 -> GetOpenIntermediateTrails();
                    case 9 -> CustomQuery();
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