package com.annular.callerApplication.Service;

import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.webModel.NotesWebModel;

public interface NotesHistoryService {

	NotesHistory saveNotes(NotesWebModel notesHistoryWebModel);

	NotesHistory getNotes(String senderNumber, String receiverNumber, String groupCode);

}
