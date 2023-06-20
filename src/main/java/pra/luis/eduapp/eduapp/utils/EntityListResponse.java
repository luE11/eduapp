package pra.luis.eduapp.eduapp.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EntityListResponse <T> {

    private List<T> result;
    private int page;
    private int size;
    private int totalPages;
    private Long totalRecords;

}
