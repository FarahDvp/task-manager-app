package com.isamm.tasks.dto;
 
import com.isamm.tasks.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

	  private String userName;
	  private String password;
      private String fullname;
	  private String email;
	  private String phone;
	  private Role role;

}
