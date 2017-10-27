package ua.nure.fomenko.final_project.db.entity;

/**
 * Created by fomenko on 08.09.2017.
 */
public class Subject extends Entity {
    private String name;


    public Subject() {
    }

    public Subject(int id, String name, int mark) {
        this.id = id;
        this.name = name;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subject{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
