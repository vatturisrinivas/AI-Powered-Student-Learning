package com.vcube.AI_Powered.Student.Learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vcube.AI_Powered.Student.Learning.entity.CareerRole;
import com.vcube.AI_Powered.Student.Learning.repository.CareerRoleRepository;
import com.vcube.AI_Powered.Student.Learning.service.CareerRoleService;

@Service
public class CareerRoleServiceImpl implements CareerRoleService {

    private final CareerRoleRepository careerRoleRepository;

    public CareerRoleServiceImpl(CareerRoleRepository careerRoleRepository) {
        this.careerRoleRepository = careerRoleRepository;
    }

    @Override
    public CareerRole createCareerRole(CareerRole careerRole) {
        return careerRoleRepository.save(careerRole);
    }

    @Override
    public CareerRole getCareerRoleById(Long id) {
        return careerRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career role not found"));
    }

    @Override
    public List<CareerRole> getAllCareerRoles() {
        return careerRoleRepository.findAll();
    }

    @Override
    public List<CareerRole> getCareerRolesByDomainId(Long domainId) {
        return careerRoleRepository.findByDomainId(domainId);
    }

    @Override
    public CareerRole updateCareerRole(Long id, CareerRole careerRole) {
        CareerRole existingRole = getCareerRoleById(id);

        existingRole.setDomain(careerRole.getDomain());
        existingRole.setTitle(careerRole.getTitle());
        existingRole.setDescription(careerRole.getDescription());
        existingRole.setRequiredSkills(careerRole.getRequiredSkills());

        return careerRoleRepository.save(existingRole);
    }

    @Override
    public void deleteCareerRole(Long id) {
        CareerRole existingRole = getCareerRoleById(id);
        careerRoleRepository.delete(existingRole);
    }
}
