/**
 * 
 */
package com.zhou.test.dataSource.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhou.test.dataSource.dao.domain.Depart;
import com.zhou.test.dataSource.dao.mappers.DepartMapper;
import com.zhou.test.dataSource.service.DepartMentService;

/**
 * @author LUCKY
 *
 */
@Service
public class DepartMentServiceImpl implements DepartMentService {

    @Autowired
    private DepartMapper departMapper;

    public int inertDepart(Depart depart) {
        return departMapper.insertDepart(depart);
    }

}
