package in.ac.iitmandi.compl.utils;

import java.io.Serializable;
import com.google.gson.GsonBuilder;

public class Actor implements Serializable {

    // TODO - move to date!
    public Integer birth;

    // TODO - move to date!
    public Integer death;

    public String name;

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

    /**
     * Manually modified to confine to JEP spec
     */
    public ValueActor toValueActor() {
        ValueActor actor = new ValueActor(birth, death, name);
        return actor;
    }
}
