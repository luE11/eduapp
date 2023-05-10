package pra.luis.eduapp.eduapp.programmes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.repository.ProgrammeRepository;

import java.util.Optional;

@Service
public class ProgrammeService {

    @Autowired
    private ProgrammeRepository programmeRepository;

    public Optional<Programme> findById(int id){
        return programmeRepository.findById(id);
    }

}
