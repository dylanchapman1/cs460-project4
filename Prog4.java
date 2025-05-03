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
    
    public static ArrayList<Integer> getOrderIDs(Connection dbconn) {
        ArrayList<Integer> IDs = new ArrayList<>();
        String query = "SELECT OrderID FROM dylanchapman.LESSONPURCHASE";

        try {
            Statement statement = dbconn.createStatement();
            ResultSet answer = statement.executeQuery(query);

            if (answer != null) {
                while (answer.next())
                    IDs.add(answer.getInt("ORDERID"));
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
                "UPDATE dylanchapman.Member SET PHONENO = %d, EMAIL = '%s', EMERGENCYCONTACTNUMBER = %d WHERE MEMBERID = %d",
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
                "INSERT INTO dylanchapman.SkiPass VALUES(%d, %d, %d, %d, TO_DATE('2024-12-31', 'YYYY-MM-DD'), %d)",
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
            System.out.println("Equipment added!\n");
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }


    }

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
    
        String updateQuery = "UPDATE dylanchapman.EQUIPMENT SET EQUIPMENTTYPE = ?, EQUIPMENTSIZE = ? WHERE ITEMID = ?";
    
        try(PreparedStatement prep = dbconn.prepareStatement(updateQuery)) {
            prep.setString(1, inputToEquipment.get(equipmentType));
            prep.setString(2, equipmentSize.toUpperCase());
            prep.setInt(3, itemID);
    
            int count = prep.executeUpdate();
            if(count > 0) {
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
            System.out.printf("Rental added successfully! Your rental ID is %d\n\n", currentID);
        }
        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        
    }
    
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
                System.out.println("Prepared statement created successfully, uploading");
                int count = prep.executeUpdate();
                if(count > 0) {
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
    
        try(Statement stmt = dbconn.createStatement();
             ResultSet rs = stmt.executeQuery(usageCheckQuery)) {
    
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

    public static void addLessonPurchase(Scanner scanner, Connection dbconn) {
    	String query;
        System.out.println("""
                        Please add all necessary fields, and SEPARATE THEM WITH COMMAS
                        <LessonID (int)>, <MemberID> (int), <PurchaseDate (YYYY-MM-DD)>, <RemainingUses(int)>
                        """);
        //TotalUses will be set to 0 by default.

        String input  = scanner.nextLine().trim();
        String[] attributes = input.split(",");
        
        int currentID = Collections.max(getOrderIDs(dbconn)) + 1;
        int temp = 0;
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
            System.out.printf("LessonPurchase ID %d has successfully been registered. \n", currentID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
        

    }

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
            System.out.printf("Lesson Purchase Order ID %d has been succesfully updated\n\n", orderID);
        }

        catch (SQLException e) {
            System.err.println("*** SQLException: Could not fetch query results.");
            System.err.println("\tMessage:   " + e.getMessage());
            System.err.println("\tSQLState:  " + e.getSQLState());
            System.err.println("\tErrorCode: " + e.getErrorCode());
        }
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

    public static void GetSkiPassUsageDetails(Scanner scanner, Connection dbconn) {
        System.out.println("Enter a passID (Your ID for your Ski Pass:");
        int passID = scanner.nextInt();
        scanner.nextLine(); // I'm pretty sure we need this

        if (!getPassIDs(dbconn).contains(passID)) {
            System.out.println("Ski Pass ID does not exist!\n");
            return;
        }

        String query1 = String.format("SELECT passID, liftName, time FROM dylanchapman.LiftUsage WHERE passID = %d", passID);
        String query2 = String.format("SELECT rentalID, itemID, rentalDate, returnStatus FROM dylanchapman.EquipmentRental WHERE passID = %d", passID);

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
                    System.out.println("\t" + answer2.getObject("rentalID") + "\t\t" + answer2.getObject("itemID") + "\t" + answer2.getString("rentalDate").split(" ")[0] + "\t" + answer2.getObject("returnStatus"));

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
                    case 7 -> GetSkiPassUsageDetails(scanner, dbconn);
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
