package dev.afinovicz.customerconnect.controller.dto;

import java.util.List;

public record APIResponse<T>(List<T> data, PaginationResponse pagination) {
}
