package ua.nure.fomenko.final_project.dao.builder;

import ua.nure.fomenko.final_project.constants.SQLQueries;
import ua.nure.fomenko.final_project.db.entity.dto.FilterParam;

/**
 * Created by fomenko on 26.09.2017.
 */
public class QueryBuilder {
    private FilterParam filterParam;
    private String query;

    public void setFilterParam(FilterParam filterParam) {
        this.filterParam = filterParam;
    }

    public void build() {
        StringBuilder stringBuilder = new StringBuilder(SQLQueries.FACULTY_GET_ALL);
        if(filterParam != null) {
            if(filterParam.getOrderBy() != null && !filterParam.getOrderBy().isEmpty()) {
                String orderBy = filterParam.getOrderBy();
                if(orderBy.equals("name")) {
                    orderBy = "name";
                }
                if(orderBy.equals("stateFunded")) {
                    orderBy = "count_state_funded_place";
                }
                if(orderBy.equals("allPlace")) {
                    orderBy = "count_all_place";
                }
                if(!filterParam.isDesc()) {
                    stringBuilder.append(" ORDER BY ").append(orderBy).append(" ASC");
                } else {
                    stringBuilder.append(" ORDER BY ").append(orderBy).append(" DESC");
                }
            }
        }
        query = stringBuilder.toString();
    }

    @Override
    public String toString() {
        return query;
    }
}
