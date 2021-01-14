package com.epam.training.io;

import com.epam.training.exception.PrintFileException;;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class PrintFileTest {

    PrintFile printFile = new PrintFile();

    @Test
    void print() {
        try {
            String path = printFile.getPATH_CATALOG_MESSAGE();
            String name = "Test file";
            String text = "Test";
            printFile.print(text, path, name);
            assertTrue(Files.exists(Paths.get(path + printFile.getNameFile(name))));
        }catch (PrintFileException e) {}
    }

    @Test
    void existCatalog() {
        String path = "Data library/Data users/";
        assertTrue(printFile.existCatalog(path));

    }

    @Test
    void getNameFile() {
        String name = "Alex";
        String result = printFile.getNameFile(name);
        System.out.println(result);
        assertTrue(result.length() > name.length());
    }

    @Test
    void createCatalog() {
        String path = "Data library/Data users";
        printFile.createCatalog(path);
        assertTrue(Files.exists(Paths.get(path)));
    }
}