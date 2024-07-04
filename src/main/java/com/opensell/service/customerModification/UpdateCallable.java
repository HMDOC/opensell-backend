package com.opensell.service.customerModification;

import java.sql.SQLException;

public interface UpdateCallable {
    //updateQuery() abstract method doesn't return an SQLException by default, so we need to add it
    int updateStatement() throws Exception, SQLException;
}
