package com.example.csv.supercsv.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author weikang.di
 * @date 2022/12/15 11:40 AM
 */
public class CsvUtils {

    public static <T> List<T> readCsv(InputStream inputStream, String[] nameMapping, CellProcessor[] processors, Class<T> clazz) {
        List<T> dataList = Lists.newArrayList();
        Reader reader = null;
        ICsvBeanReader beanReader = null;

        try {
            reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE);

            String[] header = beanReader.getHeader(true);

            T object;
            while ((object = beanReader.read(clazz, nameMapping, processors)) != null) {
                dataList.add(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (beanReader != null) {
                try {
                    beanReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return dataList;
    }

    public static void writeCsv(OutputStream outputStream, String[] header, String[] nameMapping, CellProcessor[] processors, List<?> dataList) {
        Writer writer = null;
        ICsvBeanWriter beanWriter = null;

        try {
            writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            // 写入字节流，让文档以UTF-8编码
            writer.write('\uFEFF');
            beanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

            beanWriter.writeHeader(header);

            if (CollectionUtils.isNotEmpty(dataList)) {
                for (Object data : dataList) {
                    beanWriter.write(data, nameMapping, processors);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void exportCsv(HttpServletResponse response, String fileName, String[] header, String[] nameMapping, CellProcessor[] processors, List<?> dataList) {

        try {
            // 设置文件后缀
            fileName = URLEncoder.encode(fileName + ".csv", StandardCharsets.UTF_8.name());

            // 设置响应
            response.setContentType("application/ms-txt.numberformat:@");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=30");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // 写入数据
            writeCsv(response.getOutputStream(), header, nameMapping, processors, dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
