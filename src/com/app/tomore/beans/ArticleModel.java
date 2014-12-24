package com.app.tomore.beans;

import java.io.Serializable;

import org.json.JSONObject;




import android.content.ContentValues;
import android.database.Cursor;

public class ArticleModel implements Serializable{ 
	
	public String getArticleTitle() {
		return ArticleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		ArticleTitle = articleTitle;
	}

	public String getArticleContent() {
		return ArticleContent;
	}

	public void setArticleContent(String articleContent) {
		ArticleContent = articleContent;
	}

	public String getArticleLargeImage() {
		return ArticleLargeImage;
	}

	public void setArticleLargeImage(String articleLargeImage) {
		ArticleLargeImage = articleLargeImage;
	}

	public String getArticleSmallImage() {
		return ArticleSmallImage;
	}

	public void setArticleSmallImage(String articleSmallImage) {
		ArticleSmallImage = articleSmallImage;
	}

	public String getImagePosition() {
		return ImagePosition;
	}

	public void setImagePosition(String imagePosition) {
		ImagePosition = imagePosition;
	}

	public String getArticleDate() {
		return ArticleDate;
	}

	public void setArticleDate(String articleDate) {
		ArticleDate = articleDate;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getArticleIssue() {
		return ArticleIssue;
	}

	public void setArticleIssue(String articleIssue) {
		ArticleIssue = articleIssue;
	}

	public String getDisplayStyle() {
		return DisplayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		DisplayStyle = displayStyle;
	}

	public String getArticleID() {
		return ArticleID;
	}

	public void setArticleID(String articleID) {
		ArticleID = articleID;
	}

	public String getArticleVideo() {
		return ArticleVideo;
	}

	public void setArticleVideo(String articleVideo) {
		ArticleVideo = articleVideo;
	}

	public String getVideoUrl() {
		return VideoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		VideoUrl = videoUrl;
	}

	public String getTextUrl() {
		return TextUrl;
	}

	public void setTextUrl(String textUrl) {
		TextUrl = textUrl;
	}

	private String ArticleTitle;
	private String  ArticleContent;
	private String  ArticleLargeImage;
	private String  ArticleSmallImage;
	private String  ImagePosition;
	private String  ArticleDate;
	private String  Author;
	private String  ArticleIssue;
	private String  DisplayStyle;
	private String  ArticleID;
	private String ArticleVideo;
	private String  VideoUrl;
	private String  TextUrl;
}
	