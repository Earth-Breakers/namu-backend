package univ.earthbreaker.namu.core.domain.post;

public class Post {

	private final long no;
	private final long memberNo;
	private final String title;
	private final String content;
	private final String imagePath;
	private final MissionId missionId;

	public Post(long no, long memberNo, String title, String content, String imagePath, MissionId missionId) {
		this.no = no;
		this.memberNo = memberNo;
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.missionId = missionId;
	}

	record MissionId(long missionNo) {
	}
}
