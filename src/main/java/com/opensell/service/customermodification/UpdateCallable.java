package com.opensell.service.customermodification;

import java.sql.SQLException;

@Deprecated(forRemoval = true)
public interface UpdateCallable {
    //updateQuery() abstract method doesn't return an SQLException by default, so we need to add it
    int updateStatement() throws Exception, SQLException;
}
