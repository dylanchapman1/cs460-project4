/*
 * Author(s): Dylan Chapman
 * Class:     CSC460
 * Date:      5/6/2025
 * Program:   Prog4.java
 */

import java.sql.*;
import java.util.*;

public class Prog4 {
    public static void main(String[] args) {
            // validate cl inputs
            if(args.length != 2) {
                    System.out.println("use java Prog3 <username> <password>");
                    System.exit(-1);
            }
            String username = args[0];
            String password = args[1];
            String oracle = "jdbc:oracle:thin:@aloe.cs.arizona.edu:1521:oracle";
            // get states for input validation

            try {
                    // get oracle connection
                    Class.forName("oracle.jdbc.OracleDriver");
                    Connection conn = DriverManager.getConnection(oracle, username, password);
                    Scanner scanner = new Scanner(System.in);

                    // display menu while not quit
                    while(true) {
                            int choice = menu(scanner);

                            // query 1 menu
                            if(choice == 1) {
                                    System.out.println("enter a year (1990, 2000, 2010, or 2020): ");
                                    int year = scanner.nextInt();
                                    scanner.nextLine();

                                    System.out.println("enter a category (auto, bus, truck, or motorcycle): ");
                                    String cat = scanner.nextLine().trim().toLowerCase();
                                    System.out.println();
                                    query1(conn, year, cat);
                            }
                            
                            if(choice == 10) {
                                    System.out.println("goodbye!");
                                    conn.close();
                                    System.exit(-1);
                            }
                    }
            }
            catch(ClassNotFoundException e) {
                    System.out.println("oracle error");
                    System.exit(-1);
            }
            catch(SQLException e) {
                    System.out.println("error executing sql");
                    System.exit(-1);
            }
    }

    public static int menu(Scanner scanner) {
            // Display command menu, take and validat euser input to ensure
            // process commands
            System.out.println("select a query");
            System.out.println("1. Add, Update or Delete a Member");
            System.out.println("2. Add Update, or Delete a Ski Pass");
            System.out.println("3. Add, Update, or Delete an Equipment Inventory Record");
            System.out.println("4. Add, Update, or Delete an Equipment Rental Record");
            System.out.println("5. Add, Update, or Delete a LEsson Purchase Record");
            System.out.println("6. Query 1");
            System.out.println("7. Query 2");
            System.out.println("8. Query 3");
            System.out.println("9. Query 4");
            System.out.println("10. Quit");

            System.out.println("input your choice:");
            while(!scanner.hasNextInt()) {
                    System.out.println("you must enter a number 1-10");
                    scanner.next();
            }
            return scanner.nextInt();
    }

    public static void query1(Connection conn, int year, String cat) {
            // EXAMPLE QUERY THAT I LEFT FROM MY PROG3 FILE
            // sql query string
            String sql = "select state, " + cat +
                         " from (select state, " + cat + " from dylanchapman.MVRD" + year +
                         " order by " + cat + " desc) where rownum <= 10";

            try {
                    // prepare query
                    Statement stmt = conn.createStatement();
                    ResultSet answer = stmt.executeQuery(sql);

                    // display results
                    System.out.println("query1 results for " + cat + "'s in " + year + ": ");
                    System.out.println("---------------------------------");
                    System.out.printf(" %-20s %-10s\n", "state", "year");
                    System.out.println("---------------------------------");

                    // while has andwer rows
                    while(answer.next()) {
                            String state = answer.getString("state");
                            int quantity = answer.getInt(cat);

                            // format results
                            System.out.printf(" %-20s %-10d\n", state, quantity);
                    }
                    System.out.println();
            }
            catch(SQLException e) {
                    System.out.println("error executing sql");
            }


    }
}