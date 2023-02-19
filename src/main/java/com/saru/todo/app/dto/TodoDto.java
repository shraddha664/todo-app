package com.saru.todo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private String taskName;
    private String description;
//    private String date;
    private Boolean isCompleted=Boolean.FALSE;
}
