package com.wf.aibi.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Excel 相关工具类
 *
 * @author AntonyCheng
 * @since 2023/9/30 16:09:29
 */
@Slf4j
public class ExcelUtils {
    public static String excelToCsv(MultipartFile multipartFile) {
        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("文件异常");
            e.printStackTrace();
        }
        if (CollUtil.isEmpty(list)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap<Integer, String>) list.get(0);
        List<String> headerList = headerMap.values().stream().filter(Objects::nonNull).collect(Collectors.toList());
        sb.append(StringUtils.join(headerList, ",")).append("\n");
        for (int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap<Integer, String>) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(Objects::nonNull).collect(Collectors.toList());
            sb.append(StringUtils.join(dataList, ",")).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        excelToCsv(null);
    }
}
