package com.enigma.wmbspring.service;

import com.enigma.wmbspring.constant.TableName;
import com.enigma.wmbspring.entity.Tables;

public interface TablesService {
    Tables getByName(String tableName);
}
