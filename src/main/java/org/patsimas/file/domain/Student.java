package org.patsimas.file.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Student {

    private static final long serialVersionUID = 1L;

    public String name, rollNo, department, result, pointer;
}
