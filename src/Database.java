import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {

    private static final String SERVER_URL =
            "jdbc:mysql://localhost:3306/";

    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/sitesphere";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "Hitesh@29";


    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                DATABASE_URL, USERNAME, PASSWORD
        );
    }


    public static void initializeDatabase()
            throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (
            Connection con = DriverManager.getConnection(
                    SERVER_URL, USERNAME, PASSWORD
            );
            Statement stmt = con.createStatement()
        ) {

            stmt.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS sitesphere"
            );

            System.out.println("Database checked successfully.");
        }


        try (Connection con = getConnection()) {

            createTables(con);

            System.out.println("All tables created successfully.");
            System.out.println("SiteSphere database is ready!");
        }
    }


    private static void createTables(Connection con)
            throws SQLException {

        String[] queries = {

            // 1. PREMISES TABLE
            """
            CREATE TABLE IF NOT EXISTS premises (
                premises_id INT AUTO_INCREMENT PRIMARY KEY,
                premises_name VARCHAR(100) NOT NULL,
                address VARCHAR(255) NOT NULL,
                category VARCHAR(50) NOT NULL
            )
            """,

            // 2. USERS TABLE
            """
            CREATE TABLE IF NOT EXISTS users (
                user_id INT AUTO_INCREMENT PRIMARY KEY,
                premises_id INT NOT NULL,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(20) NOT NULL,

                UNIQUE (premises_id, email),

                FOREIGN KEY (premises_id)
                REFERENCES premises(premises_id)
                ON DELETE CASCADE
            )
            """,

            // 3. ROOMS TABLE
            """
            CREATE TABLE IF NOT EXISTS rooms (
                room_id INT AUTO_INCREMENT PRIMARY KEY,
                premises_id INT NOT NULL,
                room_number VARCHAR(20) NOT NULL,
                room_type VARCHAR(50) NOT NULL,
                capacity INT NOT NULL,

                UNIQUE (premises_id, room_number),

                FOREIGN KEY (premises_id)
                REFERENCES premises(premises_id)
                ON DELETE CASCADE
            )
            """,

            // 4. STAFF PROFILES TABLE
            """
            CREATE TABLE IF NOT EXISTS staff_profiles (
                user_id INT PRIMARY KEY,
                department VARCHAR(100) NOT NULL,
                experience_years INT DEFAULT 0,
                availability VARCHAR(20) DEFAULT 'Available',
                average_rating DOUBLE DEFAULT 0.0,
                total_reviews INT DEFAULT 0,

                FOREIGN KEY (user_id)
                REFERENCES users(user_id)
                ON DELETE CASCADE
            )
            """,

            // 5. COMPLAINTS TABLE
            """
            CREATE TABLE IF NOT EXISTS complaints (
                complaint_id INT AUTO_INCREMENT PRIMARY KEY,
                premises_id INT NOT NULL,
                room_id INT NOT NULL,
                occupant_id INT NOT NULL,
                assigned_staff_id INT DEFAULT NULL,

                category VARCHAR(100) NOT NULL,
                description TEXT NOT NULL,
                priority VARCHAR(20) DEFAULT 'Medium',
                status VARCHAR(30) DEFAULT 'Pending',

                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    ON UPDATE CURRENT_TIMESTAMP,

                FOREIGN KEY (premises_id)
                REFERENCES premises(premises_id),

                FOREIGN KEY (room_id)
                REFERENCES rooms(room_id),

                FOREIGN KEY (occupant_id)
                REFERENCES users(user_id),

                FOREIGN KEY (assigned_staff_id)
                REFERENCES users(user_id)
            )
            """,

            // 6. REVIEWS TABLE
            """
            CREATE TABLE IF NOT EXISTS reviews (
                review_id INT AUTO_INCREMENT PRIMARY KEY,
                complaint_id INT NOT NULL,
                occupant_id INT NOT NULL,
                staff_id INT NOT NULL,
                rating DOUBLE NOT NULL,
                review_text TEXT,

                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                UNIQUE (complaint_id),

                FOREIGN KEY (complaint_id)
                REFERENCES complaints(complaint_id),

                FOREIGN KEY (occupant_id)
                REFERENCES users(user_id),

                FOREIGN KEY (staff_id)
                REFERENCES users(user_id)
            )
            """,

            // 7. TIPS TABLE
            """
            CREATE TABLE IF NOT EXISTS tips (
                tip_id INT AUTO_INCREMENT PRIMARY KEY,
                complaint_id INT NOT NULL,
                occupant_id INT NOT NULL,
                staff_id INT NOT NULL,
                amount DECIMAL(10,2) NOT NULL,

                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                FOREIGN KEY (complaint_id)
                REFERENCES complaints(complaint_id),

                FOREIGN KEY (occupant_id)
                REFERENCES users(user_id),

                FOREIGN KEY (staff_id)
                REFERENCES users(user_id)
            )
            """
        };


        // Execute table creation queries
        try (Statement stmt = con.createStatement()) {

            for (String query : queries) {
                stmt.executeUpdate(query);
            }
        }
    }
}