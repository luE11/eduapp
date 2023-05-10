package pra.luis.eduapp.eduapp.auth.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	
	//need default constructor for JSON Parsing
	public JwtRequest() { }
	
	public String toString() {
		return username+": "+password;
	}
}