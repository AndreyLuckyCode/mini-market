package andrey.code.manager.repositoy;

import andrey.code.manager.entity.MiniMarketUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MiniMarketUserRepository extends JpaRepository<MiniMarketUser, Integer> {

    Optional<MiniMarketUser> findByUsername(String username);
}
