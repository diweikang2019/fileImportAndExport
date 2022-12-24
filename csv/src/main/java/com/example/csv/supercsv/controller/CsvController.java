package com.example.csv.supercsv.controller;

import com.example.csv.bo.User;
import com.example.csv.supercsv.util.CsvUtils;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.ift.CellProcessor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author weikang.di
 * @date 2022/12/15 10:11 PM
 */
@RestController()
@RequestMapping("csv")
public class CsvController {

    @PostMapping("import")
    public Object importCsv(@RequestParam(value = "file") MultipartFile file) throws IOException {
        String[] nameMapping = {"id", "name", "sex", "age", "birthday", "address"};
        CellProcessor[] processors = new CellProcessor[]{
                new ParseInt(),
                new Trim(),
                new ParseInt(),
                new ParseInt(),
                new ParseDate("yyyy-MM-dd"),
                new ConvertNullTo(""),
        };
        return CsvUtils.readCsv(file.getInputStream(), nameMapping, processors, User.class);
    }

    @GetMapping("export")
    public void exportCsv(HttpServletResponse response) {
        User user1 = new User(1, "张三", 1, 25, new Date(), null);
        User user2 = new User(2, "李四", 2, 28, new Date(), "北京昌平");

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
        CsvUtils.exportCsv(response, "用户信息", header, nameMapping, processors, dataList);
    }
}
