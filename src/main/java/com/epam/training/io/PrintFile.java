package com.epam.training.io;

import com.epam.training.exception.PrintFileException;
import com.epam.training.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PrintFile {

    private final static Logger logger = LogManager.getLogger(PrintFile.class);

    private static final String PATH_CATALOG_MESSAGE = "Data library/!Message for Admin/";
    private final String DEFAULT_PATH = "Data library/Default Catalog/";

    private Validator validator = new Validator();

    public PrintFile() {
        createCatalog(PATH_CATALOG_MESSAGE);
        createCatalog(DEFAULT_PATH);
    }


    public void print(String text, String path, String nameFile) throws PrintFileException {
        try {
            String pathFile;
            if (!validator.isNull(path)) {
                pathFile = path + getNameFile(nameFile);
            }else {
                pathFile = DEFAULT_PATH + getNameFile(nameFile);
            }
                Files.createFile(Paths.get(pathFile.replace('\\', '/')));
                Files.write(Paths.get(pathFile.replace('\\', '/')), text.getBytes());

        }catch (IOException e) {
            logger.error("Method print... Error...");
            logger.error(e);
            throw new PrintFileException(e);
        }
    }

    public boolean existCatalog(String path) {
        if(Files.exists(Paths.get(path))) {
            return true;
        }
        return false;
    }

    public String getNameFile(String name) {
        return name + ".txt";
    }

    public void createCatalog(String path){
        try {
            if(!Files.exists(Paths.get(path))) {
                Files.createDirectories(Paths.get(path));
            }
        }catch (IOException e) {
            logger.error("Method getNameFile... Error...");
            logger.error(e);
        }
    }

    public String getPATH_CATALOG_MESSAGE() {
        return PATH_CATALOG_MESSAGE;
    }
}
