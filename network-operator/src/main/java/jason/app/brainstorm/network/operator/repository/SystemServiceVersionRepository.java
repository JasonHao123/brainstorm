package jason.app.brainstorm.network.operator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jason.app.brainstorm.network.operator.entity.SystemServiceVersion;

@Repository
public interface SystemServiceVersionRepository extends CrudRepository<SystemServiceVersion, Long> {
	SystemServiceVersion findFirstByServiceGroup_systemNameAndServiceGroup_countryAndServiceGroup_systemVersionAndServiceGroup_serviceGroupAndService(String systemName,String country,String systemVersion,String serviceGroup,String service);
}
