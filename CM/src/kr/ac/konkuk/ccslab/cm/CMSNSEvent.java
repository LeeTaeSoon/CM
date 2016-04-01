package kr.ac.konkuk.ccslab.cm;
import java.nio.*;
import java.util.*;

public class CMSNSEvent extends CMEvent{
	public static final int CONTENT_UPLOAD_REQUEST = 1;			// C->S
	public static final int CONTENT_UPLOAD_RESPONSE = 2;		// S->C
	public static final int CONTENT_DOWNLOAD_REQUEST = 3;		// C->S
	public static final int CONTENT_DOWNLOAD_RESPONSE = 4;		// S->C
	public static final int CONTENT_DOWNLOAD_READY = 5;			// C->S
	public static final int CONTENT_DOWNLOAD = 6;				// S->C
	public static final int CONTENT_DOWNLOAD_END = 7;			// S->C
	public static final int CONTENT_DOWNLOAD_END_RESPONSE = 8;	// C->S
	public static final int ADD_NEW_FRIEND = 9;					// C->S
	public static final int ADD_NEW_FRIEND_ACK = 10;			// S->C
	public static final int REMOVE_FRIEND = 11;					// C->S
	public static final int REMOVE_FRIEND_ACK = 12;				// S->C
	public static final int REQUEST_FRIEND_LIST = 13;			// C->S
	public static final int RESPONSE_FRIEND_LIST = 14;			// S->C
	public static final int REQUEST_FRIEND_REQUESTER_LIST = 15;	// C->S
	public static final int RESPONSE_FRIEND_REQUESTER_LIST = 16;// S->C
	public static final int REQUEST_BI_FRIEND_LIST = 17;		// C->S
	public static final int RESPONSE_BI_FRIEND_LIST = 18;		// S->C
	public static final int REQUEST_ATTACHED_FILES = 19;		// S<->C
	public static final int ATTACHED_FILES_NOT_FOUND = 20;		// S->C
	public static final int REQUEST_ATTACHED_FILE = 21;			// C->S
	public static final int RESPONSE_ATTACHED_FILE = 22;		// S->C
	public static final int CHANGE_ATTACH_DOWNLOAD_SCHEME = 23;	// S->C
	public static final int ACCESS_ATTACHED_FILE = 24;			// C->S
	public static final int PREFETCH_COMPLETED = 25;			// S->C
	
	private String m_strUserName;	// requester name
	private int m_nContentOffset;	// index of the target content
	private int m_nReturnCode;		// response result code
	private long m_lServerTime;		// for measuring roundtrip delay (a server initializes this value)
	private int m_nNumContents;		// number of contents downloaded
	// fields for one content
	private int m_nContentID;
	private String m_strDate;		// date and time
	private String m_strWriterName;
	//private String m_strAttachedFileName;
	private String m_strMessage;
	private int m_nNumAttachedFiles;
	private int m_nReplyOf;
	private int m_nLevelOfDisclosure;
	private ArrayList<String> m_fileNameList;
	private String m_strFileName;
	private int m_nEstDelay;
	// fields for friend events
	private String m_strFriendName;
	private int m_nTotalNumFriends;	// total number of friends
	private int m_nNumFriends;		// number of friends in this event
	private ArrayList<String> m_friendList;
	private int m_nAttachDownloadScheme;
	
	public CMSNSEvent()
	{
		m_nType = CMInfo.CM_SNS_EVENT;

		m_strUserName = "?";
		m_nContentOffset = -1;		
		m_nReturnCode = -1;
		m_lServerTime = -1;
		m_nNumContents = -1;

		m_nContentID = -1;
		m_strDate = "?";
		m_strWriterName = "?";
		//m_strAttachedFileName = "?";
		m_strMessage = "?";
		m_nNumAttachedFiles = -1;
		m_nReplyOf = -1;
		m_nLevelOfDisclosure = -1;
		m_fileNameList = new ArrayList<String>();
		m_strFileName = null;

		m_nEstDelay = 0;
		
		m_strFriendName = "";
		m_nTotalNumFriends = 0;
		m_nNumFriends = 0;
		m_friendList = new ArrayList<String>();
		m_nAttachDownloadScheme = -1;
	}
	
	public CMSNSEvent(ByteBuffer msg)
	{
		m_nType = CMInfo.CM_SNS_EVENT;

		m_strUserName = "?";
		m_nContentOffset = -1;		
		m_nReturnCode = -1;
		m_lServerTime = -1;
		m_nNumContents = -1;

		m_nContentID = -1;
		m_strDate = "?";
		m_strWriterName = "?";
		//m_strAttachedFileName = "?";
		m_strMessage = "?";
		m_nNumAttachedFiles = -1;
		m_nReplyOf = -1;
		m_nLevelOfDisclosure = -1;
		m_fileNameList = new ArrayList<String>();
		m_strFileName = null;

		m_nEstDelay = 0;		

		m_strFriendName = "";
		m_nTotalNumFriends = 0;
		m_nNumFriends = 0;
		m_friendList = new ArrayList<String>();
		m_nAttachDownloadScheme = -1;

		unmarshallHeader(msg);
		unmarshallBody(msg);
	}
	
	public CMSNSEvent unmarshall(ByteBuffer msg)
	{
		unmarshallHeader(msg);
		unmarshallBody(msg);
		
		return this;
	}
	
	// set/get methods
	public void setUserName(String name)
	{
		m_strUserName = name;
	}
	
	public String getUserName()
	{
		return m_strUserName;
	}
	
	public void setContentOffset(int offset)
	{
		m_nContentOffset = offset;
	}
	
	public int getContentOffset()
	{
		return m_nContentOffset;
	}
	
	public void setReturnCode(int code)
	{
		m_nReturnCode = code;
	}
	
	public int getReturnCode()
	{
		return m_nReturnCode;
	}
	
	public void setServerTime(long time)
	{
		m_lServerTime = time;
	}
	
	public long getServerTime()
	{
		return m_lServerTime;
	}
	
	public void setNumContents(int num)
	{
		m_nNumContents = num;
	}
	
	public int getNumContents()
	{
		return m_nNumContents;
	}
	
	public void setContentID(int id)
	{
		m_nContentID = id;
	}
	
	public int getContentID()
	{
		return m_nContentID;
	}
	
	public void setDate(String date)
	{
		m_strDate = date;
	}
	
	public String getDate()
	{
		return m_strDate;
	}
	
	public void setWriterName(String name)
	{
		m_strWriterName = name;
	}
	
	public String getWriterName()
	{
		return m_strWriterName;
	}
	
	/*
	public void setAttachedFileName(String fname)
	{
		if(fname == null)
			m_strAttachedFileName = "";
		else
			m_strAttachedFileName = fname;
	}
	
	public String getAttachedFileName()
	{
		return m_strAttachedFileName;
	}
	*/
	
	public void setMessage(String msg)
	{
		m_strMessage = msg;
	}
	
	public String getMessage()
	{
		return m_strMessage;
	}
	
	public void setNumAttachedFiles(int num)
	{
		m_nNumAttachedFiles = num;
	}
	
	public int getNumAttachedFiles()
	{
		return m_nNumAttachedFiles;
	}
	
	public void setReplyOf(int cid)
	{
		m_nReplyOf = cid;
	}
	
	public int getReplyOf()
	{
		return m_nReplyOf;
	}
	
	public void setLevelOfDisclosure(int lod)
	{
		m_nLevelOfDisclosure = lod;
	}
	
	public int getLevelOfDisclosure()
	{
		return m_nLevelOfDisclosure;
	}
	
	public void setFileNameList(ArrayList<String> flist)
	{
		m_fileNameList = flist;
	}
	
	public ArrayList<String> getFileNameList()
	{
		return m_fileNameList;
	}
	
	public void setFileName(String fname)
	{
		m_strFileName = fname;
	}
	
	public String getFileName()
	{
		return m_strFileName;
	}
	
	public void setEstDelay(int delay)
	{
		m_nEstDelay = delay;
	}
	
	public int getEstDelay()
	{
		return m_nEstDelay;
	}
	
	public void setFriendName(String name)
	{
		m_strFriendName = name;
	}
	
	public String getFriendName()
	{
		return m_strFriendName;
	}
	
	public void setTotalNumFriends(int num)
	{
		m_nTotalNumFriends = num;
	}
	
	public int getTotalNumFriends()
	{
		return m_nTotalNumFriends;
	}
	
	public void setNumFriends(int num)
	{
		m_nNumFriends = num;
	}
	
	public int getNumFriends()
	{
		return m_nNumFriends;
	}
	
	public void setFriendList(ArrayList<String> friendList)
	{
		m_friendList = friendList;
	}
	
	public ArrayList<String> getFriendList()
	{
		return m_friendList;
	}
	
	public void clearFriendList()
	{
		m_friendList.clear();
		return;
	}
	
	public void setAttachDownloadScheme(int nScheme)
	{
		m_nAttachDownloadScheme = nScheme;
	}
	
	public int getAttachDownloadScheme()
	{
		return m_nAttachDownloadScheme;
	}
	
	//////////////////////////////////////////////////
	
	protected int getByteNum()
	{
		/*
		typedef struct _contentUploadRequest {
			char strUserName[NAME_NUM];
			char strMessage[TALK_LEN];
			char strAttachedFileName[NAME_NUM];
		} contentUploadRequest;

		typedef struct _contentUploadResponse {
			int nReturnCode;
			int nContentID;			// sequence number
			char strDate[NAME_NUM];	// creation date and time
			char strUserName[NAME_NUM];
		} contentUploadResponse;

		typedef struct _contentDownloadRequest {
			char strUserName[NAME_NUM];	// requester name
			int nContentOffset;			// index of the first content to be downloaded
		} contentDownloadRequest;

		typedef struct _contentDownloadResponse {
			char strUserName[NAME_NUM];	// requester name
			int nContentOffset;			// index of the first content to be downloaded
			int nReturnCode;			// result of the download request
			unsigned long ulServerTime;			// current time value at the server side
		} contentDownloadResponse;

		typedef struct _contentDownloadReady {
			char strUserName[NAME_NUM];	// requester name
			int nContentOffset;			// index of the first content to be downloaded
			unsigned long ulServerTime;			// current time value at the server side ( same as that of contentDownloadResponse )
		} contentDownloadReady;

		typedef struct _contentDownload {
			char strUserName[NAME_NUM];	// requester name
			int nContentOffset;			// index of the first content to be downloaded
			int nContentID;				// current content id
			char strDate[NAME_NUM];		// date
			char strWriterName[NAME_NUM];	// writer name
			char strAttachedFileName[NAME_NUM];	// attached file name
			char strMessage[TALK_LEN];	// message body
			int nEstDelay;				// download delay of this content (4 simulation)
		} contentDownload;

		typedef struct _contentDownloadEnd {
			char strUserName[NAME_NUM];	// requester name
			int nContentOffset;			// index of the first downloaded content
			int nNumContents;			// number of downloaded contents
		} contentDownloadEnd;

		typedef struct _contentDownloadEndResponse {
			char strUserName[NAME_NUM];	// requester name
			int nContentOffset;			// index of the first downloaded content
			int nReturnCode;			// result of the download
		} contentDownloadEndResponse;
		*/
		
		int nByteNum = 0;
		nByteNum = super.getByteNum();
		int i = 0;
		
		switch(m_nID)
		{
		case CMSNSEvent.CONTENT_UPLOAD_REQUEST:
			nByteNum += 6*Integer.BYTES + m_strUserName.getBytes().length + m_strMessage.getBytes().length;
			if(m_nNumAttachedFiles > 0)
			{
				for(i = 0; i < m_fileNameList.size(); i++)
				{
					nByteNum += Integer.BYTES + m_fileNameList.get(i).getBytes().length;
				}
			}
			break;
		case CMSNSEvent.CONTENT_UPLOAD_RESPONSE:
			nByteNum += 4*Integer.BYTES + m_strDate.getBytes().length + m_strUserName.getBytes().length;
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_REQUEST:
			nByteNum += 3*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length;
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_RESPONSE:
			nByteNum += 4*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length 
					+ Long.BYTES;
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_READY:
			nByteNum += 3*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length 
					+ Long.BYTES;
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD:
			nByteNum += 10*Integer.BYTES + m_strUserName.getBytes().length + m_strDate.getBytes().length
						+ m_strWriterName.getBytes().length + m_strMessage.getBytes().length;
			if(m_nNumAttachedFiles > 0)
			{
				for(i = 0; i < m_fileNameList.size(); i++)
				{
					nByteNum += Integer.BYTES + m_fileNameList.get(i).getBytes().length;
				}
			}
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_END:
			nByteNum += 4*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length;
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_END_RESPONSE:
			nByteNum += 3*Integer.BYTES + m_strUserName.getBytes().length;
			break;
		case CMSNSEvent.ADD_NEW_FRIEND: // user name, friend name
		case CMSNSEvent.REMOVE_FRIEND:
			nByteNum += 2*Integer.BYTES + m_strUserName.getBytes().length + m_strFriendName.getBytes().length;
			break;
		case CMSNSEvent.ADD_NEW_FRIEND_ACK: // return code, user name, friend name
		case CMSNSEvent.REMOVE_FRIEND_ACK:
			nByteNum += 3*Integer.BYTES + m_strUserName.getBytes().length + m_strFriendName.getBytes().length;
			break;
		case CMSNSEvent.REQUEST_FRIEND_LIST: // user name
		case CMSNSEvent.REQUEST_FRIEND_REQUESTER_LIST: // user name
		case CMSNSEvent.REQUEST_BI_FRIEND_LIST:	// user name
			nByteNum += Integer.BYTES + m_strUserName.getBytes().length;
			break;
		case CMSNSEvent.RESPONSE_FRIEND_LIST:
			// user name, total # friends, cur # friends, friend name list
		case CMSNSEvent.RESPONSE_FRIEND_REQUESTER_LIST:
			// user name, total # requesters, cur # requesters, requester name list
		case CMSNSEvent.RESPONSE_BI_FRIEND_LIST:
			// user name, total # bi-friends, cur # bi-friends, bi-friend name list
			nByteNum += 3*Integer.BYTES + m_strUserName.getBytes().length;
			if(m_friendList != null)
			{
				for(i = 0; i < m_friendList.size(); i++)
				{
					nByteNum += Integer.BYTES + m_friendList.get(i).getBytes().length;
				}
			}
			break;
		case CMSNSEvent.REQUEST_ATTACHED_FILES:	// user name, content ID
			nByteNum += 2*Integer.BYTES + m_strUserName.getBytes().length;
			break;
		case CMSNSEvent.ATTACHED_FILES_NOT_FOUND:	// user name, content ID, # files not found, name list
			nByteNum += 3*Integer.BYTES + m_strUserName.getBytes().length;
			for(i = 0; i < m_fileNameList.size(); i++)
			{
				nByteNum += Integer.BYTES + m_fileNameList.get(i).getBytes().length;
			}
			break;
		case CMSNSEvent.REQUEST_ATTACHED_FILE:	// user name, content ID, writer name, file name
			nByteNum += 4*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length
						+ m_strFileName.getBytes().length;
			break;
		case CMSNSEvent.RESPONSE_ATTACHED_FILE:	// user name, content ID, writer name, file name, return code
			nByteNum += 5*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length
						+ m_strFileName.getBytes().length;
			break;
		case CMSNSEvent.CHANGE_ATTACH_DOWNLOAD_SCHEME: // attachment download scheme
			nByteNum += Integer.BYTES;
			break;
		case CMSNSEvent.ACCESS_ATTACHED_FILE:	// user name, content ID, writer name, file name
			nByteNum += 4*Integer.BYTES + m_strUserName.getBytes().length + m_strWriterName.getBytes().length
						+ m_strFileName.getBytes().length;
			break;
		case CMSNSEvent.PREFETCH_COMPLETED:		// user name
			nByteNum += Integer.BYTES + m_strUserName.getBytes().length;
			break;
		default:
			nByteNum = -1;
			break;
		}
		
		return nByteNum;
	}
	
	protected void marshallBody()
	{
		int i = 0;
		
		switch(m_nID)
		{
		case CMSNSEvent.CONTENT_UPLOAD_REQUEST:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_strMessage.getBytes().length);
			m_bytes.put(m_strMessage.getBytes());
			m_bytes.putInt(m_nNumAttachedFiles);
			m_bytes.putInt(m_nReplyOf);
			m_bytes.putInt(m_nLevelOfDisclosure);
			if(m_nNumAttachedFiles > 0)
			{
				for(i = 0; i < m_fileNameList.size(); i++)
				{
					m_bytes.putInt(m_fileNameList.get(i).getBytes().length);
					m_bytes.put(m_fileNameList.get(i).getBytes());
				}
			}
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_UPLOAD_RESPONSE:
			m_bytes.putInt(m_nReturnCode);
			m_bytes.putInt(m_nContentID);
			m_bytes.putInt(m_strDate.getBytes().length);
			m_bytes.put(m_strDate.getBytes());
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_REQUEST:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());	// requester name
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());	// requested writer name
			m_bytes.putInt(m_nContentOffset);
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_RESPONSE:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());	// requester name
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());	// requested writer name
			m_bytes.putInt(m_nContentOffset);
			m_bytes.putInt(m_nReturnCode);
			m_bytes.putLong(m_lServerTime);
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_READY:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());	// requester name
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());	// requested writer name
			m_bytes.putInt(m_nContentOffset);
			m_bytes.putLong(m_lServerTime);
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentOffset);
			m_bytes.putInt(m_nContentID);
			m_bytes.putInt(m_strDate.getBytes().length);
			m_bytes.put(m_strDate.getBytes());
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());
			m_bytes.putInt(m_strMessage.getBytes().length);
			m_bytes.put(m_strMessage.getBytes());
			m_bytes.putInt(m_nNumAttachedFiles);
			m_bytes.putInt(m_nReplyOf);
			m_bytes.putInt(m_nLevelOfDisclosure);
			m_bytes.putInt(m_nEstDelay);
			if(m_nNumAttachedFiles > 0)
			{
				for(i = 0; i < m_fileNameList.size(); i++)
				{
					m_bytes.putInt(m_fileNameList.get(i).getBytes().length);
					m_bytes.put(m_fileNameList.get(i).getBytes());
				}
			}
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_END:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());
			m_bytes.putInt(m_nContentOffset);
			m_bytes.putInt(m_nNumContents);
			m_bytes.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_END_RESPONSE:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentOffset);
			m_bytes.putInt(m_nReturnCode);
			m_bytes.clear();
			break;
		case CMSNSEvent.ADD_NEW_FRIEND:
		case CMSNSEvent.REMOVE_FRIEND:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_strFriendName.getBytes().length);
			m_bytes.put(m_strFriendName.getBytes());
			m_bytes.clear();
			break;
		case CMSNSEvent.ADD_NEW_FRIEND_ACK:
		case CMSNSEvent.REMOVE_FRIEND_ACK:
			m_bytes.putInt(m_nReturnCode);
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_strFriendName.getBytes().length);
			m_bytes.put(m_strFriendName.getBytes());
			m_bytes.clear();
			break;
		case CMSNSEvent.REQUEST_FRIEND_LIST:
		case CMSNSEvent.REQUEST_FRIEND_REQUESTER_LIST:
		case CMSNSEvent.REQUEST_BI_FRIEND_LIST:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.clear();
			break;
		case CMSNSEvent.RESPONSE_FRIEND_LIST:
		case CMSNSEvent.RESPONSE_FRIEND_REQUESTER_LIST:
		case CMSNSEvent.RESPONSE_BI_FRIEND_LIST:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nTotalNumFriends);
			m_bytes.putInt(m_nNumFriends);
			for(i = 0; i < m_friendList.size(); i++)
			{
				m_bytes.putInt(m_friendList.get(i).getBytes().length);
				m_bytes.put(m_friendList.get(i).getBytes());
			}
			m_bytes.clear();
			break;
		case CMSNSEvent.REQUEST_ATTACHED_FILES:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentID);
			m_bytes.clear();
			break;
		case CMSNSEvent.ATTACHED_FILES_NOT_FOUND:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentID);
			m_bytes.putInt(m_nNumAttachedFiles);
			for(i = 0; i < m_fileNameList.size(); i++)
			{
				m_bytes.putInt(m_fileNameList.get(i).getBytes().length);
				m_bytes.put(m_fileNameList.get(i).getBytes());
			}
			m_bytes.clear();
			break;
		case CMSNSEvent.REQUEST_ATTACHED_FILE:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentID);
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());
			m_bytes.putInt(m_strFileName.getBytes().length);
			m_bytes.put(m_strFileName.getBytes());
			m_bytes.clear();
			break;
		case CMSNSEvent.RESPONSE_ATTACHED_FILE:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentID);
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());
			m_bytes.putInt(m_strFileName.getBytes().length);
			m_bytes.put(m_strFileName.getBytes());
			m_bytes.putInt(m_nReturnCode);
			m_bytes.clear();
			break;
		case CMSNSEvent.CHANGE_ATTACH_DOWNLOAD_SCHEME:
			m_bytes.putInt(m_nAttachDownloadScheme);
			m_bytes.clear();
			break;
		case CMSNSEvent.ACCESS_ATTACHED_FILE:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.putInt(m_nContentID);
			m_bytes.putInt(m_strWriterName.getBytes().length);
			m_bytes.put(m_strWriterName.getBytes());
			m_bytes.putInt(m_strFileName.getBytes().length);
			m_bytes.put(m_strFileName.getBytes());
			m_bytes.clear();
			break;
		case CMSNSEvent.PREFETCH_COMPLETED:
			m_bytes.putInt(m_strUserName.getBytes().length);
			m_bytes.put(m_strUserName.getBytes());
			m_bytes.clear();
			break;
		default:
			System.out.println("CMSNSEvent.marshallBody(), unknown event id("+m_nID+").");
			m_bytes = null;
			break;
		}
		
		return;
	}
	
	protected void unmarshallBody(ByteBuffer msg)
	{
		int i = 0;
		
		switch(m_nID)
		{
		case CMSNSEvent.CONTENT_UPLOAD_REQUEST:
			m_strUserName = getStringFromByteBuffer(msg);
			m_strMessage = getStringFromByteBuffer(msg);
			m_nNumAttachedFiles = msg.getInt();
			m_nReplyOf = msg.getInt();
			m_nLevelOfDisclosure = msg.getInt();
			if(m_nNumAttachedFiles > 0)
			{
				m_fileNameList.clear();
				for(i = 0; i < m_nNumAttachedFiles; i++)
				{
					m_fileNameList.add(getStringFromByteBuffer(msg));
				}
			}
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_UPLOAD_RESPONSE:
			m_nReturnCode = msg.getInt();
			m_nContentID = msg.getInt();
			m_strDate = getStringFromByteBuffer(msg);
			m_strUserName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_REQUEST:
			m_strUserName = getStringFromByteBuffer(msg);	// requester name
			m_strWriterName = getStringFromByteBuffer(msg);	// requested writer name
			m_nContentOffset = msg.getInt();
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_RESPONSE:
			m_strUserName = getStringFromByteBuffer(msg);	// requester name
			m_strWriterName = getStringFromByteBuffer(msg);	// requested writer name
			m_nContentOffset = msg.getInt();
			m_nReturnCode = msg.getInt();
			m_lServerTime = msg.getLong();
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_READY:
			m_strUserName = getStringFromByteBuffer(msg);	// requester name
			m_strWriterName = getStringFromByteBuffer(msg);	// requested writer name
			m_nContentOffset = msg.getInt();
			m_lServerTime = msg.getLong();
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentOffset = msg.getInt();
			m_nContentID = msg.getInt();
			m_strDate = getStringFromByteBuffer(msg);
			m_strWriterName = getStringFromByteBuffer(msg);
			m_strMessage = getStringFromByteBuffer(msg);
			m_nNumAttachedFiles = msg.getInt();
			m_nReplyOf = msg.getInt();
			m_nLevelOfDisclosure = msg.getInt();
			m_nEstDelay = msg.getInt();
			if(m_nNumAttachedFiles > 0)
			{
				m_fileNameList.clear();
				for(i = 0; i < m_nNumAttachedFiles; i++)
				{
					m_fileNameList.add(getStringFromByteBuffer(msg));
				}
			}
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_END:
			m_strUserName = getStringFromByteBuffer(msg);
			m_strWriterName = getStringFromByteBuffer(msg);
			m_nContentOffset = msg.getInt();
			m_nNumContents = msg.getInt();
			msg.clear();
			break;
		case CMSNSEvent.CONTENT_DOWNLOAD_END_RESPONSE:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentOffset = msg.getInt();
			m_nReturnCode = msg.getInt();
			msg.clear();
			break;
		case CMSNSEvent.ADD_NEW_FRIEND:
		case CMSNSEvent.REMOVE_FRIEND:
			m_strUserName = getStringFromByteBuffer(msg);
			m_strFriendName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		case CMSNSEvent.ADD_NEW_FRIEND_ACK:
		case CMSNSEvent.REMOVE_FRIEND_ACK:
			m_nReturnCode = msg.getInt();
			m_strUserName = getStringFromByteBuffer(msg);
			m_strFriendName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		case CMSNSEvent.REQUEST_FRIEND_LIST:
		case CMSNSEvent.REQUEST_FRIEND_REQUESTER_LIST:
		case CMSNSEvent.REQUEST_BI_FRIEND_LIST:
			m_strUserName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		case CMSNSEvent.RESPONSE_FRIEND_LIST:
		case CMSNSEvent.RESPONSE_FRIEND_REQUESTER_LIST:
		case CMSNSEvent.RESPONSE_BI_FRIEND_LIST:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nTotalNumFriends = msg.getInt();
			m_nNumFriends = msg.getInt();
			m_friendList.clear();
			for(i = 0; i < m_nNumFriends; i++)
			{
				m_friendList.add(getStringFromByteBuffer(msg));
			}
			msg.clear();
			break;
		case CMSNSEvent.REQUEST_ATTACHED_FILES:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentID = msg.getInt();
			msg.clear();
			break;
		case CMSNSEvent.ATTACHED_FILES_NOT_FOUND:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentID = msg.getInt();
			m_nNumAttachedFiles = msg.getInt();
			m_fileNameList.clear();
			for(i = 0; i < m_nNumAttachedFiles; i++)
			{
				m_fileNameList.add(getStringFromByteBuffer(msg));
			}
			msg.clear();
			break;
		case CMSNSEvent.REQUEST_ATTACHED_FILE:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentID = msg.getInt();
			m_strWriterName = getStringFromByteBuffer(msg);
			m_strFileName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		case CMSNSEvent.RESPONSE_ATTACHED_FILE:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentID = msg.getInt();
			m_strWriterName = getStringFromByteBuffer(msg);
			m_strFileName = getStringFromByteBuffer(msg);
			m_nReturnCode = msg.getInt();
			msg.clear();
			break;
		case CMSNSEvent.CHANGE_ATTACH_DOWNLOAD_SCHEME:
			m_nAttachDownloadScheme = msg.getInt();
			msg.clear();
			break;
		case CMSNSEvent.ACCESS_ATTACHED_FILE:
			m_strUserName = getStringFromByteBuffer(msg);
			m_nContentID = msg.getInt();
			m_strWriterName = getStringFromByteBuffer(msg);
			m_strFileName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		case CMSNSEvent.PREFETCH_COMPLETED:
			m_strUserName = getStringFromByteBuffer(msg);
			msg.clear();
			break;
		default:
			System.out.println("CMSNSEvent.unmarshallBody(), unknown event id("+m_nID+").");
			m_bytes = null;
			break;
		}
		
		return;
	}

}
