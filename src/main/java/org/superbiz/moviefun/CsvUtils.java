package org.superbiz.moviefun;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {

    public static String readFile(String path) {
        try {
            Scanner scanner = new Scanner(CsvUtils.class.getClassLoader().getResourceAsStream(path)).useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return "";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> readFromCsv(ObjectReader objectReader, String path) {
        try {
            List<T> results = new ArrayList<>();

            MappingIterator<T> iterator = objectReader.readValues(readFile(path));

            while (iterator.hasNext()) {
                results.add(iterator.nextValue());
            }

            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
