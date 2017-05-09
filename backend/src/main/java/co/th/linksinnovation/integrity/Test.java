/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.integrity;

import co.th.linksinnovation.integrity.model.OrganizeData;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public class Test {

//    public static void main(String[] args) {
//        BeanListProcessor<OrganizeData> rowProcessor = new BeanListProcessor<OrganizeData>(OrganizeData.class);
//
//        CsvParserSettings parserSettings = new CsvParserSettings();
//        parserSettings.setRowProcessor(rowProcessor);
//        parserSettings.setHeaderExtractionEnabled(true);
//
//        CsvParser parser = new CsvParser(parserSettings);
//        parser.parse(new File("/Users/Kong/Desktop/Personal_DATA_v2.csv"));
//
//        // The BeanListProcessor provides a list of objects extracted from the input.
//        List<OrganizeData> beans = rowProcessor.getBeans();
//        
//        System.out.println(beans.get(7));
//    }
}
