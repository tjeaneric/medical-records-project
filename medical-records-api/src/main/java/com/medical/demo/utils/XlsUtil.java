package com.medical.demo.utils;

import com.medical.demo.models.Role;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XlsUtil {

    Sheet sheet;

    public XlsUtil(Role role) throws IOException {
        FileInputStream file = new FileInputStream(new File("/Users/Victor/Projects/medical-records-api/src/main/java/com/medical/demo/utils/Medical-Data.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);

        switch (role) {
            case PATIENT -> sheet = workbook.getSheetAt(0);
            case PHYSICIAN -> sheet = workbook.getSheetAt(1);
            case PHARMACIST -> sheet = workbook.getSheetAt(2);
        }

    }


    public List<HashMap<String, Object>> getData() {
        List<HashMap<String, Object>> data = new ArrayList<>();
        List<Double> years = new ArrayList<>();
        List<List<String>> dt = new ArrayList<>();

        for (Row row : sheet) {
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case NUMERIC: {
                        years.add(cell.getNumericCellValue());
                        dt.add(new ArrayList<>());
                    }
                }
            }
        }


        for (Row row : sheet) {
            int i = 0;

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING: {
                        dt.get(i).add(cell.getStringCellValue());
                        i++;
                    }
                }
            }
        }


        for (int i = 0; i < years.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("year", years.get(i));
            map.put("data", dt.get(i));

            data.add(map);
        }

        return data;
    }
}
