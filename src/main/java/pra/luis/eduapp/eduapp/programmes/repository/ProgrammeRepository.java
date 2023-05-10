package pra.luis.eduapp.eduapp.programmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pra.luis.eduapp.eduapp.programmes.model.Programme;

public interface ProgrammeRepository extends JpaRepository<Programme, Integer> {


}
