
package com.vcube.AI_Powered.Student.Learning.repository;

import com.vcube.AI_Powered.Student.Learning.entity.CareerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CareerRoleRepository
        extends JpaRepository<CareerRole, Long> {

    List<CareerRole> findByDomainId(Long domainId);
}
