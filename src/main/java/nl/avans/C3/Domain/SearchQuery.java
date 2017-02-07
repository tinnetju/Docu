package nl.avans.C3.Domain;

/**
 *
 * @author Tinne
 */
public class SearchQuery {
    private String searchWords;
    private String searchOption;
    
    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String SearchWords) {
        this.searchWords = SearchWords;
    }

    public String getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(String SearchType) {
        this.searchOption = SearchType;
    }
}
