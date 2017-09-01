package com.edassist.controller;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.UnauthorizedException;
import com.edassist.exception.UnprocessableEntityException;
import com.edassist.models.domain.Participant;
import com.edassist.models.domain.ThinParticipantSearch;
import com.edassist.models.domain.User;
import com.edassist.models.domain.UserType;
import com.edassist.models.dto.*;
import com.edassist.models.mappers.ParticipantMapper;
import com.edassist.models.mappers.UserMapper;
import com.edassist.models.mappers.UserTypeMapper;
import com.edassist.service.*;
import com.edassist.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

	private final AccessService accessService;
	private final EmailService emailService;
	private final AddressService addressService;
	private final ParticipantService participantService;
	private final UserService userService;
	private final UserTypeService userTypeService;
	private final ParticipantMapper participantMapper;
	private final UserMapper userMapper;
	private final UserTypeMapper userTypeMapper;

	@Autowired
	public UserController(AccessService accessService, EmailService emailService, AddressService addressService, ParticipantService participantService, UserService userService,
			UserTypeService userTypeService, ParticipantMapper participantMapper, UserMapper userMapper, UserTypeMapper userTypeMapper) {
		this.accessService = accessService;
		this.emailService = emailService;
		this.addressService = addressService;
		this.participantService = participantService;
		this.userService = userService;
		this.userTypeService = userTypeService;
		this.participantMapper = participantMapper;
		this.userMapper = userMapper;
		this.userTypeMapper = userTypeMapper;
	}

	@RequestMapping(value = "/v1/users/reset-password", method = RequestMethod.POST)
	public ResponseEntity<Void> resetUserPasword(@RequestBody ResetPasswordDTO resetPasswordDTO) {

		String email = resetPasswordDTO.getEmail();
		Long clientId = resetPasswordDTO.getClientId();

		if (email == null || clientId == null) {
			throw new BadRequestException("Invalid Information To Reset Password");
		}

		List<Long> participantEmailList = addressService.findEmails(email, clientId);
		if (participantEmailList.size() == 1) {
			Long participantId = participantEmailList.get(0);
			Participant ppts = participantService.findById(participantId);
			User user = ppts.getUser();
			user.setPassword(CommonUtil.generateDefaultPassword());
			user.setResetPassword(1);
			userService.saveOrUpdate(user);
			emailService.sendPasswordResetEmail(user.getUserId(), user.getPassword(), email);
		}

		if (participantEmailList.size() > 1) {
			throw new BadRequestException("Your email address cannot be uniquely identified");
		}

		if (participantEmailList.size() == 0) {
			throw new BadRequestException("Your email address is not in our system");
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/users/{userID}/change-password", method = RequestMethod.POST)
	public void changeUserPasword(@PathVariable("userID") Long userId, @RequestBody ChangePasswordDTO changePasswordDTO) {

		String currentPassword = changePasswordDTO.getCurrentPassword();
		String newPassword = changePasswordDTO.getNewPassword();
		String confirmNewPassword = changePasswordDTO.getConfirmNewPassword();
		if (currentPassword == null || newPassword == null || confirmNewPassword == null) {
			throw new BadRequestException("Invalid Information To Change Password");
		}

		accessService.verifyUserOrClientAdminAccess(userId);

		User user = userService.findById(userId);

		if (user != null) {
			String pwd = user.getPassword();
			if (StringUtils.isBlank(currentPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmNewPassword)) {
				// User is missing a field
				throw new UnprocessableEntityException("Please enter in all required passwords");
			} else if (!StringUtils.equals(newPassword, confirmNewPassword)) {
				// User new passwords do not match
				throw new UnprocessableEntityException("New Passwords do not match");
			} else if (!StringUtils.equals(pwd, currentPassword)) {
				// If the current password does not match
				throw new UnprocessableEntityException("Current Password does not match");
			} else {
				user.setResetPassword(0);
				user.setPassword(newPassword);
				userService.saveOrUpdate(user);
			}
		} else {
			throw new UnauthorizedException("User not found");
		}
	}

	@RequestMapping(value = "/v1/users/{userID}/bookmark-successful-login", method = RequestMethod.POST)
	public void bookmarkSuccessfulLogin(@PathVariable("userID") Long userId) {
		accessService.compareUserToSession(userId);
		User user = userService.findById(userId);

		user.setMostRecentLogin(new Date());
		userService.saveOrUpdate(user);
	}

	@RequestMapping(value = "/v1/users/search", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipantDTO>> searchUser(@RequestParam("clientId") Long clientId, @RequestParam(value = "employeeId", required = false) String employeeId,
			@RequestParam(value = "uniqueId", required = false) String uniqueId, @RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName) {

		List<ThinParticipantSearch> thinUsers = userService.searchUser(employeeId, uniqueId, firstName, lastName, clientId);
		List<ParticipantDTO> userDTOs = participantMapper.toThinDTOList(thinUsers);

		return new ResponseEntity<>(userDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {

		User user = userService.findById(id);
		User updatedUser = userMapper.toDomain(userDTO, user);
		userService.saveOrUpdate(updatedUser);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/users/userTypes", method = RequestMethod.GET)
	public ResponseEntity<List<UserTypeDTO>> getUserTypes() {

		List<UserType> userTypes = userTypeService.findAll();
		List<UserTypeDTO> userTypeDTOS = userTypeMapper.toDTOList(userTypes);

		return new ResponseEntity<>(userTypeDTOS, HttpStatus.OK);
	}
}