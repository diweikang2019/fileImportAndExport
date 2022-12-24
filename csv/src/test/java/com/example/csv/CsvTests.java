package com.example.csv;

import com.example.csv.bo.User;
import com.example.csv.supercsv.util.CsvUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.ift.CellProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

class CsvTests {

    @Test
    public void testWriteCsv() throws FileNotFoundException {
        User user1 = new User(1, "张三", 1, 25, new Date(), null);
        User user2 = new User(2, "李四", 2, 28, new Date(), "北京昌平");

        OutputStream outputStream = new FileOutputStream("/Users/diweikang/Documents/user.csv");
        String[] header = {"ID", "姓名", "性别", "年龄", "出生日期", "住址"};
        String[] nameMapping = {"id", "name", "sex", "age", "birthday", "address"};
        CellProcessor[] processors = new CellProcessor[]{
                new ParseInt(),
                new Trim(),
                new ParseInt(),
                new ParseInt(),
                new FmtDate("yyyy-MM-dd"),
                new ConvertNullTo(""),
        };
        List<User> dataList = Lists.newArrayList(user1, user2);
        CsvUtils.writeCsv(outputStream, header, nameMapping, processors, dataList);
    }

    @Test
    public void testReadCsv() throws FileNotFoundException, JsonProcessingException {
        InputStream inputStream = new FileInputStream("/Users/diweikang/Documents/user.csv");
        String[] nameMapping = {"id", "name", "sex", "age", "birthday", "address"};
        CellProcessor[] processors = new CellProcessor[]{
                new ParseInt(),
                new Trim(),
                new ParseInt(),
                new ParseInt(),
                new ParseDate("yyyy-MM-dd"),
                new ConvertNullTo(""),
        };
        List<User> userList = CsvUtils.readCsv(inputStream, nameMapping, processors, User.class);
        System.out.println(new JsonMapper().writeValueAsString(userList));
    }
}
