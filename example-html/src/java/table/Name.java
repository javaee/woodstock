package table;

public class Name {
    private String last = null; // Last name.
    private String first = null; // First name.
    
    // Default constructor.
    public Name(String first, String last) {
        this.last = last;
        this.first = first;
    }

    // Get first name.
    public String getFirst() {
        return first;
    }

    // Set first name.
    public void setFirst(String first) {
        this.first = first;
    }

    // Get last name.
    public String getLast() {
        return last;
    }

    // Set last name.
    public void setLast(String last) {
        this.last = last;
    }
}
