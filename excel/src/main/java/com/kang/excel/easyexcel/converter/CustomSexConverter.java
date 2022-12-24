package com.kang.excel.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * String and string converter
 *
 * @author Jiaju Zhuang
 */
public class CustomSexConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里是读的时候会调用
     *
     * @return
     */
    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) {
        if (context.getReadCellData().getStringValue().equals("男")) {
            return 1;
        } else if (context.getReadCellData().getStringValue().equals("女")) {
            return 2;
        }
        return 0;
    }

    /**
     * 写的时候调用
     *
     * @param context
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        return new WriteCellData<>(context.getValue() == 1 ? "男" : "女");
    }

}
