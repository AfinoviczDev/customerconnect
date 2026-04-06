package dev.afinovicz.customerconnect.controller.dto;

public record PaginationResponse(Integer page,
                                 Integer pageSize,
                                 Long totalElements,
                                 Integer totalPages) {
}
