package pra.luis.eduapp.eduapp.programmes.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Programme insert(ProgrammeDTO programmeDTO) throws EntityWithExistingFieldException, IOException {
        if(programmeRepository.existsByName(programmeDTO.getName()))
            throw new EntityWithExistingFieldException("Programme is already registered");
        String filePath = FileUploadUtil.saveFile(getInitials(programmeDTO.getName())+"_logo",
                                                        programmeDTO.getLogoImage());
        Programme programme = programmeDTO.toProgramme();
        programme.setLogoUrl(filePath);
        return programmeRepository.save(programme);
    }

    private String getInitials(String phrase){
        String initials = "";
        for (String s : phrase.split(" ")) {
            initials+=s.charAt(0);
        }
        return initials;
    }

}
