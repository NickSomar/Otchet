package by.serchenya.storage.repo;

import by.serchenya.storage.entity.Storage;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepo extends CrudRepository<Storage, Long> {
}
