package dev.afinovicz.customerconnect.service;

import dev.afinovicz.customerconnect.controller.dto.CreateCustomerDTO;
import dev.afinovicz.customerconnect.controller.dto.UpdateCustomerDTO;
import dev.afinovicz.customerconnect.entity.CustomerEntity;
import dev.afinovicz.customerconnect.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity createCustomer(CreateCustomerDTO dto) {
        var entity = new CustomerEntity();
        entity.setFullName(dto.fullName());
        entity.setEmail(dto.email());
        entity.setCpf(dto.cpf());
        entity.setPhoneNumber(dto.phoneNumber());

        return customerRepository.save(entity);
    }

    public Page<CustomerEntity> findAll(Integer page,
                                        Integer pageSize,
                                        String orderBy,
                                        String cpf,
                                        String email) {

        var pageRequest = getPageRequest(page, pageSize, orderBy);
        return findWithFilter(cpf, email, pageRequest);
    }

    private Page<CustomerEntity> findWithFilter(String cpf, String email, PageRequest pageRequest) {
        if(hasText(cpf) && hasText(email)) {
            return customerRepository.findByCpfAndEmail(cpf, email, pageRequest);
        }
        if(hasText(cpf)) {
            return customerRepository.findByCpf(cpf, pageRequest);
        }
        if(hasText(email)) {
            return customerRepository.findByEmail(email, pageRequest);
        }
        return customerRepository.findAll(pageRequest);
    }

    private PageRequest getPageRequest(Integer page, Integer pageSize, String orderBy) {
        var direction = Sort.Direction.ASC;
        if(orderBy.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        return PageRequest.of(page, pageSize, direction, "createdAt");
    }

    public Optional<CustomerEntity> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerEntity> updateById(Long id, UpdateCustomerDTO dto) {
        var customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            if(hasText(dto.fullName())) {
                customer.get().setFullName(dto.fullName());
            }
            if(hasText(dto.email())) {
                customer.get().setEmail(dto.email());
            }
            if(hasText(dto.phoneNumber())) {
                customer.get().setPhoneNumber(dto.phoneNumber());
            }
            customerRepository.save(customer.get());
        }
        return customer;
    }

    public Boolean deleteById(Long id) {

        var exists = customerRepository.existsById(id);
        if(exists) {
            customerRepository.deleteById(id);
        }
        return exists;
    }
}
