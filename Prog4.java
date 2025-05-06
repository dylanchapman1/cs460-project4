/*
 * Author(s): Dylan Chapman, Andrew Hicks, Adam Tilkens
 * Class:     CSC460
 * Date:      5/6/2025
 * Program:   Prog4.java
 */

import java.sql.*;
import java.sql.Date;
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

    /*
        Name: getMemberIDs

        Purpose: Query all memberIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all memberIDs in the database as ArrayList<Integer>
        */
    public static ArrayList<Integer> getMemberIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT MEMBERID FROM dylanchapman.MEMBER";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if(answer != null) {
                while(answer.next())
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

    /*
        Name: getRentalIDs

        Purpose: Query all memberIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all rentalIDs in the database as ArrayList<Integer>
        */
    public static ArrayList<Integer> getRentalIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT RENTALID FROM dylanchapman.EQUIPMENTRENTAL";

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

    /*
        Name: getItemIDs

        Purpose: Query all itemIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all itemIDs in the database as ArrayList<Integer>
        */
    public static ArrayList<Integer> getItemIDs(Connection dbconn) {

        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT ITEMID FROM dylanchapman.EQUIPMENT";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if(answer != null) {
                while(answer.next())
                    IDs.add(answer.getInt("ITEMID"));
            }
        }
        catch(SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
        return IDs;
    }

    /*
        Name: getPassIDs

        Purpose: Query all passIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all passIDs in the database as ArrayList<Integer>
        */
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

    /*
    public static ArrayList<Integer> getOrderIDs(Connection dbconn) {
        /*
        Name: getOrderIDs

        Purpose: Query all OrderIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all OrdersIDs in the database as ArrayList<Integer>
        */
    public static ArrayList<Integer> getOrderIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT ORDERID FROM dylanchapman.LESSONPURCHASE";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next()) {
                    IDs.add(answer.getInt("ORDERID"));
                }
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

    /*
        Name: getLessonIDs

        Purpose: Query all LessonIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all LessonIDs in the database as ArrayList<Integer>
        */
    public static ArrayList<Integer> getLessonIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT LessonID FROM dylanchapman.LESSON";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next())
                    IDs.add(answer.getInt("LESSONID"));
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
    
    public static ArrayList<Integer> getAuditIDs(Connection dbconn) {
        /*
        Name: getAuditIDs

        Purpose: Query all AuditIDs from the database for validation

        Pre-Conditions: Valid DB connection

        Parameters:
        dbconn (Connection): Connection to oracle database

        Returns: Returns a list of all AuditIDs in the database as ArrayList<Integer>
        */
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT auditID FROM dylanchapman.AuditLog";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next())
                    IDs.add(answer.getInt("auditID"));
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch AuditID.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
            System.exit(-1);

        }

        return IDs;
    }

    /*
    Name: addMember

    Purpose: Insert a new member record into the database

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
        */
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
            audit(dbconn, "Member", String.valueOf(currentID), "Add");
            System.out.printf("Member added successfully! Your member ID is %s\n\n", currentID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: updateMember

    Purpose: Updates an existing member record in the database

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
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
                "UPDATE dylanchapman.Member SET PHONENUMBER = %d, EMAIL = '%s', EMERGENCYCONTACTNUMBER = %d WHERE MEMBERID = %d",
                Long.parseLong(attributes[0]),
                attributes[1].trim(),
                Long.parseLong(attributes[2].trim()),
                memberID
        );

        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            audit(dbconn, "Member", String.valueOf(memberID), "Update");
            System.out.printf("Your information has been updated successfully! Your member ID is %d\n\n", memberID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: deleteMember

    Purpose: Deletes a member from the DB, making sure that before deletion no
             existing data like skip passes or lessons are active for the given
             user

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void deleteMember(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the MemberID of the user you wish to delete:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getMemberIDs(dbconn).contains(memberID)) {
            System.out.println("Member ID does not exist!\n");
            return;
        }

        int skiPassID = 0;
        String getSkiPass = String.format("SELECT passID FROM dylanchapman.SkiPass WHERE memberID = %d", memberID);

        Statement statement = null;
        ResultSet answer = null;
        try {
            statement = dbconn.createStatement();
            answer = statement.executeQuery(getSkiPass);

            if (answer != null) {
                while (answer.next())
                    skiPassID = answer.getInt("passID");
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not generate a query to find the skiPass of a member.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }

        System.out.println("The passID of memberID " + memberID + " is " + skiPassID + "\n\n");

        String activeSkiPassQuery = String.format("SELECT COUNT(*) FROM dylanchapman.SkiPass WHERE memberID = %d AND active = 1", memberID);
        String openRentalRecordsQuery = String.format("SELECT COUNT(*) FROM dylanchapman.EquipmentRental WHERE passID = %d AND returnStatus = 0", skiPassID);
        String unusedLessonSessionsQuery = String.format("SELECT COUNT(*) FROM dylanchapman.LessonPurchase WHERE memberID = %d AND remainingUses > 0", memberID);

        try {
            int skiPassCount = 0, openRentalCount = 0, unusedLessonsCount = 0;

            // Query 1: Active Ski Passes
            try (Statement stmt1 = dbconn.createStatement();
                 ResultSet skiPassAnswer = stmt1.executeQuery(activeSkiPassQuery)) {
                if (skiPassAnswer.next())
                    skiPassCount = skiPassAnswer.getInt(1);
            }

            // Query 2: Open Rental Records
            try (Statement stmt2 = dbconn.createStatement();
                 ResultSet openRentalAnswer = stmt2.executeQuery(openRentalRecordsQuery)) {
                if (openRentalAnswer.next())
                    openRentalCount = openRentalAnswer.getInt(1);
            }

            // Query 3: Unused Lesson Sessions
            try (Statement stmt3 = dbconn.createStatement();
                 ResultSet unusedLessonsAnswer = stmt3.executeQuery(unusedLessonSessionsQuery)) {
                if (unusedLessonsAnswer.next())
                    unusedLessonsCount = unusedLessonsAnswer.getInt(1);
            }

            if (skiPassCount == 0 && openRentalCount == 0 && unusedLessonsCount == 0) {
                // Safe to delete
                try {
                    dbconn.setAutoCommit(false); // Start transaction

                    // Delete lift usage logs
                    String deleteLiftUsage = String.format("DELETE FROM dylanchapman.LiftUsage WHERE passID = %d", skiPassID);
                    try (Statement stmt = dbconn.createStatement()) {
                        stmt.executeUpdate(deleteLiftUsage);
                    }

                    // Delete ski pass data
                    String deleteSkiPass = String.format("DELETE FROM dylanchapman.SkiPass WHERE memberID = %d", memberID);
                    try (Statement stmt = dbconn.createStatement()) {
                        stmt.executeUpdate(deleteSkiPass);
                    }

                    // Delete rental history
                    String deleteRentals = String.format("DELETE FROM dylanchapman.EquipmentRental WHERE passID = %d", skiPassID);
                    try (Statement stmt = dbconn.createStatement()) {
                        stmt.executeUpdate(deleteRentals);
                    }

                    // Delete lesson purchases
                    String deleteLessonPurchases = String.format("DELETE FROM dylanchapman.LessonPurchase WHERE memberID = %d", memberID);
                    try (Statement stmt = dbconn.createStatement()) {
                        stmt.executeUpdate(deleteLessonPurchases);
                    }

                    // Finally, delete the member
                    String deleteMember = String.format("DELETE FROM dylanchapman.Member WHERE memberID = %d", memberID);
                    try (Statement stmt = dbconn.createStatement()) {
                        stmt.executeUpdate(deleteMember);
                        audit(dbconn, "Member", String.valueOf(memberID), "Delete");
                    }

                    dbconn.commit(); // All deletions succeeded
                    System.out.println("Member and all related data successfully deleted.");

                } catch (SQLException e) {
                    try {
                        dbconn.rollback(); // Undo changes if something fails
                        System.out.println("Deletion failed. All changes rolled back.");
                    }
                    catch (SQLException rollbackEx) {
                        System.err.println("Rollback failed: " + rollbackEx.getMessage());
                    }
                    System.err.println("Error during deletion: " + e.getMessage());
                }
                finally {
                    try {
                        dbconn.setAutoCommit(true); // Restore default
                    }
                    catch (SQLException ex) {
                        System.err.println("Failed to reset auto-commit: " + ex.getMessage());
                    }
                }



            }
            else {
                System.out.println("Member cannot be deleted. Outstanding obligations exist:");
                if (skiPassCount > 0) System.out.println(" - Active ski passes.");
                if (openRentalCount > 0) System.out.println(" - Open equipment rentals.");
                if (unusedLessonsCount > 0) System.out.println(" - Unused lesson sessions.");
                System.out.println();
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: addSkiPass

    Purpose: Insert a new ski pass record into the database

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

     None, as the method is void
    */
    public static void addSkiPass(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the MemberID of the user you wish to buy a Ski Pass for:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if(!getMemberIDs(dbconn).contains(memberID)) {
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

        int passID = Collections.max(getPassIDs(dbconn)) + 1;

        String query = String.format(
                "INSERT INTO dylanchapman.SkiPass VALUES(%d, %d, %d, %d, TO_DATE('2025-12-31', 'YYYY-MM-DD'), %d, %d)",
                passID,
                memberID,
                skiPassMap.get(selectedPass)[0],
                skiPassMap.get(selectedPass)[0],
                skiPassMap.get(selectedPass)[1],
                1
        );


        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            audit(dbconn, "SkiPass", String.valueOf(passID), "Add");
            System.out.printf("Ski Pass purchased for member ID %s!\n\n", memberID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }

    }

    /*
    Name: updateSkiPass

    Purpose: Updates a ski pass record from the database, in cases of usage at
             a resort, an incorrect usage deduction, or just manually overriding

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void updateSkiPass(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the PassID of the user you wish to update a Ski Pass for:");
        int passID = scanner.nextInt();
        scanner.nextLine();
        if(!getPassIDs(dbconn).contains(passID)) {
            System.out.println("PassID does not exist!\n");
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

        String sql = "update dylanchapman.SKIPASS SET ";
        String[] vals = new String[cols.size()];

        int i = 0;
        while(i < cols.size()) {
            String col = cols.get(i);
            System.out.println("Enter a new value for " + col + ": ");
            vals[i] = scanner.nextLine();

            if(i == 0) {
                ArrayList<Integer> memIds = getMemberIDs(dbconn);
                if(!memIds.contains(Integer.parseInt(vals[i]))) {
                    System.out.println("MemberID does not exist!\n");
                    return;
                }
            }

            sql += col + " = ?";
            if(i < cols.size()-1) {
                sql += ", ";

            }
            i++;
        }

        sql += " where passid = ?";

        try {
            PreparedStatement prep = dbconn.prepareStatement(sql);
            i = 0;
            while(i < vals.length) {
                if (i == 3) {
                    try {
                        Date sqlDate = Date.valueOf(vals[i]); // YYYY-MM-DD
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
            int count = prep.executeUpdate();
            if(count > 0) {
            	audit(dbconn, "SkiPass", String.valueOf(passID), "Update");
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

    /*
    Name: deleteSkiPass

    Purpose: Deletes a ski pass from the DB, making sure that before deletion,
             the ski pass has either expired, or has no remaining uses left

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void deleteSkiPass(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the PassID of the user you wish to delete a Ski Pass for:");
        int passID = scanner.nextInt();


        if (!getPassIDs(dbconn).contains(passID)) {
            System.out.println("PassID does not exist!\n");
            return;
        }

        String query = String.format("SELECT MEMBERID, REMAININGUSES, EXPIRATIONDATE FROM dylanchapman.SkiPass WHERE PASSID = %d", passID);
        ResultSet answer = null;

        try {
            Statement statement = dbconn.createStatement();
            answer = statement.executeQuery(query);            
            

            if (answer != null) {
                while (answer.next()) {
                    int remaining = Integer.parseInt(answer.getString("REMAININGUSES"));
                    int memberID = Integer.parseInt(answer.getString("MEMBERID"));
                    String expired = answer.getString("EXPIRATIONDATE").split(" ")[0];

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate currentDate = LocalDate.now();
                    LocalDate expirationDate = LocalDate.parse(expired, formatter);

                    if (currentDate.isAfter(expirationDate) || remaining == 0) {
                        String deleteQuery = String.format("UPDATE dylanchapman.SkiPass SET ACTIVE = %d WHERE PASSID = %d", 0, passID);
                        try {
                            Statement deleteStatement = dbconn.createStatement();
                            deleteStatement.executeUpdate(deleteQuery);
                            audit(dbconn, "SkiPass", String.valueOf(passID), "Delete");
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

    /*
    Name: addEquipmentInventory

    Purpose: Insert a new equipment piece into the database. When doing so, the
             piece of equipment must align with its sizing chart. While there is
             no error checking for this, it still must be adhered to

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void addEquipmentInventory(Scanner scanner, Connection dbconn) {
        // Assume an admin-only function for now
        System.out.println("""
                What type of equipment would you like to add (enter the corresponding integer):
                    1. Boots     | Sizes 4.0 to 14.0 (half sizes)
                    2. Poles     | Lengths 100 cm to 140 cm
                    3. Skis      | Lengths 115 cm to 200 cm
                    4. Snowboard | Lengths 90 cm to 178 cm
                    5. Helmet    | XS, S, M, L, XL
                """);
        int equipmentType = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        Map<Integer, String> inputToEquipment = Map.of(
                1, "BOOTS",
                2, "POLES",
                3, "SKIS",
                4, "SNOWBOARD",
                5, "HELMET"
        );

        System.out.println("Enter the corresponding size for the equipment:");
        String equipmentSize = scanner.nextLine();

        int itemID = Collections.max(getItemIDs(dbconn)) + 1;
        String query = String.format("INSERT INTO dylanchapman.Equipment VALUES (%d, '%s', '%s', %d, %d)", itemID, inputToEquipment.get(equipmentType), equipmentSize, 0, 1);

        try {
            System.out.println("Equipment Type: " + equipmentType);
            System.out.println("Equipment Size: " + equipmentSize);

            Statement statement = dbconn.createStatement();
            statement.executeQuery(query);
            audit(dbconn, "Equipment", String.valueOf(itemID), "Add");
            System.out.println("Equipment added!\n");
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }


    }

    /*
    Name: updateEquipmentInventory

    Purpose: Updates an equipment piece in the ski resort the DB, in case of
    reclassification, mislabeling, or reassignment to a different size. Like its
    'add' counterpart, changing a piece of equipment still needs to adhere to type
    safety to ensure consistency in the DB

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void updateEquipmentInventory(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the ItemID of the equipment you wish to update:");
        int itemID = scanner.nextInt();
        scanner.nextLine();

        if(!getItemIDs(dbconn).contains(itemID)) {
            System.out.println("ItemID does not exist!\n");
            return;
        }

        System.out.println("""
                What type of equipment would you like to change it to (enter the corresponding integer):
                    1. Boots     | Sizes 4.0 to 14.0 (half sizes)
                    2. Poles     | Lengths 100 cm to 140 cm
                    3. Skis      | Lengths 115 cm to 200 cm
                    4. Snowboard | Lengths 90 cm to 178 cm
                    5. Helmet    | XS, S, M, L, XL
                """);

        int equipmentType = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, String> inputToEquipment = Map.of(
                1, "BOOTS",
                2, "POLES",
                3, "SKIS",
                4, "SNOWBOARD",
                5, "HELMET"
        );

        if(!inputToEquipment.containsKey(equipmentType)) {
            System.out.println("Invalid equipment type selected.");
            return;
        }

        System.out.println("Enter the corresponding size for the equipment:");
        String equipmentSize = scanner.nextLine();

        String updateQuery = "UPDATE dylanchapman.EQUIPMENT SET TYPE = ?, ITEMSIZE = ?, RENTED = ?, ACTIVE = ? WHERE ITEMID = ?";

        try(PreparedStatement prep = dbconn.prepareStatement(updateQuery)) {
            prep.setString(1, inputToEquipment.get(equipmentType));
            prep.setString(2, equipmentSize.toUpperCase());
            prep.setInt(3,0);
            prep.setInt(4, 1);
            prep.setInt(5, itemID);

            int count = prep.executeUpdate();
            if(count > 0) {
            	audit(dbconn, "Equipment", String.valueOf(itemID), "Delete");
                System.out.println("Equipment updated successfully!\n");
            }
            else {
                System.out.println("No matching record found.\n");
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not update equipment.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: deleteEquipmentInventory

    Purpose: Deletes an equipment listing from the DB, making sure that it is
             still archived for record-keeping purposes

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void deleteEquipmentInventory(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the ItemID of the equipment you wish to archive:");
        int itemID = scanner.nextInt();
        scanner.nextLine();

        String rentalCheck = String.format(
                "SELECT COUNT(*) as current_usage FROM dylanchapman.EQUIPMENTRENTAL " +
                        "WHERE ITEMID = %d AND RETURNSTATUS = 0", itemID
        );

        try (Statement stmt = dbconn.createStatement();
             ResultSet rs = stmt.executeQuery(rentalCheck)) {

            if(rs.next() && rs.getInt("current_usage") > 0) {
                System.out.println("This equipment is currently rented or reserved and cannot be archived.");
                return;
            }

        }
        catch(SQLException e) {
            System.out.println("SQLError: " + e.getMessage());
            return;
        }

        String updateQuery = String.format(
                "UPDATE dylanchapman.EQUIPMENT SET ACTIVE = 0 WHERE ITEMID = %d", itemID
        );

        try (Statement stmt = dbconn.createStatement()) {
            int count = stmt.executeUpdate(updateQuery);
            if (count > 0) {
            	audit(dbconn, "Equipment", String.valueOf(itemID), "Delete");
                System.out.printf("Equipment with ItemID %d has been archived successfully.\n", itemID);
            }
            else {
                System.out.println("Update failed — equipment was not archived.");
            }

        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not archive equipment.");
            System.err.println("\tMessage:   " + e.getMessage());
        }
    }

    /*
    Name: addEquipmentRental

    Purpose: Insert a new equipment rental record into the database, making sure
             that whoever wants this equipment actually exists in the resort system

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void addEquipmentRental(Scanner scanner, Connection dbconn) {
        String query;
        System.out.println("""
                        Please add all necessary fields, and SEPARATE THEM WITH COMMAS
                        <PassID (int)>, <ReturnStatus (int)>, <ItemID (int)>, <Archived (int)>, <StartDate (YYYY-MM-DD)>, <EndDate (YYYY-MM-DD)>
                        """);

        String input  = scanner.nextLine().trim();
        String[] attributes = input.split(",");
        int currentID = Collections.max(getRentalIDs(dbconn)) + 1;

        try {
            int passId = Integer.parseInt(attributes[0].trim());
            if (!getPassIDs(dbconn).contains(passId)) {
                System.out.println("That PASSID does not exist. Try again.");
                return;
            }
            int returnStatus = Integer.parseInt(attributes[1].trim());

            int itemId = Integer.parseInt(attributes[2].trim());
            if (!getItemIDs(dbconn).contains(itemId)) {
                System.out.println("That ITEMID does not exist. Try again.");
                return;
            }

            int archived = Integer.parseInt(attributes[3].trim());

            String startDate = attributes[4].trim();
            String endDate = attributes[5].trim();


            query = String.format(
                    "INSERT INTO dylanchapman.EquipmentRental " +
                            "VALUES (%d, %d, %d, %d, %d, TO_TIMESTAMP('%s', 'YYYY-MM-DD'), TO_TIMESTAMP('%s', 'YYYY-MM-DD'))",
                    currentID, passId, returnStatus, itemId, archived, startDate, endDate
            );

            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            audit(dbconn, "EquipmentRental", String.valueOf(currentID), "Add");
            System.out.printf("Rental added successfully! Your rental ID is %d\n\n", currentID);
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }

    }

    /*
    Name: updateEquipmentRental

    Purpose: Updates an equipment rental record from the database, by having an
             admin enter all quantifying information. Updates can also change the
             return status when a piece of equipment is returned

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void updateEquipmentRental(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the RentalID of the record you wish to update:");
        int rentalID = scanner.nextInt();
        scanner.nextLine();

        if(!getRentalIDs(dbconn).contains(rentalID)) {
            System.out.println("RentalID does not exist!\n");
            return;
        }

        System.out.println("""
            Please enter the updated values, separated by commas:
            <PassID (int)>, <ReturnStatus (int)>, <ItemID (int)>, <Archived (int)>, <StartDate (YYYY-MM-DD HH:MM:SS)>, <EndDate (YYYY-MM-DD HH:MM:SS)>
        """);

        String input = scanner.nextLine().trim();
        String[] attributes = input.split(",");

        if(attributes.length != 6) {
            System.out.println("Please provide exactly 6 fields.");
            return;
        }

        try {
            int passId = Integer.parseInt(attributes[0].trim());
            if(!getPassIDs(dbconn).contains(passId)) {
                System.out.println("That PASSID does not exist. Try again.");
                return;
            }

            int returnStatus = Integer.parseInt(attributes[1].trim());

            int itemId = Integer.parseInt(attributes[2].trim());
            if(!getItemIDs(dbconn).contains(itemId)) {
                System.out.println("That ITEMID does not exist. Try again.");
                return;
            }

            int archived = Integer.parseInt(attributes[3].trim());

            String startDate = attributes[4].trim();
            String endDate = attributes[5].trim();

            String sql = "UPDATE dylanchapman.EQUIPMENTRENTAL SET " +
                    "PASSID = ?, RETURNSTATUS = ?, ITEMID = ?, ARCHIVED = ?, " +
                    "STARTDATE = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ENDDATE = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS') " +
                    "WHERE RENTALID = ?";

            try(PreparedStatement prep = dbconn.prepareStatement(sql)) {
                prep.setInt(1, passId);
                prep.setInt(2, returnStatus);
                prep.setInt(3, itemId);
                prep.setInt(4, archived);
                prep.setString(5, startDate);
                prep.setString(6, endDate);
                prep.setInt(7, rentalID);
                int count = prep.executeUpdate();
                if(count > 0) {
                	audit(dbconn, "EquipmentRental", String.valueOf(rentalID), "Update");
                    System.out.println("Rental record updated successfully!\n");
                }
                else {
                    System.out.println("No matching record found.\n");
                }
            }

        }
        catch(NumberFormatException e) {
            System.out.println("Invalid number format in one or more fields. Please try again.");
        }
        catch(SQLException e) {
            System.err.println("*** SQLException: Could not update rental.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }


    /*
    Name: deleteEquipmentRental

    Purpose: Deletes an equipment rental record from the DB, only if the record
             was spuriously created AND the equipment is not being used. All
             updates and deletions will be logged

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void deleteEquipmentRental(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the RentalID of the equipment you wish to archive:");
        int rentalID = scanner.nextInt();
        scanner.nextLine();

        // Step 1: Get PASSID, ITEMID, STARTDATE, ENDDATE
        String rentalInfoQuery = String.format(
                "SELECT PASSID, ITEMID, STARTDATE, ENDDATE FROM dylanchapman.EQUIPMENTRENTAL WHERE RENTALID = %d",
                rentalID
        );

        int passId = -1;
        int itemId = -1;
        Timestamp startDate = null;
        Timestamp endDate = null;

        try(Statement stmt = dbconn.createStatement();
            ResultSet rs = stmt.executeQuery(rentalInfoQuery)) {

            if(rs.next()) {
                passId = rs.getInt("PASSID");
                itemId = rs.getInt("ITEMID");
                startDate = rs.getTimestamp("STARTDATE");
                endDate = rs.getTimestamp("ENDDATE");
            }
            else {
                System.out.println("No rental found with that RentalID.");
                return;
            }

        }
        catch(SQLException e) {
            System.err.println("Error retrieving rental details: " + e.getMessage());
            return;
        }

        String usageCheckQuery = String.format(
                "SELECT COUNT(*) AS usage_count FROM dylanchapman.LIFTUSAGE " +
                        "WHERE PASSID = %d AND TIME BETWEEN TO_TIMESTAMP('%s', 'YYYY-MM-DD HH24:MI:SS') " +
                        "AND TO_TIMESTAMP('%s', 'YYYY-MM-DD HH24:MI:SS')",
                passId,
                startDate.toString().substring(0, 19),
                endDate.toString().substring(0, 19)
        );

        try(Statement stmt = dbconn.createStatement()) {
            ResultSet rs = stmt.executeQuery(usageCheckQuery);

            if(rs.next() && rs.getInt("usage_count") > 0) {
                System.out.println("This rental has lift usage and cannot be archived.");
                return;
            }

        } catch (SQLException e) {
            System.err.println("Error checking lift usage: " + e.getMessage());
            return;
        }

        String archiveQuery = String.format(
                "UPDATE dylanchapman.EQUIPMENTRENTAL SET ARCHIVED = 1 WHERE RENTALID = %d",
                rentalID
        );

        try(Statement stmt = dbconn.createStatement()) {
            int rows = stmt.executeUpdate(archiveQuery);
            if(rows > 0) {
            	audit(dbconn, "EquipmentRental", String.valueOf(rentalID), "Delete");
                System.out.printf("Rental with RentalID %d archived successfully.\n", rentalID);
            }
            else {
                System.out.println("Archiving failed — no rows were updated.");
            }

        }
        catch (SQLException e) {
            System.err.println("Error archiving rental: " + e.getMessage());
        }
    }

    /*
    Name: addLessonPurchase

    Purpose: Inserts a new lesson rental record into the database, making sure
             that whoever tried to buy this lesson actually exists in the resort
             system

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void addLessonPurchase(Scanner scanner, Connection dbconn) {
        String query;
        System.out.println("""
                        Please add all necessary fields, and SEPARATE THEM WITH COMMAS
                        <LessonID (int)>, <MemberID> (int), <PurchaseDate (YYYY-MM-DD)>, <RemainingUses(int)>
                        """);
        //TotalUses will be set to 0 by default.

        String input = scanner.nextLine().trim();
        String[] attributes = input.split(",");

        ArrayList<Integer> lessonIDs = getLessonIDs(dbconn);
        if(!lessonIDs.contains(Integer.parseInt(attributes[0].trim()))) {
            System.out.println("That LessonID does not exist. Try again.");
            return;
        }

        ArrayList<Integer> memIDs = getMemberIDs(dbconn);
        if(!memIDs.contains(Integer.parseInt(attributes[1].trim()))) {
            System.out.println("That MemberID does not exist. Try again.");
            return;
        }


        int currentID = Collections.max(getOrderIDs(dbconn)) + 1;
        query = String.format(
                "INSERT INTO dylanchapman.LessonPurchase VALUES(%d, '%s', '%s', TO_DATE('%s', 'YYYY-MM-DD'), 0, '%s')",
                currentID, // OrderID - PK
                attributes[0].trim(), // LessonID
                attributes[1].trim(), // MemberID
                attributes[2].trim(), //Purchase Time (Date)
                attributes[3].trim() // Remaining Uses
        );


        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            audit(dbconn, "LessonPurchase", String.valueOf(currentID), "Add");
            System.out.printf("LessonPurchase ID %d has successfully been registered. \n", currentID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }


    }

    /*
    Name: updateLessonPurchase

    Purpose: Updates a  lesson rental record from the database, by having an admin
             reenter all important credentials. Updates reflect usage or
             adjustments to the purchase

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void updateLessonPurchase(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the Lesson OrderID to be updated:");
        int orderID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getOrderIDs(dbconn).contains(orderID)) {
            System.out.println("OrderID "+ orderID +" does not exist!\n");
            return;
        }

        String query;
        System.out.println("""
                        Please reenter all necessary fields, and SEPARATE THEM WITH COMMAS
                        <LessonID (int)>, <MemberID (int)>, <PurchaseDate (YYYY-MM-DD)>, <TotalUses (int)>, <RemainingUses(int)>
                        """);

        String input  = scanner.nextLine().trim();
        String[] attributes = input.split(",");

        ArrayList<Integer> lessonIDs = getLessonIDs(dbconn);
        if(!lessonIDs.contains(Integer.parseInt(attributes[0].trim()))) {
            System.out.println("That LessonID does not exist. Try again.");
            return;
        }

        ArrayList<Integer> memIDs = getMemberIDs(dbconn);
        if(!memIDs.contains(Integer.parseInt(attributes[1].trim()))) {
            System.out.println("That MemberID does not exist. Try again.");
            return;
        }

        query = String.format(
                "UPDATE dylanchapman.LessonPurchase SET LessonID = '%s', MemberID = '%s', PurchaseDate = TO_DATE('%s', 'YYYY-MM-DD'), "
                        + "TotalUses = '%s', RemainingUses = '%s' WHERE OrderID = '%d'",
                attributes[0].trim(), // LessonID
                attributes[1].trim(), // MemberID
                attributes[2].trim(), // Purchase Time (Date)
                attributes[3].trim(), // Total Uses
                attributes[4].trim(), // Remaining Uses
                orderID // OrderID
        );

        try {
            Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
            audit(dbconn, "LessonPurchase", String.valueOf(orderID), "Update");
            System.out.printf("Lesson Purchase Order ID %d has been succesfully updated\n\n", orderID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: deleteLessonPurchase

    Purpose: Deletes a lesson purchase record from the DB, only if no lessons
             have been used by the purchaser

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void deleteLessonPurchase(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter the LessonPurchase OrderID to be deleted.");
        int orderID = scanner.nextInt();
        scanner.nextLine();

        if (!getOrderIDs(dbconn).contains(orderID)) {
            System.out.println("No such OrderID within LessonPurchase!\n");
            return;
        }
        String queryGet = String.format(
                "SELECT * FROM dylanchapman.lessonpurchase WHERE OrderID = '%s' ", orderID);

        String queryDelete = String.format(
                "DELETE FROM dylanchapman.lessonpurchase WHERE OrderID = '%s'", orderID);

        try {
            Statement statement = dbconn.createStatement();
            ResultSet result = statement.executeQuery(queryGet);
            int fetchedTotalUses = -1;
            if(result.next()) {
                fetchedTotalUses = result.getInt("TotalUses");
            }
            System.out.println("Retrieved TotalUse value: " + fetchedTotalUses);

            // We can ONLY delete a LessonPurchase record if TotalUses = 0 (unused)
            if (fetchedTotalUses == 0) {
                statement.executeUpdate(queryDelete);
                audit(dbconn, "LessonPurchase", String.valueOf(orderID), "Delete");
                System.out.printf("Lesson Purchase Order ID %d has been succesfully deleted.\n\n", orderID);
            }else {
                System.out.printf("The Lesson Purchase OrderID #%s has '%s' use(s), so cannot be deleted.\n\n",
                        orderID, fetchedTotalUses);                
            }            
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: GetMemberSkiLessonDetails

    Purpose: Lists all the ski lessons a particular member has purchased,
             including the # of remaining sessions, instructor name, and
             scheduled time of said lessons

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void GetMemberSkiLessonDetails(Scanner scanner, Connection dbconn) {
        System.out.println("Enter a memberID:");
        int memberID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getMemberIDs(dbconn).contains(memberID)) {
            System.out.println("Member ID does not exist!\n");
            return;
        }

        String query = "SELECT LessonPurchase.lessonID, LessonPurchase.remainingUses as \"Remaining Uses\", Employee.name as \"Instructor Name\", startTime as \"Start Time\" FROM dylanchapman.LessonPurchase JOIN dylanchapman.Lesson ON LessonPurchase.lessonID = Lesson.lessonID JOIN dylanchapman.Employee ON Lesson.instructorID = Employee.employeeID WHERE LessonPurchase.memberID = " + memberID;

        Statement statement;
        ResultSet answer;
        try {
            statement = dbconn.createStatement();
            answer = statement.executeQuery(query);

            if (answer != null) {
                ResultSetMetaData answerMetaData = answer.getMetaData();
                System.out.println("\nPurchased Ski Lessons:");

                for (int i = 1; i <= answerMetaData.getColumnCount(); i++)
                    System.out.print(answerMetaData.getColumnName(i) + "\t");
                System.out.println();

                while (answer.next())
                    System.out.println(answer.getObject("LESSONID") + "\t\t" + answer.getObject("Remaining Uses") + "\t\t" + answer.getObject("Instructor Name") + "\t" + answer.getObject("Start Time"));

                System.out.println();
            }

        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not add equipment.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    /*
    Name: GetSkiPassUsageDetails

    Purpose: For a given ski pass, list all lift rides and equipment rentals
             associated with it, along with timestamps and return status

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void GetSkiPassUsageDetails(Scanner scanner, Connection dbconn) {
        System.out.println("Enter a passID (Your ID for your Ski Pass:");
        int passID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getPassIDs(dbconn).contains(passID)) {
            System.out.println("Ski Pass ID does not exist!\n");
            return;
        }

        String query1 = String.format("SELECT passID, liftName, time FROM dylanchapman.LiftUsage WHERE passID = %d", passID);
        String query2 = String.format("SELECT rentalID, itemID, returnStatus FROM dylanchapman.EquipmentRental WHERE passID = %d", passID);

        Statement statement1, statement2;
        ResultSet answer1, answer2;
        try {
            ResultSetMetaData answerMetaData;
            statement1 = dbconn.createStatement();
            statement2 = dbconn.createStatement();

            answer1 = statement1.executeQuery(query1);
            answer2 = statement2.executeQuery(query2);

            if (answer1 != null && answer2 != null) {
                System.out.println("\nLift Ride Information");
                answerMetaData = answer1.getMetaData();
                System.out.print("\t");
                for (int i = 1; i <= answerMetaData.getColumnCount(); i++)
                    System.out.print(answerMetaData.getColumnName(i) + "\t");
                System.out.println();


                while (answer1.next())
                    System.out.println("\t" + answer1.getObject("passID") + "\t" + answer1.getObject("liftName") + "\t" + answer1.getObject("time"));


                System.out.println("\nEquipment Rental Information");
                answerMetaData = answer2.getMetaData();
                System.out.print("\t");
                for (int i = 1; i <= answerMetaData.getColumnCount(); i++)
                    System.out.print(answerMetaData.getColumnName(i) + "\t");
                System.out.println();

                while (answer2.next())
                    System.out.println("\t" + answer2.getObject("rentalID") + "\t\t" + answer2.getObject("itemID") + "\t" + answer2.getObject("returnStatus"));

                System.out.println();
            }

        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not add equipment.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }

    }

    /*
    Name: GetOpenIntermediateTrails

    Purpose: List all open trails suitable for intermediate-level skiers, along
             with their category and connected lifts that are currently operational

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: Returns: None, as the method is void
    */
    public static void GetOpenIntermediateTrails(Connection dbconn) {
        // Because each Lift has a "connected" field which can match a trail's starting point,
        // We can use this to join tables to recieve only Trail-Lift relations
        // with these matching points and filter by trails that are
        // intermediate level (2) AND have both trail and lift in 'Open' Status.

        String query = """
        SELECT TrailName, Category,
            LISTAGG(LiftName, ', ') WITHIN GROUP (ORDER BY LiftName) AS ConnectedLifts
            FROM dylanchapman.Trail
            JOIN dylanchapman.Lift ON Trail.StartLocation = Lift.Destination
            WHERE Trail.Difficulty = 2 AND Trail.Status = 'Open' AND Lift.Status = 'Open'
            GROUP BY TrailName, Category        
        """;

        // String Length for formatting purposes.
        // Values are based on Varchar2(x) as defined in DB (with some padding)
        final int trailStrLen = 30 + 5;
        final int catStrLen = 20 + 5;
        final int liftStrLen = 20;

        try {
            Statement statement = dbconn.createStatement();
            ResultSet result = statement.executeQuery(query);

            ResultSetMetaData resultMetadata = result.getMetaData();
            int counter = 0;

            // Output Table Headers
            System.out.println('\n');
            System.out.print(padRight(resultMetadata.getColumnName(1), trailStrLen));  // TrailName
            System.out.print(padRight(resultMetadata.getColumnName(2), catStrLen));	// Category
            System.out.print(padRight(resultMetadata.getColumnName(3), liftStrLen));	// ConnectedTrails
            System.out.println("\n_________________________________________________________________________________\n");

            while(result.next()) {
                String trailName = result.getString("TrailName");
                String category = result.getString("Category");

                String connectedLifts = result.getString("ConnectedLifts");
                String[] lifts = connectedLifts.split(",\\s*");

                System.out.print(padRight(trailName, trailStrLen));
                System.out.print(padRight(category, catStrLen));

                for (String lift : lifts){
                    System.out.print(padRight(lift,liftStrLen));
                    System.out.print('\n' + padRight("",trailStrLen + catStrLen));
                }

                System.out.println();
                counter++;
            }
            System.out.printf("\n\nFound %d intermediate trails currently open.\n\n", counter);
        }


        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }


    }

    /*
    Name: CustomQuery

    Purpose: Find all members who have rented equipment AND purchased a ski pass
             with a price greater than some threshold (the provided value), along
             with the details of their equipment rentals and ski pass
             information. The idea is that this query will provide insight into
             "big spenders" at the resort, and incentivize them to spend more
             w/ coupons or promotions

    Pre-Conditions: Valid DB connection, initialized scanner object

    Parameters:
    dbconn (Connection): Connection to oracle database

    Returns: None, as the method is void
    */
    public static void CustomQuery(Scanner scanner, Connection dbconn) {
        System.out.println("Please enter your desired price:");
        int price = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        String query = String.format("""
        SELECT m.memberID, 
        m.name, 
        m.email, 
        sp.passID, 
        sp.price, 
        sp.remainingUses, 
        er.rentalID, 
        er.startdate, 
        e.type, 
        e.itemsize 
        FROM dylanchapman.Member m 
        JOIN dylanchapman.SkiPass sp ON m.memberID = sp.memberID 
        JOIN dylanchapman.EquipmentRental er ON sp.passID = er.passID 
        JOIN dylanchapman.Equipment e ON er.itemID = e.itemID 
        WHERE sp.price > %d       
        """, price);

        try {
            Statement statement = dbconn.createStatement();
            ResultSet result = statement.executeQuery(query);

            ResultSetMetaData resultMetadata = result.getMetaData();
            System.out.printf("%-10s %-30s %-30s %-10s %-10s %-10s %-10s %-20s %-20s %-10s%n",
                    "MemberID", "Name", "Email", "PassID", "Price", "RemUses", "RentalID",
                    "StartDate", "EquipType", "Size");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");

            while(result.next()) {
                int memberID = result.getInt("memberID");
                String name = result.getString("name");
                String email = result.getString("email");
                int passID = result.getInt("passID");
                int passPrice = result.getInt("price");
                int remainingUses = result.getInt("remainingUses");
                int rentalID = result.getInt("rentalID");
                Timestamp startDate = result.getTimestamp("startdate");
                String equipmentType = result.getString("type");
                String equipmentSize = result.getString("itemsize");

                System.out.printf("%-10d %-30s %-30s %-10d %-10d %-10d %-10d %-20s %-20s %-10s%n",
                        memberID, name, email, passID, passPrice, remainingUses, rentalID,
                        startDate.toString(), equipmentType, equipmentSize);
            }
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    }

    // Helper function for formatting, fills the right of a string
    // with spaces to reach 'length' total chars.
    public static String padRight(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length); // Trim if too long
        }
        return String.format("%-" + length + "s", str); // Pad with spaces
    }
    
    /*
    Name: audit

    Purpose: Accepts values to log into auditlog table for purposes of record keeping.

    Pre-Conditions: Valid DB connection

    Parameters:
    dbconn (Connection): Connection to oracle database
    table (String): Name of the table being altered
    pk (String): The primary key of the object for reference.
	operation (String): The operation performed (Add, Update, Delete)

    Returns: None (void)
    */
    private static void audit(Connection dbconn, String table, String pk, String operation) {   	
    	
    	int auditID;
    	try {
    	 auditID = Collections.max(getAuditIDs(dbconn)) + 1;
    	} catch (NoSuchElementException e) {
         auditID = 1; // Empty Table Failsafe
    	}
    	
    	String query = String.format("INSERT INTO dylanchapman.auditLog VALUES (%d,'%s','%s','%s', SYSTIMESTAMP)",
    			auditID, // auditID
    			table, // tableName
    			pk, // identifier (PK)
    			operation // Operation   			
    			);    	   	
    	
    	try {
    		Statement statement = dbconn.createStatement();
            statement.executeUpdate(query);
    	}
    	
    	catch (SQLException e) {
            System.err.println("*** SQLException: Error inserting new Audit Log entry.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
    	
    	
    	
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
                    case 7 -> GetSkiPassUsageDetails(scanner, dbconn);
                    case 8 -> GetOpenIntermediateTrails(dbconn);
                    case 9 -> CustomQuery(scanner, dbconn);
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