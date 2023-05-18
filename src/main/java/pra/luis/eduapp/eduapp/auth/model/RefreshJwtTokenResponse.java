package pra.luis.eduapp.eduapp.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class RefreshJwtTokenResponse {

    private String jwttoken;
    private Date expirationDate;

}
