package com.vcube.AI_Powered.Student.Learning.service;

import java.util.List;

import com.vcube.AI_Powered.Student.Learning.entity.CareerRole;

public interface CareerRoleService {

    CareerRole createCareerRole(CareerRole careerRole);

    CareerRole getCareerRoleById(Long id);

    List<CareerRole> getAllCareerRoles();

    List<CareerRole> getCareerRolesByDomainId(Long domainId);

    CareerRole updateCareerRole(Long id, CareerRole careerRole);

    void deleteCareerRole(Long id);
}
