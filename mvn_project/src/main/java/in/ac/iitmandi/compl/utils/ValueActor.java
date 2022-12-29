package in.ac.iitmandi.compl.utils;

import java.io.Serializable;
import com.google.gson.GsonBuilder;

public primitive class ValueActor implements Serializable {

    public final int birth;

    public final int death;

    public final byte[] name;

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(toActor());
    }

    /**
     * Manually modified to confine to JEP spec
     */
    public ValueActor(Integer birth, Integer death, String name) {
        this.birth = birth;
        this.death = death;
        this.name = name.getBytes();
    }

    public Actor toActor() {
        Actor actor = new Actor();
        actor.birth = this.birth;
        actor.death = this.death;
        actor.name = new String(name);
        return actor;
    }
}
