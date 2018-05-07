package jason.app.brainstorm.network.operator.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jason.app.brainstorm.network.operator.entity.HostBlackListItem;


@Repository
public interface BlackListRepository extends CrudRepository<HostBlackListItem, String> {

}
