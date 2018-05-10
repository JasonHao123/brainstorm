package jason.app.brainstorm.network.operator.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jason.app.brainstorm.network.operator.entity.VersionRule;

@Repository
public interface VersionRuleRepository extends CrudRepository<VersionRule, Long> {
	VersionRule findFirstByServiceVersion_systemNameAndServiceVersion_countryAndServiceVersion_systemVersionAndServiceVersion_serviceGroupAndServiceVersion_service(String systemName,String country,String systemVersion,String serviceGroup,String Service);
}
