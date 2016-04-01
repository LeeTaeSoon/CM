package kr.ac.konkuk.ccslab.cm;

public class CMEventInfo {
	private CMEventReceiver m_eventReceiver;
	
	public CMEventInfo()
	{
		m_eventReceiver = null;
	}
	
	// set/get methods

	public void setEventReceiver(CMEventReceiver receiver)
	{
		m_eventReceiver = receiver;
	}
	
	public CMEventReceiver getEventReceiver()
	{
		return m_eventReceiver;
	}
}
