package com.enigma.wmbspring.service.impl;

import com.enigma.wmbspring.constant.TableName;
import com.enigma.wmbspring.entity.Tables;
import com.enigma.wmbspring.repository.TablesRepository;
import com.enigma.wmbspring.service.TablesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TablesServiceImpl implements TablesService {
    private final TablesRepository tablesRepository;

    @Override
    public Tables getByName(String tableName) {
        return tablesRepository.findByTableName(TableName.valueOf(tableName));

    }
}
