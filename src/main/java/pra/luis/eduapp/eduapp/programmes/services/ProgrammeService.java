package pra.luis.eduapp.eduapp.programmes.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.model.ProgrammeDTO;
import pra.luis.eduapp.eduapp.programmes.repository.ProgrammeRepository;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;
import pra.luis.eduapp.eduapp.utils.FileUploadUtil;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProgrammeService {

    private final ProgrammeRepository programmeRepository;

    public Optional<Programme> findById(int id){
        return programmeRepository.findById(id);
    }

    public Page<Programme> findAll(Specification<Programme> spec, @NonNull Pageable pageable) {
        return programmeRepository.findAll(spec, pageable);
    }

    public Programme insert(ProgrammeDTO programmeDTO) throws EntityWithExistingFieldException, IOException {
        if(programmeRepository.existsByName(programmeDTO.getName()))
            throw new EntityWithExistingFieldException("Programme is already registered");
        String filePath = FileUploadUtil.saveFile(getInitials(programmeDTO.getName())+"_logo",
                                                        programmeDTO.getLogoImage());
        Programme programme = programmeDTO.toProgramme();
        programme.setLogoUrl(filePath);
        return programmeRepository.save(programme);
    }

    public Programme update(Integer programmeId, Programme updatedProgramme){
        return programmeRepository.findById(programmeId)
                .map( programme -> {
                    programme.updateProperties(updatedProgramme);
                    return programmeRepository.save(programme);
                }).orElseThrow(() -> new EntityNotFoundException("Can't update unregistered person"));
    }

    public void delete(Integer programmeId){
        if(findById(programmeId).isEmpty())
            throw new EntityNotFoundException("Programme of id "+programmeId+" not founded");
        programmeRepository.deleteById(programmeId);
    }

    private String getInitials(String phrase){
        StringBuilder initials = new StringBuilder();
        for (String s : phrase.split(" ")) {
            initials.append(s.charAt(0));
        }
        return initials.toString();
    }

}
