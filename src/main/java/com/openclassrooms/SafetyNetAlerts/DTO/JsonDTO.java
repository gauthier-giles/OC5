package com.openclassrooms.SafetyNetAlerts.DTO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Repository
public class JsonDTO {

    private static Logger logger = LoggerFactory.getLogger(JsonDTO.class);

    private static JsonDTO jsonDTOInstance;
    private static String pathToFile = "data.json";
    private JSONObject jsonObject;


    @Bean
    public static JsonDTO getInstance(){
        if(JsonDTO.jsonDTOInstance== null) {
            jsonDTOInstance = new JsonDTO();
        }
        return jsonDTOInstance;
    }

    private JsonDTO(){
        try{
            FileReader reader = new FileReader(pathToFile);
            JSONParser jsonParser = new JSONParser();
            jsonObject  = (JSONObject) jsonParser.parse(reader);
            logger.info("data.json parsed with success");
        } catch (FileNotFoundException e) {
            logger.error("data.json not found", e);
        } catch (ParseException e) {
            logger.error("parse data.json is impossible", e);
        } catch (IOException e) {
            logger.error("fail to load data.json", e);
        }
    }

}
