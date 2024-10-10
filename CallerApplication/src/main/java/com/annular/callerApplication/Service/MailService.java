package com.annular.callerApplication.Service;

import com.annular.callerApplication.model.MailDetails;
import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.webModel.MailDetailsWebModel;
import com.annular.callerApplication.webModel.NotesWebModel;

public interface MailService {

	MailDetails saveMail(MailDetailsWebModel mailDetailsWebModel);

}
