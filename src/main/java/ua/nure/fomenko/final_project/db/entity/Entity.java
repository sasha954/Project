package ua.nure.fomenko.final_project.db.entity;

import java.io.Serializable;

/**
 * Created by fomenko on 25.08.2017.
 */
public abstract class Entity implements Serializable {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
