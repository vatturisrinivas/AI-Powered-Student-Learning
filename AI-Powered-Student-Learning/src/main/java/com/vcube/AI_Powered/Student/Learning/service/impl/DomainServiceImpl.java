package com.vcube.AI_Powered.Student.Learning.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vcube.AI_Powered.Student.Learning.entity.Domain;
import com.vcube.AI_Powered.Student.Learning.repository.DomainRepository;
import com.vcube.AI_Powered.Student.Learning.service.DomainService;

@Service
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;

    public DomainServiceImpl(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public Domain createDomain(Domain domain) {
        if (domainRepository.existsByName(domain.getName())) {
            throw new RuntimeException("Domain name already exists");
        }

        return domainRepository.save(domain);
    }

    @Override
    public Domain getDomainById(Long id) {
        return domainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Domain not found"));
    }

    @Override
    public Domain getDomainByName(String name) {
        return domainRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Domain not found"));
    }

    @Override
    public List<Domain> getAllDomains() {
        return domainRepository.findAll();
    }

    @Override
    public Domain updateDomain(Long id, Domain domain) {
        Domain existingDomain = getDomainById(id);

        if (!existingDomain.getName().equals(domain.getName())
                && domainRepository.existsByName(domain.getName())) {
            throw new RuntimeException("Domain name already exists");
        }

        existingDomain.setName(domain.getName());
        existingDomain.setDescription(domain.getDescription());

        return domainRepository.save(existingDomain);
    }

    @Override
    public void deleteDomain(Long id) {
        Domain existingDomain = getDomainById(id);
        domainRepository.delete(existingDomain);
    }
}
