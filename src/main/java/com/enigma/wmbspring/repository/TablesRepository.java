package com.enigma.wmbspring.repository;

import com.enigma.wmbspring.constant.TableName;
import com.enigma.wmbspring.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TablesRepository extends JpaRepository<Tables, String> {
    Tables findByTableName(TableName tableName);
}
