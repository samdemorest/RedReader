package org.quantumbadger.redreader.reddit.prepared;

import org.apache.commons.lang3.StringEscapeUtils;
import org.quantumbadger.redreader.reddit.prepared.markdown.MarkdownParagraphGroup;
import org.quantumbadger.redreader.reddit.prepared.markdown.MarkdownParser;
import org.quantumbadger.redreader.reddit.things.RedditPost;
import org.quantumbadger.redreader.reddit.things.RedditThingWithIdAndType;

public class RedditParsedPost implements RedditThingWithIdAndType {

	private final RedditPost mSrc;

	private final String mTitle;
	private final String mUrl;
	private final MarkdownParagraphGroup mSelfText;
	private final String mFlairText;
	private final String mLockedText;

	public RedditParsedPost(
			final RedditPost src,
			final boolean parseSelfText) {

		this.mSrc = src;

		if(src.title == null) {
			mTitle = "[null]";
		} else {
			mTitle = StringEscapeUtils.unescapeHtml4(src.title.replace('\n', ' ')).trim();
		}

		mUrl = StringEscapeUtils.unescapeHtml4(src.url);

		if(parseSelfText && src.is_self && src.selftext != null && src.selftext.trim().length() > 0) {
			mSelfText = MarkdownParser.parse(StringEscapeUtils.unescapeHtml4(src.selftext).toCharArray());
		} else {
			mSelfText = null;
		}

		if(src.link_flair_text != null && src.link_flair_text.length() > 0) {
			mFlairText = StringEscapeUtils.unescapeHtml4(src.link_flair_text);
		} else {
			mFlairText = null;
		}

		if(src.locked){
			mLockedText = "Locked";
		} else {
			mLockedText = null;
		}
	}

	@Override
	public String getIdAlone() {
		return mSrc.getIdAlone();
	}

	@Override
	public String getIdAndType() {
		return mSrc.getIdAndType();
	}

	public String getTitle() {
		return mTitle;
	}

	public String getUrl() {
		return mUrl;
	}

	public boolean isStickied() {
		return mSrc.stickied;
	}

	public RedditPost getSrc() {
		return mSrc;
	}

	public String getThumbnailUrl() {
		return mSrc.thumbnail;
	}

	public boolean isArchived() {
		return mSrc.archived;
	}

	public String getAuthor() {
		return mSrc.author;
	}

	public String getRawSelfText() {
		return mSrc.selftext;
	}

	public String getSubreddit() {
		return mSrc.subreddit;
	}

	public int getScoreExcludingOwnVote() {

		int score = mSrc.score;

		if(Boolean.TRUE.equals(mSrc.likes)) score--;
		if(Boolean.FALSE.equals(mSrc.likes)) score++;

		return score;
	}

	public boolean isNsfw() {
		return mSrc.over_18;
	}

	public String getFlairText() {
		return mFlairText;
	}

	public long getCreatedTimeSecsUTC() {
		return mSrc.created_utc;
	}

	public String getDomain() {
		return mSrc.domain;
	}

	public boolean isSelfPost() {
		return mSrc.is_self;
	}

	public MarkdownParagraphGroup getSelfText() {
		return mSelfText;
	}

	public boolean isLocked() { return mSrc.locked; }
}
