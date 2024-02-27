package org.detelin.service;

import org.detelin.enums.ApplicationEnum;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ReaderServiceImpl implements ReaderService {

    private static final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public int readInt() {

        try {
            String input = consoleReader.readLine();
            return Integer.parseInt(input);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readLine() {
        try {
            return consoleReader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
