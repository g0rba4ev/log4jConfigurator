package test.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import java.io.IOException;
import java.util.Collection;

/**
 * Class to build json with suggestions for jQuery Autocomplete
 */
public class SuggestionsBuilder {

    /**
     * find strings in {@code values} that contains substring {@code term} (ignoring case)
     * and return json array as a string
     * @param term expression that should contains into each of returned suggestions
     * @param values collection of string
     * @param numberRequiredSuggestions number of required suggestions (equals size of json array)
     * @return json array with values for jQuery autocomplete widget
     */
    public static String getJson(String term, Collection<String> values, int numberRequiredSuggestions) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode suggestions = mapper.createArrayNode();
        for (String value: values){
            if(suggestions.size() == numberRequiredSuggestions){
                break;
            }
            if ( value.toLowerCase().contains(term.toLowerCase()) ) {
                suggestions.add(value);
            }
        }
        try {
            return mapper.writeValueAsString(suggestions);
        } catch (IOException e) {
            // TODO add logger
            return "something going wrong";
        }
    }

}
