package com.annular.callerApplication.Service;

import java.util.List;

import com.annular.callerApplication.model.MailDetails;
import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.webModel.MailDetailsWebModel;
import com.annular.callerApplication.webModel.NotesWebModel;

public interface MailService {

	MailDetails saveMail(MailDetailsWebModel mailDetailsWebModel);

	List<MailDetails> getAllMail();

}
