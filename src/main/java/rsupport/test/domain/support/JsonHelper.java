package rsupport.test.domain.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    public static String prettyPrintJsonUsingDefaultPrettyPrinter(String uglyJsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object jsonObject = objectMapper.readValue(uglyJsonString, Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

    }
}
