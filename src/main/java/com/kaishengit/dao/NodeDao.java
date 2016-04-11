package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NodeDao {

    public List<Node> findAll() {
        String sql = "select * from t_node";
        return DBHelp.query(sql,new BeanListHandler<>(Node.class));
    }

}
