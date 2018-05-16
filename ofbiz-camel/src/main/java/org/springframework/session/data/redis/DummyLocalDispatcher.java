package org.springframework.session.data.redis;

import java.util.Map;

import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.security.Security;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.GenericRequester;
import org.apache.ofbiz.service.GenericResultWaiter;
import org.apache.ofbiz.service.GenericServiceCallback;
import org.apache.ofbiz.service.GenericServiceException;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceAuthException;
import org.apache.ofbiz.service.ServiceValidationException;
import org.apache.ofbiz.service.jms.JmsListenerFactory;
import org.apache.ofbiz.service.job.JobManager;

public class DummyLocalDispatcher implements LocalDispatcher {
	
	@Override
	public void schedule(String arg0, String arg1, String arg2, long arg3, int arg4, int arg5, int arg6, long arg7,
			int arg8, Object... arg9) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, String arg1, String arg2, Map<String, ? extends Object> arg3, long arg4, int arg5,
			int arg6, int arg7, long arg8, int arg9) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, String arg1, long arg2, int arg3, int arg4, int arg5, long arg6, int arg7,
			Object... arg8) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, String arg1, Map<String, ? extends Object> arg2, long arg3, int arg4, int arg5,
			int arg6, long arg7, int arg8) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, long arg1, int arg2, int arg3, int arg4, long arg5, Object... arg6)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, Map<String, ? extends Object> arg1, long arg2, int arg3, int arg4, int arg5,
			long arg6) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, long arg1, int arg2, int arg3, long arg4, Object... arg5)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, Map<String, ? extends Object> arg1, long arg2, int arg3, int arg4, long arg5)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, long arg1, int arg2, int arg3, int arg4, Object... arg5)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, Map<String, ? extends Object> arg1, long arg2, int arg3, int arg4, int arg5)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, long arg1, Object... arg2) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void schedule(String arg0, Map<String, ? extends Object> arg1, long arg2) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runSyncIgnore(String arg0, int arg1, boolean arg2, Object... arg3)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runSyncIgnore(String arg0, Map<String, ? extends Object> arg1, int arg2, boolean arg3)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runSyncIgnore(String arg0, Map<String, ? extends Object> arg1) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Map<String, Object> runSync(String arg0, int arg1, boolean arg2, Object... arg3)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> runSync(String arg0, Map<String, ? extends Object> arg1, int arg2, boolean arg3)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> runSync(String arg0, Map<String, ? extends Object> arg1) throws GenericServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GenericResultWaiter runAsyncWait(String arg0, boolean arg1, Object... arg2)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GenericResultWaiter runAsyncWait(String arg0, Map<String, ? extends Object> arg1, boolean arg2)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GenericResultWaiter runAsyncWait(String arg0, Map<String, ? extends Object> arg1)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void runAsync(String arg0, GenericRequester arg1, boolean arg2, int arg3, boolean arg4, Object... arg5)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, Map<String, ? extends Object> arg1, GenericRequester arg2, boolean arg3, int arg4,
			boolean arg5) throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, GenericRequester arg1, boolean arg2, Object... arg3)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, Map<String, ? extends Object> arg1, GenericRequester arg2, boolean arg3)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, boolean arg1, Object... arg2)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, Map<String, ? extends Object> arg1, boolean arg2)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, GenericRequester arg1, Object... arg2)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, Map<String, ? extends Object> arg1, GenericRequester arg2)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void runAsync(String arg0, Map<String, ? extends Object> arg1)
			throws ServiceAuthException, ServiceValidationException, GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void registerCallback(String arg0, GenericServiceCallback arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isEcasDisabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Security getSecurity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JobManager getJobManager() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JmsListenerFactory getJMSListeneFactory() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DispatchContext getDispatchContext() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Delegator getDelegator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void enableEcas() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disableEcas() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deregister() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addRollbackService(String arg0, boolean arg1, Object... arg2) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addRollbackService(String arg0, Map<String, ? extends Object> arg1, boolean arg2)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addCommitService(String arg0, boolean arg1, Object... arg2) throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addCommitService(String arg0, Map<String, ? extends Object> arg1, boolean arg2)
			throws GenericServiceException {
		// TODO Auto-generated method stub
		
	}
};