package dev.afinovicz.customerconnect.controller;

import dev.afinovicz.customerconnect.controller.dto.APIResponse;
import dev.afinovicz.customerconnect.controller.dto.CreateCustomerDTO;
import dev.afinovicz.customerconnect.controller.dto.PaginationResponse;
import dev.afinovicz.customerconnect.controller.dto.UpdateCustomerDTO;
import dev.afinovicz.customerconnect.entity.CustomerEntity;
import dev.afinovicz.customerconnect.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CreateCustomerDTO dto) {
        var customer = customerService.createCustomer(dto);
        return ResponseEntity.created(URI.create("/customers/" +customer.getId())).build();
    }

    @GetMapping
    public ResponseEntity<APIResponse<CustomerEntity>> findAll(@RequestParam (name = "page", defaultValue = "0") Integer page,
                                                               @RequestParam (name = "pageSize", defaultValue = "10") Integer pageSize,
                                                               @RequestParam (name = "oderdBy", defaultValue = "desc") String orderBy,
                                                               @RequestParam (name = "cpf", required = false) String cpf,
                                                               @RequestParam (name = "email", required = false) String email) {
        var pageResp = customerService.findAll(page, pageSize, orderBy, cpf, email);
        return ResponseEntity.ok(new APIResponse<>(
                pageResp.getContent(),
                new PaginationResponse(pageResp.getNumber(),
                                       pageResp.getSize(),
                                       pageResp.getTotalElements(),
                                       pageResp.getTotalPages())));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerEntity> findById(@PathVariable("id") Long id) {
        var customer = customerService.findById(id);
        return customer.isPresent() ?
                ResponseEntity.ok(customer.get()) :
                ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CustomerEntity> updateById(@PathVariable("id") Long id,
                                                     @RequestBody UpdateCustomerDTO dto) {
        var customer = customerService.updateById(id, dto);
        return customer.isPresent() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CustomerEntity> deleteById(@PathVariable("id") Long id) {
        var deleted = customerService.deleteById(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }


}
