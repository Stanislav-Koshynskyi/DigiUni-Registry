package repository;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.Entity;
import exception.StorageException;
import util.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public abstract class AbstractRepositorySaveByLong<T extends Entity>
        extends AbstractRepositoryByLong<T> implements Stored {
    private final ObjectMapper objectMapper;
    private final Path file;
    private final Class<T> clazz;
    public AbstractRepositorySaveByLong(Class<T> clazz, Path file) {
        super();
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper();

        //for date format possible
        objectMapper.registerModule(new JavaTimeModule());

        // for pretty date format
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // for read
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.file = file;

        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
        } catch (IOException e) {
            throw new StorageException("Error of create directory: " + file.getParent(), e);
        }
        loadFromFile();
    }
    public synchronized void saveToFile() {
        Path tempPath = file.resolveSibling(file.getFileName() + ".tmp");
        try {
            long currentId = getCurrentId();
            RepositoryWrapperForSave<T> repositoryWrapperForSave = new RepositoryWrapperForSave<T>(
                    currentId, new HashMap<>(getData())
            );
            objectMapper.writeValue(tempPath.toFile(), repositoryWrapperForSave );
            Files.move(tempPath, file,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new StorageException("Error to write in file: " + file.getFileName(), e);
        }
    }
    private void loadFromFile()  {


        try {

            if (!Files.exists(file) || Files.size(file) == 0)
                return;
            RepositoryWrapperForSave<T> wrapper = objectMapper.readValue(
                    file.toFile(),
                    objectMapper.getTypeFactory().constructParametricType(RepositoryWrapperForSave.class, clazz)
            );

            getData().putAll(wrapper.getData());

            setCurrentId(wrapper.getCurrentId());

        } catch (IOException e) {
            throw new StorageException("Error of read file: " + file.getFileName(), e);
        }
    }
}
