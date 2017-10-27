package ua.nure.fomenko.final_project.db.entity;

/**
 * Created by fomenko on 23.09.2017.
 */
public enum Status {
    APPROVED("Approved"),
    ENROLLED("Enrolled");

    private String value;

    Status(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status getStatus(String value) {
        for(Status status : Status.values()) {
            if(status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
