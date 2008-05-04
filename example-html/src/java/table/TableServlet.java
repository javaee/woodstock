package table;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet to retrieve table data.
 */
public class TableServlet extends HttpServlet {
    private TableData data;
    private int totalRows;
    
    public TableServlet() {
        super();
        data = new TableData();
        totalRows = data.getNames().length;
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {
            JSONObject json = null;
            String event = request.getParameter("event");
            if (event.equals("scroll")) {
                json = processScrollEvent(request);
            } else if (event.equals("filter")) {
                json = processFilterEvent(request);
            }
            if (json != null) {
                json.write(out);
            }
        } catch (JSONException e) {
            // Log an error
        } finally {
            out.close();
        }
    }

    /**
     * Get column data.
     * 
     * @param id The widget id
     * @param row The current table data row.
     */
    protected JSONArray getColumns(String id, int row)
            throws JSONException {
        Name[] names = data.getNames();

        JSONObject lastName = new JSONObject();
        lastName.put("id", id + "-" + row + "-col0")
            .put("value", names[row].getLast())
            .put("widgetType", "staticText");

        JSONObject firstName = new JSONObject();
        firstName.put("id", id + "-" + row + "-col1")
            .put("value", names[row].getFirst())
            .put("widgetType", "staticText");

        JSONArray json = new JSONArray();
        json.put(lastName);
        json.put(firstName);

        return json;
    }

    /** 
     * Process filter events.
     * 
     * @param request servlet request
     */
    protected JSONObject processFilterEvent(HttpServletRequest request) 
            throws JSONException {
        Name[] names = data.getNames();
        String id = request.getParameter("id");
        String execute = request.getParameter("execute");
        String filter = null;
        if (execute != null) {
            String[] params = execute.split(",");
            filter = request.getParameter(params[0]);
        }

        JSONArray rows = new JSONArray();
        for (int i = 0; i < 10 && i < totalRows; i++) {
            if (filter != null && names[i].getLast().indexOf(filter) != -1) {
                rows.put(getColumns(id, i));
            }
        }
            
        JSONObject json = new JSONObject();
        json.put("id", id)
            .put("rows", rows);

        return json;
    }

    /** 
     * Process scroll events.
     * 
     * @param request servlet request
     */
    protected JSONObject processScrollEvent(HttpServletRequest request)
            throws JSONException {
        String id = request.getParameter("id");
        int first = new Integer(request.getParameter("first")).intValue();
        int maxRows = first + 10;

        JSONArray rows = new JSONArray();
        for (int i = first; i < maxRows && i < totalRows; i++) {
            rows.put(getColumns(id, i));
        }
        
        JSONObject json = new JSONObject();
        json.put("id", id)
            .put("rows", rows)
            .put("totalRows", totalRows);

        return json;
    }
}
