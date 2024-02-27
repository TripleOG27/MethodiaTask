package org.detelin.service;

import jxl.*;
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
    private static final String STRING_TO_BE_REVERSED_PROMPT = "Enter a word to be reversed!";
    private static final String STRING_TO_COUNT_WORDS_PROMPT = "Enter a word to check how many words there are separated by space!";
    private static final String SPACE_PATTERN = "\\s+";
    private static final String LIST_TRAVERSE_PROMPT = "Enter elements separated by space!";
    private static final String STRING_DUPLICATES_FIND_PROMPT = "Enter a word or phrase to check for duplicate characters!";
    //the chosen library only supports xls ad not xlsx
    private static final String EXCEL_FILE_READ_PROMPT = "Enter a valid absolute path to a xls format excel file with permissions for read!";
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
        } catch (InputMismatchException ex) {
            writerService.writeLine(TRY_AGAIN);
            run();
        }
    }

    private void runExcelFileRead() {
        writerService.writeLine(EXCEL_FILE_READ_PROMPT);
        try {
            String filePath = readerService.readLine();
            validationService.validateString(filePath);

            Workbook workbook =  readerService.readExcelFile(filePath);
            StringBuilder sb = new StringBuilder();
            for (Sheet sheet : workbook.getSheets()) {
                for (int row = 0; row < sheet.getRows(); row++) {
                    Cell[] rowCells = sheet.getRow(row);
                    for (int col = 0; col < rowCells.length; col++) {
                        Cell cell = sheet.getCell(col, row);
                        sb.append(cell.getContents());
                        sb.append(", ");
                    }
                }
            }
            writerService.writeLine(sb.toString());
        } catch (InputMismatchException | IOException ex) {
            writerService.writeLine(ex + TRY_AGAIN);
        }
        run();
    }

    private void runFindDuplicatesInString() {
        writerService.writeLine(STRING_DUPLICATES_FIND_PROMPT);
        try {
            String input = readerService.readLine();
            validationService.validateString(input);

            //my first choice option
            Map<String, Long> charMap = Arrays.stream(input.split(""))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            writerService.writeLine(String.format("Found the following duplicate characters %s",
                    String.join(", ", charMap.entrySet().stream()
                            .filter( entry -> entry.getValue() > 1)
                            .map(Map.Entry::getKey).toList())));
        } catch (InputMismatchException | IOException ex) {
            writerService.writeLine(TRY_AGAIN);
        }
        run();
    }

    private void runListTraverse() {
        writerService.writeLine(LIST_TRAVERSE_PROMPT);
        try{
            String input = readerService.readLine();
            validationService.validateString(input);
            List<String> arr = Arrays.stream(input.split(SPACE_PATTERN)).toList();

            // traverse with for loop
            int countFor = 0;
            for (String s : arr) {
                countFor++;
            }
            // traverse with while loop
            int countWhile = 0;
            while (countWhile < arr.size()) {
                countWhile++;
            }
            writerService.writeLine(String.format("Counted %s items with For loop", countFor));
            writerService.writeLine(String.format("Counted %s items with While loop", countWhile));
        } catch (InputMismatchException | IOException ex) {
            writerService.writeLine(TRY_AGAIN);
        }
        run();
    }

    private void runStringWordCount() {
        writerService.writeLine(STRING_TO_COUNT_WORDS_PROMPT);
        try {
            String input = readerService.readLine();
            validationService.validateString(input);
            //collect to a map with count in order to have duplicate words also counted
            Map<String, Long> wordCountMap = Arrays.stream(input.split(SPACE_PATTERN))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            writerService.writeLine(wordCountMap.values().stream().mapToLong(Long::longValue).sum());
        } catch (IOException | InputMismatchException ex) {
            writerService.writeLine(TRY_AGAIN);
        }
        run();
    }

    private void runStringReverseProgram() {
        writerService.writeLine(STRING_TO_BE_REVERSED_PROMPT);
        try {
            String input = readerService.readLine();
            validationService.validateString(input);
            writerService.writeLine(reverseString(input));
        } catch (IOException | InputMismatchException ex) {
            writerService.writeLine(TRY_AGAIN);
        }
        run();
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
