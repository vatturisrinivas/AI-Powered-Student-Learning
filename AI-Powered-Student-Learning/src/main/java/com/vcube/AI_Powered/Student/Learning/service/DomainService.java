package com.vcube.AI_Powered.Student.Learning.service;

import java.util.List;

import com.vcube.AI_Powered.Student.Learning.entity.Domain;

public interface DomainService {

    Domain createDomain(Domain domain);

    Domain getDomainById(Long id);

    Domain getDomainByName(String name);

    List<Domain> getAllDomains();

    Domain updateDomain(Long id, Domain domain);

    void deleteDomain(Long id);
}
