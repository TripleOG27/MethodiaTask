package org.detelin.service;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.InputMismatchException;

@Service
public class ReaderServiceImpl implements ReaderService {
    private static final String EMPTY_STRING = "";

    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public int readInt() {

        try {
            String input = consoleReader.readLine();
            return Integer.parseInt(input);
        } catch (IOException | NumberFormatException ex) {
            throw new InputMismatchException(ex.getMessage());
        }
    }

    @Override
    public String readLine() {
        try {
            return consoleReader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return EMPTY_STRING;
        }
    }

    @Override
    public Workbook readExcelFile(String filePath) {
        try {
            File file = new File(filePath);
            return Workbook.getWorkbook(file);
        } catch (BiffException | IOException ex) {
            throw new InputMismatchException(ex.getMessage());
        }
    }
}
