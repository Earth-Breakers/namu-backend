package univ.earthbreaker.namu.core.domain.post;

import org.jetbrains.annotations.NotNull;

public class Post {

	private final long no;
	private final MemberId memberId;
	private final String title;
	private final String content;
	private final String imagePath;
	private final MissionId missionId;

	private Post(long no, MemberId memberId, String title, String content, String imagePath, MissionId missionId) {
		this.no = no;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.missionId = missionId;
	}

	public static @NotNull Post of(
		long no,
		long memberNo,
		String nickname,
		String title,
		String content,
		String imagePath,
		long missionNo
	) {
		return new Post(no, new MemberId(memberNo, nickname), title, content, imagePath, new MissionId(missionNo));
	}

	record MemberId(long memberNo, String nickname) {
	}

	record MissionId(long missionNo) {
	}

	public long getNo() {
		return no;
	}

	public long getMemberNo() {
		return memberId.memberNo;
	}

	public String getMemberNickname() {
		return memberId.nickname;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getImagePath() {
		return imagePath;
	}

	public long getMissionNo() {
		return missionId.missionNo;
	}
}
