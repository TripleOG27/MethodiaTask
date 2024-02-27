package org.detelin.service;

import java.io.IOException;

public interface ReaderService {
    int readInt();

    String readLine() throws IOException;
}
