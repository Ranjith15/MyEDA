package com.edassist.service.impl;

import com.edassist.constants.ApplicationConstants;
import com.edassist.constants.EmailConstant;
import com.edassist.constants.UserTypeConstants;
import com.edassist.dao.ApplicationDao;
import com.edassist.dao.ApprovalHistoryDao;
import com.edassist.dao.GenericDao;
import com.edassist.exception.BadRequestException;
import com.edassist.exception.ExceededMaxResultsException;
import com.edassist.models.domain.*;
import com.edassist.security.JWTTokenClaims;
import com.edassist.service.*;
import com.edassist.utils.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ApplicationApprovalServiceImpl extends GenericServiceImpl<Application> implements ApplicationApprovalService {

	private static Logger log = Logger.getLogger(ApplicationApprovalServiceImpl.class);

	@Autowired
	private ApprovalHistoryService approvalHistoryService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private UserService userService;

	@Autowired
	private GenericService<EligibilityEventComment> eligibilityEventCommentService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private ApprovalTypeService approvalTypeService;

	@Autowired
	private ApprovalHistoryDao approvalHistoryDao;

	private ApplicationDao applicationDao;

	public ApplicationApprovalServiceImpl() {
	}

	@Autowired
	public ApplicationApprovalServiceImpl(@Qualifier("applicationDaoImpl") GenericDao<Application> genericDao) {
		super(genericDao);
		this.applicationDao = (ApplicationDao) genericDao;
	}

	@Override
	public void resetApprovers(Application application) {
		try {

			List<ApprovalHistory> approvalHistoryList = approvalHistoryService.findByParam("applicationID", application.getApplicationID());

			if (CollectionUtils.isEmpty(approvalHistoryList)) {

				// create approvers
				if (application.getBenefitPeriodID() == null) {
					throw new BadRequestException("Benefit Period must not be null.");
				}

				if (application.getBenefitPeriodID().getProgramID() == null) {
					throw new BadRequestException("Program must not be null.");
				}

				if (application.getBenefitPeriodID().getProgramID().getApprovalLevels() != null) {
					if (Program.ONE_APPROVER.equals(application.getBenefitPeriodID().getProgramID().getApprovalLevels())) {

						participantService.populateSupervisorLevels(application.getParticipantID());
						if (application.getParticipantID().getLevelOneSupervisor() != null) {
							createApprover(1, application, application.getParticipantID().getLevelOneSupervisor());
						}
					} else if (Program.TWO_APPROVERS.equals(application.getBenefitPeriodID().getProgramID().getApprovalLevels())) {
						participantService.populateSupervisorLevels(application.getParticipantID());

						if (application.getParticipantID().getLevelOneSupervisor() != null) {
							createApprover(1, application, application.getParticipantID().getLevelOneSupervisor());
						}

						if (application.getParticipantID().getLevelTwoSupervisor() != null) {
							createApprover(2, application, application.getParticipantID().getLevelTwoSupervisor());
						}
					}
				}
			} else {
				for (ApprovalHistory currHist : approvalHistoryList) {
					if (currHist != null) {
						if (!currHist.getApprovalTypeID().equals(approvalTypeService.fetchBypassedApprovalType())) {
							currHist.setApprovalTypeID(approvalTypeService.fetchPendingApprovalType());
							currHist.setModifiedDate(new Date());
							currHist.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));

							approvalHistoryService.saveOrUpdate(currHist);
						}
					}
				}
			}
		} catch (Exception ex) {
			log.equals(ex);
		}
	}

	@Override
	public boolean isCurrentUserApprover(Application application) {
		return isCurrentUserApplicationApprover(application, 0, false);
	}

	@Override
	public boolean isCurrentUserLevelOneApprover(Application application) {
		return isCurrentUserApplicationApprover(application, 1, true);
	}

	@Override
	public PaginationResult<ThinAppActivityForMyTeam> findActionRequiredApplications(Participant supervisor, int index, int recordsPerPage) {
		PaginationResult<ThinAppActivityForMyTeam> apps;

		try {
			apps = applicationDao.findActionRequiredApplicationsBySupervisorId(supervisor, index, recordsPerPage);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BadRequestException();
		}

		return apps;
	}

	private boolean isCurrentUserApplicationApprover(Application application, int level, boolean filterByLevel) {

		if (application == null) {
			throw new BadRequestException("Application must not be null.");
		}

		Long participantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);

		if (participantId == null) {
			return false;
		}

		List<ApprovalHistory> approvalHistoryList;

		try {
			approvalHistoryList = approvalHistoryService.findByParam("applicationID", application.getApplicationID());

			if (CollectionUtils.isEmpty(approvalHistoryList)) {
				return false;
			} else {
				for (ApprovalHistory currHist : approvalHistoryList) {
					if (currHist != null && currHist.getSupervisor() != null && participantId.equals(currHist.getSupervisor().getParticipantId())) {
						if (filterByLevel) {
							if (currHist.getApprovalLevel() == level) {
								return true;
							}
							// else keep iterating
						} else {
							return true;
						}
					}
				}
			}

		} catch (Exception ex) {
			log.error(ex);
		}

		return false;
	}

	@Override
	public boolean isCurrentUserLevelTwoApprover(Application application) {
		return isCurrentUserApplicationApprover(application, 2, true);
	}

	@Override
	public boolean isCurrentUserSupervisorApprovalValid(Application application) {
		if (application == null) {
			throw new BadRequestException("Application must not be null.");
		}

		if (application.getApplicationStatusID() == null) {
			throw new BadRequestException("ApplicationStatus must not be null.");
		}

		if (!ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(application.getApplicationStatusID().getApplicationStatusCode())) {
			return false;
		}

		Long participantId = sessionService.getClaimAsLong(JWTTokenClaims.PARTICIPANT_ID);

		// lookup supervisors for application
		ApprovalHistory levelOneApprover = lookupApprover(application, 1);
		ApprovalHistory levelTwoApprover = lookupApprover(application, 2);

		// if there are no approval history records then supervisor is not yet
		// allowed to approve/deny the app
		if (levelOneApprover == null && levelTwoApprover == null) {
			return false;
		}

		// both approvers are the same user
		if (levelOneApprover != null && levelOneApprover.getSupervisor() != null && levelTwoApprover != null && levelOneApprover.getSupervisor().equals(levelTwoApprover.getSupervisor())
				&& levelOneApprover.getSupervisor().equals(levelOneApprover.getSupervisor())) {
			return (isSupervisorOneApprovalValid(levelOneApprover) || isSupervisorTwoApprovalValid(levelOneApprover, levelTwoApprover));
		}

		if (levelOneApprover != null && levelOneApprover.getSupervisor() != null && participantId.equals(levelOneApprover.getSupervisor().getParticipantId())) {
			return isSupervisorOneApprovalValid(levelOneApprover);
		}

		if (levelTwoApprover != null && levelTwoApprover.getSupervisor() != null && participantId.equals(levelTwoApprover.getSupervisor().getParticipantId())) {
			return isSupervisorTwoApprovalValid(levelOneApprover, levelTwoApprover);
		}

		return false;
	}

	/* ED-1345 - complete rewrite of processApplicationApproval(...) */

	@Override
	public ApplicationStatus processApplicationApproval(Application application, ApplicationStatus targetStatus) {
		return processApplicationApproval(application, targetStatus, false, false);
	}

	@Override
	public ApplicationStatus processApplicationApproval(Application application, ApplicationStatus targetStatus, boolean bypassLevelOne, boolean bypassLevelTwo) {

		Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
		User currentUser = userService.findById(userId);

		if (application == null) {
			throw new BadRequestException("Application must not be null");
		}

		if (application.getApplicationStatusID() == null) {
			throw new BadRequestException("Application Status must not be null");
		}

		if (application.getBenefitPeriodID() == null || application.getBenefitPeriodID().getProgramID() == null) {
			throw new BadRequestException("Application must be associated with a program");
		}

		if (Program.TWO_APPROVERS.compareTo(application.getBenefitPeriodID().getProgramID().getApprovalLevels()) < 0) {
			throw new BadRequestException("Unsupported number of approval levels [" + application.getBenefitPeriodID().getProgramID().getApprovalLevels() + "]");
		}

		// TAM-2745
		// if zero levels of approval then just return the input status
		if (application.getBenefitPeriodID().getProgramID().getApprovalLevels() == 0 || application.getIsRelatedApplication()) {
			return targetStatus;
		}

		/* must be more than 0 levels of approval to get here */
		ApprovalHistory levelOneApprover = lookupApprover(application, 1);
		ApprovalHistory levelTwoApprover = lookupApprover(application, 2);

		boolean bLevelOneBypassed = false;
		if ((levelOneApprover != null && levelOneApprover.getApprovalTypeID().getApprovalType() != null && approvalTypeService.isBypassed(levelOneApprover.getApprovalTypeID())) || bypassLevelOne
				|| Boolean.TRUE.equals(application.getBypassLevel1())) {
			bLevelOneBypassed = true;
		}
		boolean bLevelTwoBypassed = false;
		if ((levelTwoApprover != null && levelTwoApprover.getApprovalTypeID().getApprovalType() != null && approvalTypeService.isBypassed(levelTwoApprover.getApprovalTypeID())) || bypassLevelTwo
				|| Boolean.TRUE.equals(application.getBypassLevel2())) {
			bLevelTwoBypassed = true;
		}

		if (!ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW.equals(application.getApplicationStatusID().getApplicationStatusCode())) {
			if (Program.ONE_APPROVER.equals(application.getBenefitPeriodID().getProgramID().getApprovalLevels())) {
				if (bLevelOneBypassed) {
					return targetStatus;
				} else {
					ApplicationStatus returnStatus = validateSupervisor(levelOneApprover, currentUser, application, "1");
					if (returnStatus != null) {
						return returnStatus;
					} else {
						notifySupervisor(application, levelOneApprover, EmailConstant.EMAIL_REQUIRES_SUPERVISOR1_APPROVAL);
						return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
					}
				}
			} else {
				if (Program.TWO_APPROVERS.equals(application.getBenefitPeriodID().getProgramID().getApprovalLevels())) {
					if (!bLevelOneBypassed) {
						ApplicationStatus returnStatus = validateSupervisor(levelOneApprover, currentUser, application, "1");
						if (returnStatus != null) {
							return returnStatus;
						} else {
							notifySupervisor(application, levelOneApprover, EmailConstant.EMAIL_REQUIRES_SUPERVISOR1_APPROVAL);
							return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
						}
					} else {
						/*
						 * level 1 is bypassed, we must verify level 2 super here
						 */
						if (!bLevelTwoBypassed) {
							ApplicationStatus returnStatus = validateSupervisor(levelTwoApprover, currentUser, application, "2");
							if (returnStatus != null) {
								return returnStatus;
							} else {
								notifySupervisor(application, levelTwoApprover, EmailConstant.EMAIL_REQUIRES_SUPERVISOR2_APPROVAL);
								return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
							}
						} else {
							/* both level 1 and level 2 are bypassed */
							return targetStatus;
						}
					}
				}
				/*
				 * and this should never happen. Program has neither 0, nor 1, nor 2 levels of approval. Huh...what??
				 */
				return targetStatus;
			}
		} else {
			/*
			 * when I get here I know the application is in 125 status and the action is "Approved" by the logged in user
			 */
			if (Program.ONE_APPROVER.equals(application.getBenefitPeriodID().getProgramID().getApprovalLevels())) {
				if (bLevelOneBypassed) {
					return targetStatus;
				} else {
					/* level 1 is not bypassed */
					if (isCurrentUserLevelOneApprover(application)) {
						if (isSupervisorOneApprovalValid(levelOneApprover)) {
							updateApprovalHistory(levelOneApprover, approvalTypeService.fetchApprovedApprovalType());
							return targetStatus;
						} else {
							throw new BadRequestException("Supervisor is not authorized to approve this application.");
						}
					} else {
						if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.INTERNAL_ADMIN) || userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)) {
							if (isAdminApprovalValid(levelOneApprover, null)) {
								return targetStatus;
							} else if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)) {
								String infoMessage = "Client Admin bypassing Approver #1 and approving the application";
								createEehEntry(currentUser, infoMessage, application, targetStatus);
								application.setBypassLevel1(Boolean.TRUE);
								return targetStatus;
							} else {
								throw new BadRequestException("Supervisor(s) must take action or be bypassed in order to approve this application. (processOneApprover)");
							}
						} else {
							throw new BadRequestException("Unable to determine application's current approval state. (One Approver)");
						}
					}
				}
			} else {
				if (Program.TWO_APPROVERS.equals(application.getBenefitPeriodID().getProgramID().getApprovalLevels())) {
					if (bLevelOneBypassed && bLevelTwoBypassed) {
						return targetStatus;
					} else {
						/*
						 * We know that the application is still in 125 status, but we don't know which approver (1 or 2 or EdAssist Admin) is triggering the "Approve" action
						 */
						// is this the level one approver AND is this approver
						// notified?
						if (isCurrentUserLevelOneApprover(application) && (approvalTypeService.isNotified(levelOneApprover.getApprovalTypeID()))) {
							// is the level 1 approver authorized to approve?
							if (isSupervisorOneApprovalValid(levelOneApprover)) {
								updateApprovalHistory(levelOneApprover, approvalTypeService.fetchApprovedApprovalType());
								// is the level 2 approver bypassed?
								if (levelTwoApprover != null && approvalTypeService.isBypassed(levelTwoApprover.getApprovalTypeID())) {
									return targetStatus;
								} else {
									// level 2 is not bypassed
									// are the level 1 and level 2 approvers the
									// same participant?
									if ((levelTwoApprover != null && levelTwoApprover != null)
											&& levelOneApprover.getSupervisor().getParticipantId().equals(levelTwoApprover.getSupervisor().getParticipantId())) {
										updateApprovalHistory(levelTwoApprover, approvalTypeService.fetchBypassedApprovalType());
										String infoMessage = "Approver #1 and approver #2 match (" + levelOneApprover.getSupervisor().getUser().getFullNameFirstThenLast()
												+ "). Level 2 approval bypassed.";
										createEehEntry(currentUser, infoMessage, application, targetStatus);
										return targetStatus;
									} else {
										// validate level 2 approver
										ApplicationStatus returnStatus = validateSupervisor(levelTwoApprover, currentUser, application, "2");
										if (returnStatus != null) {
											return returnStatus;
										} else {
											// all seems good - let the app flow
											// to the next super
											notifySupervisor(application, levelTwoApprover, EmailConstant.EMAIL_REQUIRES_SUPERVISOR2_APPROVAL);
											return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
										}
									}
								}
							} else {
								throw new BadRequestException("Supervisor is not authorized to approve this application.");
							}
						} else {
							// approve was not triggered by level 1 super
							if (isCurrentUserLevelTwoApprover(application)) {
								if (isSupervisorTwoApprovalValid(levelOneApprover, levelTwoApprover)) {
									updateApprovalHistory(levelTwoApprover, approvalTypeService.fetchApprovedApprovalType());
									return targetStatus;
								} else {
									throw new BadRequestException("Supervisor is not authorized to approve this application.");
								}
							} else {
								/* logged in user is not level 2 approver... */
								if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.INTERNAL_ADMIN) || userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)) {
									if (isAdminApprovalValid(levelOneApprover, levelTwoApprover)) {
										return targetStatus;
									} else if (userTypeService.verfiyLoggedInUserType(UserTypeConstants.CLIENT_ADMIN)) {
										String infoMessage = "Client Admin bypassing Approver #1, approver #2 and approving the application";
										createEehEntry(currentUser, infoMessage, application, targetStatus);
										application.setBypassLevel1(Boolean.TRUE);
										application.setBypassLevel2(Boolean.TRUE);
										return targetStatus;
									} else {
										if (approvalTypeService.isPending(levelTwoApprover.getApprovalTypeID())
												&& (approvalTypeService.isApproved(levelOneApprover.getApprovalTypeID()) || approvalTypeService.isBypassed(levelOneApprover.getApprovalTypeID()))) {
											/*
											 * level 1 is approved or bypassed and level 2 is pending
											 */
											ApplicationStatus returnStatus = validateSupervisor(levelTwoApprover, currentUser, application, "2");
											if (returnStatus != null) {
												return returnStatus;
											} else {
												notifySupervisor(application, levelTwoApprover, EmailConstant.EMAIL_REQUIRES_SUPERVISOR2_APPROVAL);
												return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
											}
										} else {
											if (approvalTypeService.isPending(levelOneApprover.getApprovalTypeID())) {
												ApplicationStatus returnStatus = validateSupervisor(levelOneApprover, currentUser, application, "1");
												if (returnStatus != null) {
													return returnStatus;
												} else {
													notifySupervisor(application, levelOneApprover, EmailConstant.EMAIL_REQUIRES_SUPERVISOR1_APPROVAL);
													return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			// If the program has zero levels of approval then the application's
			// status should become "approved"
			return targetStatus;
		}
	}

	// ED-1345
	private void createEehEntry(User user, String infoMessage, Application application, ApplicationStatus applicationStatus) {
		EligibilityEventComment comment = new EligibilityEventComment();
		comment.setApplicationID(application.getApplicationID());
		ThinUser cUser = new ThinUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getMiddleInitial());
		comment.setCreatedBy(cUser);
		comment.setDateCreated(new Timestamp(new Date().getTime()));
		comment.setApplicationStatus(applicationStatus);
		comment.setViewableByParticipant(true);
		comment.setComment(infoMessage);
		comment.setCommentTypeID(0L);
		comment.setAppStatusChange(true);
		eligibilityEventCommentService.saveOrUpdate(comment);

	}

	private ApplicationStatus validateSupervisor(ApprovalHistory approvalHistory, User actionInitiatingUser, Application application, String approvalLevel) {
		if (approvalHistory == null) {
			String infoMessage = "Approver #" + approvalLevel + " not found. Unable to forward application for review.";
			createEehEntry(actionInitiatingUser, infoMessage, application, applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW));
			return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW);
		} else {
			if (approvalHistory.getSupervisor() != null && approvalHistory.getSupervisor().getActiveIndicator() != true) {
				String infoMessage = approvalHistory.getSupervisor().getUser().getFullNameFirstThenLast() + " (Level " + approvalLevel
						+ " approver) TAMS Status is inactive. He/she will not be able to access the system to review this application for approval.";
				createEehEntry(actionInitiatingUser, infoMessage, application, applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW));
				return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_SUBMITTED_PENDING_REVIEW);
			} else {
				if (approvalHistory.getSupervisor() != null && ((approvalHistory.getSupervisor().getMailingAddress() != null && approvalHistory.getSupervisor().getMailingAddress().getEmail() != null
						&& approvalHistory.getSupervisor().getMailingAddress().getEmail().trim().length() > 0 && CommonUtil.isEmail(approvalHistory.getSupervisor().getMailingAddress().getEmail()))
						|| (approvalHistory.getSupervisor().getWorkAddress() != null && approvalHistory.getSupervisor().getWorkAddress().getEmail() != null
								&& approvalHistory.getSupervisor().getWorkAddress().getEmail().trim().length() > 0 && CommonUtil.isEmail(approvalHistory.getSupervisor().getWorkAddress().getEmail()))
						|| (approvalHistory.getSupervisor().getHomeAddress() != null && approvalHistory.getSupervisor().getHomeAddress().getEmail() != null
								&& approvalHistory.getSupervisor().getHomeAddress().getEmail().trim().length() > 0
								&& CommonUtil.isEmail(approvalHistory.getSupervisor().getHomeAddress().getEmail())))) {
					return null;
				} else {
					String infoMessage = approvalHistory.getSupervisor().getUser().getFullNameFirstThenLast() + " (Level " + approvalLevel
							+ " approver) email address is invalid. Notification not sent.";
					createEehEntry(actionInitiatingUser, infoMessage, application, applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW));
					return applicationStatusService.findByCode(ApplicationConstants.APPLICATION_STATUS_FORWARDED_TO_SUPERVISOR_FOR_REVIEW);
				}
			}
		}
	}

	// ED-1345 - removed checkAndConvertLegacy.... */
	// ED-1345 - removed commented out lookupApprover.... */

	private ApprovalHistory lookupApprover(Application application, int level) {

		if (application == null) {
			throw new BadRequestException("Application must not be null");
		}

		List<ApprovalHistory> approvalHistoryList = null;

		try {
			approvalHistoryList = approvalHistoryService.findByParam("applicationID", application.getApplicationID());
		} catch (ExceededMaxResultsException e) {
			log.error(e.getMessage());
			throw new BadRequestException();
		}

		if (CollectionUtils.isEmpty(approvalHistoryList)) {
			return null;
		} else {
			for (ApprovalHistory currHist : approvalHistoryList) {
				if (currHist != null) {
					if (currHist.getApprovalLevel() == level) {
						return currHist;
					}
				}

			}
		}

		return null;
	}

	// ED-1345 - pulled logic for processOneApprover(...) and
	// processTwoApprovers(...) into processApplicationApproval()
	@Override
	public void notifySupervisor(Application application, ApprovalHistory approver, Long emailContentId) {
		if (approver == null) {
			throw new BadRequestException("Approver must not be null.");
		}
		// notifying supervisor
		// only send notification if the approver has not already been notified
		// and has NOT been bypassed
		if (!approvalTypeService.isNotified(approver.getApprovalTypeID()) && !approvalTypeService.isBypassed(approver.getApprovalTypeID())) {
			try {
				emailService.sendApplicationEventNotificationEmail(application.getApplicationID(), emailContentId, "");
			} catch (Exception e) {
				log.error("Failed to send email notification to approver.", e);
			}
			updateApprovalHistory(approver, approvalTypeService.fetchNotifiedApprovalType());
		}
	}

	private boolean isSupervisorApprovalValid(ApprovalHistory approver) {
		if (approver == null) {
			return false;
		}

		if (approvalTypeService.isApproved(approver.getApprovalTypeID()) || approvalTypeService.isDenied(approver.getApprovalTypeID())
				|| approvalTypeService.isBypassed(approver.getApprovalTypeID())) {
			return false;
		} else if (approvalTypeService.isPending(approver.getApprovalTypeID()) || approvalTypeService.isNotified(approver.getApprovalTypeID())
				|| approvalTypeService.isNotReviewed(approver.getApprovalTypeID())) {
			return true;
		} else {
			throw new BadRequestException("Invalid approval type: " + approver.getApprovalTypeID());
		}
	}

	private boolean isAdminApprovalValid(ApprovalHistory levelOneApprover, ApprovalHistory levelTwoApprover) {
		if (levelOneApprover == null) {
			return true;
		}

		if (levelTwoApprover == null) {
			// handle one approver case
			if (approvalTypeService.isApproved(levelOneApprover.getApprovalTypeID()) || approvalTypeService.isDenied(levelOneApprover.getApprovalTypeID())
					|| approvalTypeService.isBypassed(levelOneApprover.getApprovalTypeID())) {
				return true;
			} else if (approvalTypeService.isPending(levelOneApprover.getApprovalTypeID()) || approvalTypeService.isNotified(levelOneApprover.getApprovalTypeID())
					|| approvalTypeService.isNotReviewed(levelOneApprover.getApprovalTypeID())) {
				return false;
			} else {
				throw new BadRequestException("Invalid approval type: " + levelOneApprover.getApprovalTypeID());
			}
		} else {
			// handle two approver case
			if (approvalTypeService.isApproved(levelOneApprover.getApprovalTypeID()) || approvalTypeService.isBypassed(levelOneApprover.getApprovalTypeID())) {

				// supervisor one has approved the app or been bypassed, now
				// check supervisor two
				if (approvalTypeService.isApproved(levelTwoApprover.getApprovalTypeID()) || approvalTypeService.isDenied(levelTwoApprover.getApprovalTypeID())
						|| approvalTypeService.isBypassed(levelTwoApprover.getApprovalTypeID())) {
					return true;
				} else if (approvalTypeService.isPending(levelTwoApprover.getApprovalTypeID()) || approvalTypeService.isNotified(levelTwoApprover.getApprovalTypeID())
						|| approvalTypeService.isNotReviewed(levelTwoApprover.getApprovalTypeID())) {
					return false;
				} else {
					throw new BadRequestException("Invalid approval type: " + levelTwoApprover.getApprovalTypeID());
				}

			} else if (approvalTypeService.isDenied(levelOneApprover.getApprovalTypeID())) {
				return true;
			} else if (approvalTypeService.isPending(levelOneApprover.getApprovalTypeID()) || approvalTypeService.isNotified(levelOneApprover.getApprovalTypeID())
					|| approvalTypeService.isNotReviewed(levelOneApprover.getApprovalTypeID())) {
				return false;
			} else {
				throw new BadRequestException("Invalid approval type: " + levelOneApprover.getApprovalTypeID());
			}
		}
	}

	@Override
	public void createApprover(int level, Application application, Participant supervisor) {
		if (supervisor != null) {
			ThinUser thinUser = new ThinUser();
			thinUser.setId(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
			ApprovalHistory approver = new ApprovalHistory(level, approvalTypeService.fetchDefaultApprovalType(), application.getApplicationID(), supervisor, new Date(), thinUser);
			approvalHistoryService.saveOrUpdate(approver);
		}
	}

	private boolean isSupervisorOneApprovalValid(ApprovalHistory levelOneApprover) {
		return isSupervisorApprovalValid(levelOneApprover);
	}

	private boolean isSupervisorTwoApprovalValid(ApprovalHistory levelOneApprover, ApprovalHistory levelTwoApprover) {
		if (levelOneApprover == null) {
			return false;
		}

		// if supervisor one has not approved the app or been bypassed then
		// supervisor two cannot approve the app
		if (!approvalTypeService.isApproved(levelOneApprover.getApprovalTypeID()) && !approvalTypeService.isBypassed(levelOneApprover.getApprovalTypeID())) {
			return false;
		}

		return isSupervisorApprovalValid(levelTwoApprover);
	}

	@Override
	public void adjustApproversSet(Application application) {
		/* attempt to localize the client sensitivity right here */
		/*
		 * LMC clientid is 52; this won't change any time soon and is probably more stable that clientName
		 */

		Long userId = sessionService.getClaimAsLong(JWTTokenClaims.USER_ID);
		String firstName = sessionService.getClaimAsString(JWTTokenClaims.USER_FIRST_NAME);
		String lastName = sessionService.getClaimAsString(JWTTokenClaims.USER_LAST_NAME);
		String middleInitials = sessionService.getClaimAsString(JWTTokenClaims.USER_MIDDLE_INITIALS);

		if (new Long(52).equals(application.getParticipantID().getClient().getClientId())) {
			CourseOfStudy cos = application.getCourseOfStudyID();
			/* CourseOfStudyID == 55 == "Other" */

			DegreeObjectives degreeObj = application.getDegreeObjectiveID();
			/* DegreeObjectiveID == 9 == "Ph.D." */
			/* DegreeObjectiveID == 15 == "Professional Degree" */

			if (new Long(55).equals(cos.getCourseOfStudyID()) || new Long(9).equals(degreeObj.getDegreeObjectiveID()) || new Long(15).equals(degreeObj.getDegreeObjectiveID())) {
				if (application.getParticipantID().getGeneric5() == null || application.getParticipantID().getGeneric5().trim().equals("")) {
					EligibilityEventComment comment = new EligibilityEventComment();
					comment.setApplicationID(application.getApplicationID());
					ThinUser cUser = new ThinUser(userId, firstName, lastName, middleInitials);
					comment.setCreatedBy(cUser);
					comment.setDateCreated(new Timestamp(new Date().getTime()));
					comment.setApplicationStatus(application.getApplicationStatusID());
					comment.setViewableByParticipant(true);
					comment.setComment("Policy mandated second level of approval not created. Unable to determine appropriate approving entity.");
					comment.setCommentTypeID(0L);
					comment.setAppStatusChange(true);
					eligibilityEventCommentService.saveOrUpdate(comment);

				} else {
					try {
						ApprovalHistory approvalHistory = new ApprovalHistory();
						approvalHistory.setApplicationID(application.getApplicationID());
						approvalHistory.setSupervisorID(null);
						approvalHistory.setApprovalLevel(2);
						approvalHistory.setIdType(' ');
						approvalHistory.setActionDate(new Date());
						approvalHistory.setApprovalTypeID(approvalTypeService.fetchPendingApprovalType());
						approvalHistory.setActionDate(new Date());
						approvalHistory.setDateCreated(new Date());
						approvalHistory.setModifiedDate(new Date());
						ThinUser thinUser = new ThinUser();
						thinUser.setId(userId);
						approvalHistory.setCreatedBy(thinUser);
						approvalHistory.setModifiedBy(userId);
						String paramNames[] = new String[] { "client", "employeeId" };
						Object params[] = new Object[] { application.getParticipantID().getClient(), application.getParticipantID().getGeneric5() };
						Participant approvingParticipant = (Participant) participantService.findByParams(paramNames, params, null, null).get(0);

						approvalHistory.setSupervisor(approvingParticipant);
						approvalHistoryService.saveOrUpdate(approvalHistory);

						// comments as appropriate

						EligibilityEventComment comment = new EligibilityEventComment();
						comment.setApplicationID(application.getApplicationID());
						ThinUser cUser = new ThinUser(userId, firstName, lastName, middleInitials);
						comment.setCreatedBy(cUser);
						comment.setDateCreated(new Timestamp(new Date().getTime()));
						comment.setApplicationStatus(application.getApplicationStatusID());
						comment.setViewableByParticipant(true);
						comment.setComment("Second level of approval added per policy guidelines.");
						comment.setCommentTypeID(0L);
						comment.setAppStatusChange(true);
						eligibilityEventCommentService.saveOrUpdate(comment);
					} catch (Exception e) {
						;
					}
				}
			}
		}

		/*
		 * on a client-wide basis, determine whether or not a potential second level approver should be automatically bypassed (based on the rules)
		 */

		if (!application.getBenefitPeriodID().getProgramID().getDegreeComparisonCompanyApprovalBypass().equals(Program.NOT_ACTIVE)) {
			boolean haveMatchOnDoCos = false;
			List<Application> appList = null;
			try {
				appList = applicationService.findByParam("participantID", application.getParticipantID());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Iterator<Application> appIter = appList.iterator();
			while (appIter.hasNext()) {
				Application historicalApp = appIter.next();
				/* TS-350 */
				if (historicalApp.getDegreeObjectiveID() != null && historicalApp.getCourseOfStudyID() != null) {
					if (historicalApp.getDegreeObjectiveID().getDegreeObjectiveID().equals(application.getDegreeObjectiveID().getDegreeObjectiveID())
							&& historicalApp.getCourseOfStudyID().getCourseOfStudyID().equals(application.getCourseOfStudyID().getCourseOfStudyID())) {

						/* test for application status >= approved... */
						if (historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(120))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(130))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(135))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(400))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(425))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(450))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(500))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(510))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(520))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(530))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(540))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(545))
								|| historicalApp.getApplicationStatusID().getApplicationStatusID().equals(new Long(900))) {
							haveMatchOnDoCos = true;
						}
					}
				}
			}

			if (haveMatchOnDoCos && (application.getBenefitPeriodID().getProgramID().getDegreeComparisonCompanyApprovalBypass().equals(Program.LEVEL_1_ONLY)
					|| application.getBenefitPeriodID().getProgramID().getDegreeComparisonCompanyApprovalBypass().equals(Program.ALL))) {
				/*
				 * this is the place where we disable the first level supervisor by "bypassing" it
				 */
				String paramNames[] = new String[] { "applicationID", "approvalLevel" };
				Object params[] = new Object[] { application.getApplicationID(), 1 };

				List<ApprovalHistory> ahList = null;
				try {
					ahList = approvalHistoryService.findByParams(paramNames, params, null, null);
				} catch (Exception e) {
					;
				}
				if (ahList != null && ahList.size() > 0) {
					ApprovalHistory ah = ahList.get(0);
					ApprovalType bypassType = approvalTypeService.findById(4); // "4"
																				// ==
																				// bypass
					ah.setApprovalTypeID(bypassType);
					ah.setActionDate(new Date());
					approvalHistoryService.saveOrUpdate(ah);

					// comments as appropriate

					EligibilityEventComment comment = new EligibilityEventComment();
					comment.setApplicationID(application.getApplicationID());
					ThinUser cUser = new ThinUser(userId, firstName, lastName, middleInitials);
					comment.setCreatedBy(cUser);
					comment.setDateCreated(new Timestamp(new Date().getTime()));
					comment.setApplicationStatus(application.getApplicationStatusID());
					comment.setViewableByParticipant(true);
					comment.setComment("First level of approval bypassed per policy guidelines.");
					comment.setCommentTypeID(0L);
					comment.setAppStatusChange(true);
					eligibilityEventCommentService.saveOrUpdate(comment);
				}
			}

			if (haveMatchOnDoCos && (application.getBenefitPeriodID().getProgramID().getDegreeComparisonCompanyApprovalBypass().equals(Program.LEVEL_2_ONLY)
					|| application.getBenefitPeriodID().getProgramID().getDegreeComparisonCompanyApprovalBypass().equals(Program.ALL))) {
				/*
				 * this is the place where we disable the first second supervisor by "bypassing" it
				 */
				String paramNames[] = new String[] { "applicationID", "approvalLevel" };
				Object params[] = new Object[] { application.getApplicationID(), 2 };

				List<ApprovalHistory> ahList = null;
				try {
					ahList = approvalHistoryService.findByParams(paramNames, params, null, null);
				} catch (Exception e) {
					;
				}
				if (ahList != null && ahList.size() > 0) {
					ApprovalHistory ah = ahList.get(0);
					ApprovalType bypassType = approvalTypeService.findById(4); // "4"
																				// ==
																				// bypass
					ah.setApprovalTypeID(bypassType);
					ah.setActionDate(new Date());
					approvalHistoryService.saveOrUpdate(ah);

					// comments as appropriate

					EligibilityEventComment comment = new EligibilityEventComment();
					comment.setApplicationID(application.getApplicationID());
					ThinUser cUser = new ThinUser(userId, firstName, lastName, middleInitials);
					comment.setCreatedBy(cUser);
					comment.setDateCreated(new Timestamp(new Date().getTime()));
					comment.setApplicationStatus(application.getApplicationStatusID());
					comment.setViewableByParticipant(true);
					comment.setComment("First level of approval bypassed per policy guidelines.");
					comment.setCommentTypeID(0L);
					comment.setAppStatusChange(true);
					eligibilityEventCommentService.saveOrUpdate(comment);
				}
			}
		}

		/* add more client sensitivity right here */

	}

	@Override
	public void updateApprovalHistory(ApprovalHistory approver, ApprovalType approvalType) {

		if (approver == null) {
			return;
		}

		approver.setApprovalTypeID(approvalType);
		approver.setActionDate(new Date());

		approver.setModifiedBy(sessionService.getClaimAsLong(JWTTokenClaims.USER_ID));
		approver.setModifiedDate(new Date());
		approvalHistoryDao.saveOrUpdate(approver);

	}
}
