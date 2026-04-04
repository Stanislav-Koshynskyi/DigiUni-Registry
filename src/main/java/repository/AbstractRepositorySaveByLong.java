package repository;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.Entity;
import util.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public abstract class AbstractRepositorySaveByLong<T extends Entity>
        extends AbstractRepositoryByLong<T>{
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
            throw new RuntimeException("Не вдалося створити директорії", e); // щось кастомне мб
        }
        loadFromFile();
    }
    @Override
    public synchronized T save(T entity) {
        T savedEntity = super.save(entity);
        saveToFile();
        return savedEntity;
    }

    @Override
    public synchronized void delete(T entity) {
        super.delete(entity);
        saveToFile();
    }

    @Override
    public synchronized void deleteById(Long id) {
        super.deleteById(id);
        saveToFile();
    }
    private void saveToFile() {
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
            throw new RuntimeException(e); // тут має бути якись кастомний, потім додам
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
            throw new RuntimeException(e); // тут теж своя
        }
    }
}
