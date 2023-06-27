package pra.luis.eduapp.eduapp.subjects.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pra.luis.eduapp.eduapp.programmes.model.Programme;
import pra.luis.eduapp.eduapp.programmes.services.ProgrammeService;
import pra.luis.eduapp.eduapp.subjects.model.FullSubjectDTO;
import pra.luis.eduapp.eduapp.subjects.model.Subject;
import pra.luis.eduapp.eduapp.subjects.model.SubjectDTO;
import pra.luis.eduapp.eduapp.subjects.model.SubjectSubcribeDTO;
import pra.luis.eduapp.eduapp.subjects.repository.SubjectRepository;
import pra.luis.eduapp.eduapp.utils.EntityWithExistingFieldException;

import java.util.function.Supplier;

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
        Programme subjectProgramme = getProgrammeById(subjectDTO.getProgrammeId());
        Subject requiredPrevSubject = getRequiredPrevSubject(subjectDTO.getRequiredSubjectId());
        Subject subject = subjectDTO.toSubject();
        subject.setRequiredSubject(requiredPrevSubject);
        subject.setProgramme(subjectProgramme);
        return subjectRepository.save(subject);
    }

    public FullSubjectDTO findById(int id){
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(generateSubjectNotFoundException(id));
        return new FullSubjectDTO(subject);
    }

    public Page<Subject> findAll(Specification<Subject> spec, @NonNull Pageable pageable){
        return subjectRepository.findAll(spec, pageable);
    }

    public Subject update(Integer id, Subject updated){
        return subjectRepository.findById(id)
                .map((found) -> {
                    found.updateProperties(updated);
                    return subjectRepository.save(found);
                })
                .orElseThrow(generateSubjectNotFoundException(id));
    }

    public void delete(Integer id){
        if(!subjectRepository.existsById(id))
            throw generateSubjectNotFoundException(id).get();
        subjectRepository.deleteById(id);
    }

    /**
     * Patch canSubscribe attribute
     * @param id
     * @param subcribeDTO
     * @return
     */
    public Subject patchSubscribable(Integer id, SubjectSubcribeDTO subscribeDTO){
        return subjectRepository.findById(id)
                .map((found) -> {
                    found.setSubscribable(subscribeDTO.isSubscribable());
                    return subjectRepository.save(found);
                })
                .orElseThrow(generateSubjectNotFoundException(id));
    }

    private Programme getProgrammeById(Integer id){
        return programmeService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Programme of id "+id+ " not found"));
    }

    private Subject getRequiredPrevSubject(Integer subjectId){
        if(subjectId!=null)
            return subjectRepository.findById(subjectId)
                    .orElseThrow(generateSubjectNotFoundException(subjectId));
        return null;
    }

    private Supplier<EntityNotFoundException> generateSubjectNotFoundException(Integer id){
        return () -> new EntityNotFoundException("Subject of id "+id+ " not found");
    }
}
