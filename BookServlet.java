import java.io.IOException;//Used to handle input/output exceptions.
import java.io.PrintWriter;//Used to send output (HTML response) to the browser.
import java.sql.Connection;//Used to create a connection with the database.
import java.sql.DriverManager;//Used to load the database driver and establish a connection.
import java.sql.PreparedStatement;//Used to execute SQL queries securely (recommended way).
import java.sql.ResultSet;//Used to retrieve data from the database after executing a query.
import jakarta.servlet.http.HttpServlet;//This is the base class for creating a Servlet.
import jakarta.servlet.http.HttpServletRequest;//Used to get data from the client (browser).
import jakarta.servlet.http.HttpServletResponse;//Used to send a response back to the client (browser).
public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)//doGet method used to handle the get request
            throws IOException {
        res.setContentType("text/html");//tells the browser the response is html form/content.
        PrintWriter out = res.getWriter();//it helps you to send output to the browser.
        String key = req.getParameter("key");//it reads search keyword send from the html form input name key.
        try {
      Class.forName("org.postgresql.Driver");
Connection con = DriverManager.getConnection(
    "jdbc:postgresql://aws-1-ap-southeast-2.pooler.supabase.com:5432/postgres?sslmode=require&connectTimeout=10",
    "postgres.heqsaekmpteutghhpein",
    "29072005poulomi"
);
      String sql = "SELECT * FROM public.books WHERE title ILIKE ? OR author ILIKE ?";
           PreparedStatement ps = con.prepareStatement(sql);
                 ps.setString(1, "%" + key + "%");
          ps.setString(2, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            out.println("<h2>Book List</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Title</th><th>Author</th><th>Price</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getInt("price") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e + "</p>");
        }
    }
}
