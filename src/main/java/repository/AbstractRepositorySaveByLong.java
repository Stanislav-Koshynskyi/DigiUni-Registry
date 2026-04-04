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
import java.util.HashMap;

public abstract class AbstractRepositorySaveByLong<T extends Entity>
        extends AbstractRepositoryByLong<T>{
    private final ObjectMapper objectMapper;
    private final File file;
    private final Class<T> clazz;
    public AbstractRepositorySaveByLong(Class<T> clazz, File file) {
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
        loadFromFile();
    }
    @Override
    public synchronized T save(T entity) {
        T savedEntity = super.save(entity);
        saveToFile();
        return savedEntity;
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
        saveToFile();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
        saveToFile();
    }
    private void saveToFile() {
        try {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            long currentId = getCurrentId();
            RepositoryWrapperForSave<T> repositoryWrapperForSave = new RepositoryWrapperForSave<T>(
                    currentId, new HashMap<>(getData())
            );
            objectMapper.writeValue(file, repositoryWrapperForSave );
        } catch (IOException e) {
            throw new RuntimeException(e); // тут має бути якись кастомний, потім додам
        }
    }
    private void loadFromFile() {
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try {
            RepositoryWrapperForSave<T> wrapper = objectMapper.readValue(
                    file,
                    objectMapper.getTypeFactory().constructParametricType(RepositoryWrapperForSave.class, clazz)
            );

            getData().putAll(wrapper.getData());

            setCurrentId(wrapper.getCurrentId());

        } catch (IOException e) {
            throw new RuntimeException(e); // тут теж своя
        }
    }
}
