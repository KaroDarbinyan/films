package am.films.api.factory;

import am.films.api.model.Person;
import am.films.api.model.enums.Sex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;

import javax.persistence.Column;
import java.util.Date;

public class PersonFactory {

    public static Person createPerson(String json) throws JsonProcessingException {
        Person person = new Person();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
//        private int kPId;
        person.setKPId(jsonNode.get("personId").intValue());

//        private String webUrl;
        person.setWebUrl(jsonNode.get("webUrl").textValue());

//        private String nameRu;
        person.setNameRu(jsonNode.get("nameRu").textValue());

//        private String nameEn;
        person.setNameEn(jsonNode.get("nameEn").textValue());

//        private String sex;
        person.setSex(Sex.valueOf(jsonNode.get("sex").textValue()));

//        private String posterUrl;
        person.setPosterUrl(jsonNode.get("posterUrl").textValue());

//        private int growth;
        person.setGrowth(jsonNode.get("growth").intValue());

//        private Date birthday;
        person.setBirthday(new Date());

//        private String death;
        person.setDeath(jsonNode.get("death").textValue());

//        private int age;
        person.setAge(jsonNode.get("age").intValue());

//        private String birthPlace;
        person.setDeathPlace(jsonNode.get("deathplace").textValue());

//        private String deathPlace;
        person.setDeathPlace(jsonNode.get("deathplace").textValue());

//        private int hasAwards;
        person.setHasAwards(jsonNode.get("hasAwards").intValue());

        return person;
    }
}
