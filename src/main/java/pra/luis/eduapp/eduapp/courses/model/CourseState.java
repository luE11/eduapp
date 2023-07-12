package pra.luis.eduapp.eduapp.courses.model;

/**
 * @author luE11 on 28/06/23
 */
public enum CourseState {

    CREATED("CREATED"), ACTIVE("ACTIVE"), FINISHED("FINISHED");

    private final String stateStr;

    private CourseState(String state){
        this.stateStr = state;
    }

    public String getStateStr() {
        return stateStr;
    }
}
