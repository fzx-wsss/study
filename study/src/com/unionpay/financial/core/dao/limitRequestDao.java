package com.unionpay.financial.core.dao;

import com.unionpay.financial.model.limitRequest;
import java.util.List;

public interface limitRequestDao {
    int deleteByPrimaryKey(String id);

    int insert(limitRequest record);

    limitRequest selectByPrimaryKey(String id);

    List<limitRequest> selectAll();

    int updateByPrimaryKey(limitRequest record);
}