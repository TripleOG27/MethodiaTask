package org.detelin.service;

import org.detelin.enums.ApplicationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private static final String CHOOSE_PROGRAM = "Enter the id of the program you want to run!";
    private static final String NO_PROGRAMS_IMPLEMENTED = "Sorry, nothing to do here!";
    private static final String CLOSE_PARENTHESIS_SPACE = ") ";
    private static final String STRING_TO_BE_REVERSED = "Enter a word to be reversed!";
    private static final String STRING_TO_COUNT_WORDS = "Enter a word to check how many words there are separated by space!";
    private static final String SPACE_PATTERN = "\\s+";
    private static final String TRY_AGAIN = "Try again!";

    private final WriterService writerService;
    private final ReaderService readerService;
    private final InputValidationService validationService;

    @Autowired
    public ApplicationServiceImpl(WriterService writerService, ReaderService readerService, InputValidationService validationService) {
        this.writerService = writerService;
        this.readerService = readerService;
        this.validationService = validationService;
    }

    @Override
    public void run() {
        List<ApplicationEnum> programs = Arrays.stream(ApplicationEnum.values()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(programs)) {
            writerService.writeLine(NO_PROGRAMS_IMPLEMENTED);
            return;
        }

        programs.forEach(app -> writerService.writeLine(app.getKey() + CLOSE_PARENTHESIS_SPACE + app.name()));
        writerService.writeLine(CHOOSE_PROGRAM);
        try {
            int appKey = readerService.readInt();
            ApplicationEnum app = ApplicationEnum.getByKey(appKey);
            switch (app) {
                case STRING_REVERSE -> runStringReverseProgram();
                case STRING_WORD_COUNT -> runStringWordCount();
                case LIST_TRAVERSE -> runListTraverse();
                case STRING_DUPLICATES_FIND -> runFindDuplicatesInString();
                case EXCEL_FILE_READ -> runExcelFileRead();
                default -> run();
            }
        } catch (IllegalArgumentException ex) {
            writerService.writeLine(TRY_AGAIN);
            run();
        }
    }

    private void runExcelFileRead() {

    }

    private void runFindDuplicatesInString() {

    }

    private void runListTraverse() {

    }

    private void runStringWordCount() {
        writerService.writeLine(STRING_TO_COUNT_WORDS);
        try {
            String input = readerService.readLine();
            validationService.validateString(input);
            //collect to a map with count in order to have duplicate words also counted
            Map<String, Long> wordCountMap = Arrays.stream(input.split(SPACE_PATTERN))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            writerService.writeLine(wordCountMap.values().stream().mapToLong(Long::longValue).sum());
            run();
        } catch (IOException | InputMismatchException ex) {
            writerService.writeLine(TRY_AGAIN);
            run();
        }
    }

    private void runStringReverseProgram() {
        writerService.writeLine(STRING_TO_BE_REVERSED);
        try {
            String input = readerService.readLine();
            validationService.validateString(input);
            writerService.writeLine(reverseString(input));
            run();
        } catch (IOException | InputMismatchException ex) {
            writerService.writeLine(TRY_AGAIN);
        }

    }

    private String reverseString(String input) {
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;

            left++;
            right--;
        }
        return new String(chars);
    }
}
