package org.detelin.service;

import jxl.Workbook;

import java.io.IOException;

public interface ReaderService {
    int readInt();

    String readLine() throws IOException;

    Workbook readExcelFile(String filePath);
}
