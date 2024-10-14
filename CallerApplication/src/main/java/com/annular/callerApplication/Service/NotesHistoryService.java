package com.annular.callerApplication.Service;

import java.util.List;
import java.util.Map;

import com.annular.callerApplication.model.NotesHistory;
import com.annular.callerApplication.webModel.NotesWebModel;

public interface NotesHistoryService {

	NotesHistory saveNotes(NotesWebModel notesHistoryWebModel);

	NotesHistory getNotes(String senderNumber, String receiverNumber, String groupCode);

	List<Map<String, String>> getNumberBySenderNumber(String senderNumber);

	List<NotesHistory> getNotesBySenderNumberAndReceiverNumbers(String senderNumber, String receiverNumber);

}
