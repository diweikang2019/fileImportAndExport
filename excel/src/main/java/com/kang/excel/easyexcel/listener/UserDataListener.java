package com.kang.excel.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.kang.excel.easyexcel.bo.User;
import com.kang.excel.easyexcel.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 模板的读取类
 * 有个很重要的点：UserDataListener不能被spring管理，要每次读取excel都要new，然后里面用到bean可以通过构造方法传进去
 */
public class UserDataListener implements ReadListener<User> {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserDataListener.class);

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    /**
     * 缓存的数据
     */
    private List<User> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service
     */
    private UserDao userDao;

    public UserDataListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring，请使用下面的有参构造函数
        userDao = new UserDao();
    }

    /**
     * 如果使用了spring，请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param userDao
     */
    public UserDataListener(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 每一条数据解析都会被调用
     *
     * @param data
     * @param context
     */
    @Override
    public void invoke(User data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成后被调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", cachedDataList.size());
        userDao.save(cachedDataList);
        LOGGER.info("存储数据库成功！");
    }
}
