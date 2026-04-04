package repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryWrapperForSave<T> {
    private long currentId;
    private HashMap<Long, T> data;
}
