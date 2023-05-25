package pra.luis.eduapp.eduapp.persons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PersonListResponse {
    private List<Person> personList;
    private int totalPages;
    private Long totalRecords;
}
