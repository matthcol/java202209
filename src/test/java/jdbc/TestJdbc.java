package jdbc;

import data.Pilot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TestJdbc {

    static DataSource ds;

    @BeforeAll
    static void initDataSourcePg() {
        // TODO: in real app / test, configured outside code !!!!!!
        var dsLocal = new org.postgresql.ds.PGSimpleDataSource();
        dsLocal.setURL("jdbc:postgresql://localhost:5432/dbpilot");
        dsLocal.setUser("pilot");
        dsLocal.setPassword("password");
        ds = dsLocal;
    }

    @Test
    void testInsert() throws SQLException {
        String sql = "INSERT INTO pilot (firstname, lastname, agility) VALUES (?, ?, ?)";
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            // set parameters
            ps.setString(1,"Buck");
            ps.setString(2,"Dany");
            ps.setDouble(3, 5.5);
            ps.executeUpdate();
        } // finally, try conn.close() and ps.close()

    }

    @Test
    void testRead() throws  SQLException {
        String sql = "select firstname, lastname, birthdate, agility from pilot where lastname = ?";
        List<Pilot> pilots = new ArrayList<>();
        try (
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            // set params
            ps.setString(1, "Dany");

            // execute query
            try (ResultSet cursor = ps.executeQuery()) {
                while (cursor.next()) {
                    String firstname = cursor.getString(1);
                    String lastname = cursor.getString("lastname");
                    LocalDate birthdate = Optional.ofNullable(cursor.getDate("birthdate"))
                            .map(d ->  d.toLocalDate())
                            .orElse(null);

                    double agilityPrimitive = cursor.getDouble("agility");
                    Double agilityObject = (cursor.wasNull()) ? null : agilityPrimitive;
                    Pilot pilot = new Pilot(); // Meta : newInstance
                    pilot.setFirstname(firstname);
                    pilot.setLastname(lastname);
                    pilot.setBirthdate(birthdate);
                    pilot.setAgility(agilityObject);
                    pilots.add(pilot);
                }
            };
        }
        System.out.println(pilots);
    }
}
