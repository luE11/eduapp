package pra.luis.eduapp.eduapp.subjects.model;

import jakarta.validation.constraints.NotNull;

/**
 * @author luE11 on 23/06/23
 */
public class SubjectSubcribeDTO {
    @NotNull
    protected boolean subscribable = false;

    public SubjectSubcribeDTO(){ }

    public SubjectSubcribeDTO(boolean subscribable) {
        this.subscribable = subscribable;
    }

    public boolean isSubscribable(){
        return this.subscribable;
    }
}
