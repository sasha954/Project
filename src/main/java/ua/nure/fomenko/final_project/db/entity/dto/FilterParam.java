package ua.nure.fomenko.final_project.db.entity.dto;


public class FilterParam {
    private String orderBy;
    private boolean desc;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FilterParam{");
        sb.append("orderBy='").append(orderBy).append('\'');
        sb.append(", desc=").append(desc);
        sb.append('}');
        return sb.toString();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
