package com.kang.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kang.excel.easyexcel.bo.User;
import com.kang.excel.easyexcel.listener.UserDataListener;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

class EasyExcelTests {

    public static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelTests.class);

    private List<User> data() {
        List<User> dataList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setName("姓名" + i);
            user.setSex(1);
            user.setAge(20 + i);
            user.setBirthday(new Date());
            user.setScore(0.56);
            user.setIgnore("ignore");
            dataList.add(user);
        }
        return dataList;
    }

    @Test
    public void testWriteExcel() throws IOException {
        OutputStream outputStream = new FileOutputStream("/Users/diweikang/Documents/user.xlsx");

        EasyExcel.write(outputStream, User.class).sheet("成绩单").doWrite(data());
    }

    @Test
    public void testWriteExcel2() throws IOException {
        OutputStream outputStream = new FileOutputStream("/Users/diweikang/Documents/user2.xlsx");

        try (ExcelWriter excelWriter = EasyExcel.write(outputStream, User.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("成绩单").build();
            excelWriter.write(data(), writeSheet);
        }
    }

    @Test
    public void testRepeatedWriteExcel() throws IOException {
        OutputStream outputStream = new FileOutputStream("/Users/diweikang/Documents/repeatedUser.xlsx");

        try (ExcelWriter excelWriter = EasyExcel.write(outputStream, User.class).build()) {
            // 这里注意,如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("成绩单").build();
            // 去调用写入,这里我调用了五次,实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据,这里可以去数据库查询每一页的数据
                List<User> data = data();
                excelWriter.write(data, writeSheet);
            }
        }
    }

    @Test
    public void testRead() {
        // 使用自带的监听器，每次会读取100条数据，然后返回过来，直接调用使用数据就行
        EasyExcel.read("/Users/diweikang/Documents/user.xlsx", User.class, new PageReadListener<User>(dataList -> {
            for (User user : dataList) {
                LOGGER.info("读取到一条数据:{}", JSON.toJSONString(user));
            }
        })).sheet().doRead();
    }

    @Test
    public void testRead2() {
        // 使用自定义监听器
        EasyExcel.read("/Users/diweikang/Documents/user.xlsx", User.class, new UserDataListener()).sheet().doRead();
    }
}
