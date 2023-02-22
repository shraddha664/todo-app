package com.saru.todo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {
    private String taskName;
    private String description;
    private Boolean isCompleted=Boolean.FALSE;
}
