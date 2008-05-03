package table;

/**
 * Model to hold table data.
 */
public class TableData {
    // An array of Name objects.
    protected static final Name[] names = {
        new Name("William", "Dupont"),
        new Name("Anna", "Keeney"),
        new Name("Mariko", "Randor"),
        new Name("John", "Wilson"),
        new Name("Lynn", "Seckinger"),
        new Name("Richard", "Tattersall"),
        new Name("Gabriella", "Sarintia"),
        new Name("Lisa", "Hartwig"),
        new Name("Shirley", "Jones"),
        new Name("Bill", "Sprague"),
        new Name("Greg", "Doench"),
        new Name("Solange", "Nadeau"),
        new Name("Heather", "McGann"),
        new Name("Roy", "Martin"),
        new Name("Claude", "Loubier"),
        new Name("Dan", "Woodard"),
        new Name("Ron", "Dunlap"),
        new Name("Keith", "Frankart"),
        new Name("Andre", "Nadeau"),
        new Name("Horace", "Celestin")
    };
    
    /**
     * Get table data.
     * 
     * @return An array of Name obejcts.
     */
    public Name[] getNames() {
        return names;
    }
}
