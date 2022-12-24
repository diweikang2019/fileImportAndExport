package com.kang.excel.easyexcel.dao;

import com.kang.excel.easyexcel.bo.User;
import com.kang.excel.easyexcel.listener.UserDataListener;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author weikang.di
 * @date 2022/12/18 9:13 PM
 */
@Repository
public class UserDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserDataListener.class);

    public void save(List<User> userList) {
        if (CollectionUtils.isNotEmpty(userList)) {
            LOGGER.info("DB存储条{}数据", userList.size());
        }
    }
}
