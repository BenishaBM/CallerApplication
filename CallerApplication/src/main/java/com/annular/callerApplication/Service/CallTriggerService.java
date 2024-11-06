package com.annular.callerApplication.Service;

import com.annular.callerApplication.controller.CallTriggerWebModel;
import com.annular.callerApplication.model.CallTriggerList;

public interface CallTriggerService {

	CallTriggerList saveCallTrigger(CallTriggerWebModel groupDetailsWebModel);

}
