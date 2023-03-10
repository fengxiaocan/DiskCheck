package com.n.disk.common;

import java.io.File;

/**
 * 常量字符集合
 * @author wind
 */
public interface Constants {

    /**********************************************分隔符常量************************************************/

    String POINT_STR = ".";

    String BLANK_STR = "";

    String SPACE_STR = " ";

    String NEWLINE_STR = "\n";

    String SYS_SEPARATOR = File.separator;

    String FILE_SEPARATOR = "/";

    String BRACKET_LEFT = "[";

    String BRACKET_RIGHT = "]";

    String UNDERLINE = "_";

    String MINUS_STR = "-";



    /**********************************************编码格式************************************************/

    String UTF8 = "UTF-8";


    /**********************************************文件后缀************************************************/

    String EXCEL_XLS = ".xls";

    String EXCEL_XLSX = ".xlsx";

    String IMAGE_PNG = "png";

    String IMAGE_JPG = "jpg";

    String FILE_ZIP = ".zip";
    String FILE_GZ = ".gz";


    /**********************************************io流************************************************/

    int BUFFER_1024 = 1024;

    int BUFFER_512 = 512;

    String USER_DIR = "user.dir";
}
