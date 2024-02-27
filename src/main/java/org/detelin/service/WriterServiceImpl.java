package org.detelin.service;

import org.springframework.stereotype.Service;

@Service
public class WriterServiceImpl implements WriterService {

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }

    @Override
    public void writeLine(Long line) {
        System.out.println(line);
    }
}
