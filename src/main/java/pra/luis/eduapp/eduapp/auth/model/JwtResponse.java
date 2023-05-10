package pra.luis.eduapp.eduapp.auth.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minidev.json.JSONObject;

@Getter
@AllArgsConstructor
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	
	public JSONObject toJSON() {
		JSONObject body = new JSONObject();
		JSONObject data = new JSONObject();
	    data.put("message", "User logged in successfully");
	    data.put("token", this.jwttoken);
	    body.put("data", data);
	    return body;
	}
}