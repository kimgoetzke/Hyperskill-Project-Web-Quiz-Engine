package engine.persistence;

import engine.business.Completion;
import engine.business.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {
    @Query(value = "SELECT * FROM Completions WHERE user_id = ?1", nativeQuery = true)
    public Page<Completion> findAllByUser(User user, Pageable pageable);
}