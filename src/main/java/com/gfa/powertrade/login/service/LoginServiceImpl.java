package com.gfa.powertrade.login.service;

import com.gfa.powertrade.common.exceptions.InvalidUserTypeException;
import com.gfa.powertrade.login.exceptions.LoginFailureException;
import com.gfa.powertrade.login.models.LoginDTO;
import com.gfa.powertrade.login.models.TokenResponseDTO;
import com.gfa.powertrade.registration.models.UserType;
import com.gfa.powertrade.security.JwtSystemKeys;
import com.gfa.powertrade.user.models.User;
import com.gfa.powertrade.user.services.UserService;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

  private UserService userService;

  private JwtSystemKeys jwtSystemKeys;

  @Override
  public TokenResponseDTO login(LoginDTO loginDTO) throws LoginFailureException {
    validateUsertype(loginDTO);
    Optional<User> user = userService.findByUsername(loginDTO.getUsername(), loginDTO.getUserType().toString());
    if (!user.isPresent() || !BCrypt.checkpw(loginDTO.getPassword(), user.get().getPassword()))
      throw new LoginFailureException();
    String jws = createToken(user.get());
    return new TokenResponseDTO(jws);
  }

  public String createToken(User user) {
    String jws = Jwts.builder()
        .claim("username", user.getUsername())
        .claim("userID", user.getId())
        .claim("userType", user.getUserType())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
        .signWith(jwtSystemKeys.getKey()).compact();
    return jws;
  }

  @Override
  public void validateUsertype(LoginDTO loginDTO) throws InvalidUserTypeException {

    if (!loginDTO.getUserType().toUpperCase().equals(UserType.SUPPLIER.toString())
        && !loginDTO.getUserType().toUpperCase().equals(
        UserType.CONSUMER.toString())) {
      throw new InvalidUserTypeException("Invalid usertype.");
    }

  }

}
