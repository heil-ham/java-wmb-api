package com.enigma.wmbspring.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchBillRequest {
    private Integer page;
    private Integer size;
}
