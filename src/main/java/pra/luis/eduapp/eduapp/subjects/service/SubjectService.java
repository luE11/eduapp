package pra.luis.eduapp.eduapp.subjects.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.services.ProgrammeService;
import pra.luis.eduapp.eduapp.subjects.model.Subject;
import pra.luis.eduapp.eduapp.subjects.model.SubjectDTO;
import pra.luis.eduapp.eduapp.subjects.repository.SubjectRepository;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

/**
 * @author luE11 on 22/06/23
 */
@AllArgsConstructor
@Service
public class SubjectService {
    private SubjectRepository subjectRepository;
    private ProgrammeService programmeService;

    public Subject insert(SubjectDTO subjectDTO) throws EntityWithExistingFieldException {
        if(subjectRepository.existsBySubjectName(subjectDTO.getSubjectName()))
            throw new EntityWithExistingFieldException("The subject name is already used by another record");
        Programme subjectProgramme = programmeService.findById(subjectDTO.getProgrammeId())
                .orElseThrow(() -> new EntityNotFoundException("Programme of id "+subjectDTO.getProgrammeId()+ " not found"));
        Subject requiredPrevSubject = null;
        if(subjectDTO.getRequiredSubjectId()!=null)
            requiredPrevSubject = subjectRepository.findById(subjectDTO.getRequiredSubjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Subject of id "+subjectDTO.getRequiredSubjectId()+ " not found"));
        Subject subject = subjectDTO.toSubject();
        subject.setRequiredSubject(requiredPrevSubject);
        subject.setProgramme(subjectProgramme);
        return subjectRepository.save(subject);
    }
}
