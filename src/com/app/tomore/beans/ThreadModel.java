package com.app.tomore.beans;

import java.util.ArrayList;
import com.app.tomore.beans.ThreadCmtModel;
import com.app.tomore.beans.ThreadImageModel;
import com.app.tomore.beans.ThreadLikeModel;

public class ThreadModel {

	public ArrayList<ThreadCmtModel> getThreadCmtList() {
		return threadCmtList;
	}
	public void setThreadCmtList(ArrayList<ThreadCmtModel> threadCmtList) {
		this.threadCmtList = threadCmtList;
	}
	public ArrayList<ThreadImageModel> getThreadImageList() {
		return threadImageList;
	}
	public void setThreadImageList(ArrayList<ThreadImageModel> threadImageList) {
		this.threadImageList = threadImageList;
	}
	public ArrayList<ThreadLikeModel> getThreadLikeList() {
		return threadLikeList;
	}
	public void setThreadLikeList(ArrayList<ThreadLikeModel> threadLikeList) {
		this.threadLikeList = threadLikeList;
	}
	private ArrayList<ThreadCmtModel> threadCmtList;
	private ArrayList<ThreadImageModel> threadImageList;
	private ArrayList<ThreadLikeModel> threadLikeList;
	
	public String getThreadID() {
		return ThreadID;
	}
	public void setThreadID(String threadID) {
		ThreadID = threadID;
	}
	public String getThreadTitle() {
		return ThreadTitle;
	}
	public void setThreadTitle(String threadTitle) {
		ThreadTitle = threadTitle;
	}
	public String getThreadPostDate() {
		return ThreadPostDate;
	}
	public void setThreadPostDate(String threadPostDate) {
		ThreadPostDate = threadPostDate;
	}
	public String getThreadUpdateDate() {
		return ThreadUpdateDate;
	}
	public void setThreadUpdateDate(String threadUpdateDate) {
		ThreadUpdateDate = threadUpdateDate;
	}
	public String getThreadContent() {
		return ThreadContent;
	}
	public void setThreadContent(String threadContent) {
		ThreadContent = threadContent;
	}
	public String getThreadType() {
		return ThreadType;
	}
	public void setThreadType(String threadType) {
		ThreadType = threadType;
	}
	public String getLastCommentDate() {
		return LastCommentDate;
	}
	public void setLastCommentDate(String lastCommentDate) {
		LastCommentDate = lastCommentDate;
	}
	public String getMemberID() {
		return MemberID;
	}
	public void setMemberID(String memberID) {
		MemberID = memberID;
	}
	public String getAccountName() {
		return AccountName;
	}
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	public String getMemberImage() {
		return MemberImage;
	}
	public void setMemberImage(String memberImage) {
		MemberImage = memberImage;
	}
	public String getTimeDiff() {
		return TimeDiff;
	}
	public void setTimeDiff(String timeDiff) {
		TimeDiff = timeDiff;
	}
	private String ThreadID;
	private String ThreadTitle;
	private String ThreadPostDate;
	private String ThreadUpdateDate;
	private String ThreadContent;
	private String ThreadType;
	private String LastCommentDate;
	private String MemberID;
	private String AccountName;
	private String MemberImage;
	private String TimeDiff;
	
}
