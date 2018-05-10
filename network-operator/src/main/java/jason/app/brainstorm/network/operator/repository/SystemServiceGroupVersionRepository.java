package jason.app.brainstorm.network.operator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jason.app.brainstorm.network.operator.entity.SystemServiceGroupVersion;

@Repository
public interface SystemServiceGroupVersionRepository extends CrudRepository<SystemServiceGroupVersion, Long> {
	public SystemServiceGroupVersion findFirstByCountryIsNullOrCountryIsAndSystemNameAndSystemVersionAndServiceGroupOrderByCountryDesc(String country,String systemName,String version,String serviceGroup);
}
