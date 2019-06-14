package com.unionpay.financial.core.dao;

import com.unionpay.financial.model.AutoRequireConfigHis;
import java.util.List;

public interface AutoRequireConfigHisDao {
    int deleteByPrimaryKey(String id);

    int insert(AutoRequireConfigHis record);

    AutoRequireConfigHis selectByPrimaryKey(String id);

    List<AutoRequireConfigHis> selectAll();

    int updateByPrimaryKey(AutoRequireConfigHis record);
}