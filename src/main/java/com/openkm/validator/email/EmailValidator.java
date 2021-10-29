package com.openkm.validator.email;

import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.dao.AuthDAO;
import com.openkm.spring.PrincipalUtils;
import com.openkm.validator.ValidatorException;
import com.openkm.validator.password.CompletePasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmailValidator{

	private static Logger log = LoggerFactory.getLogger(EmailValidator.class);

	public void Validate(String email) throws ValidatorException, DatabaseException {
		checkFormat(email);
		isExistEmail(email);
	}

	private void checkFormat(String email) throws ValidatorException {
		String regex  = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		if(!email.isEmpty()){
			if(!email.matches(regex)){
				throw new ValidatorException("Email: Invalid email format");
			}
		}
	}

	private void isExistEmail(String email) throws ValidatorException, DatabaseException {
		String user = PrincipalUtils.getUser();
		List<String> listEmails = AuthDAO.findAllEmails(user);
		if(listEmails.contains(email)){
			throw new ValidatorException("Email: Email is already exist!");
		}
	}
}
