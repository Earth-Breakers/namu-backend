package univ.earthbreaker.namu.infra.storage.post;

import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import univ.earthbreaker.namu.core.domain.post.Post;
import univ.earthbreaker.namu.core.domain.post.PostCreateDbCommand;
import univ.earthbreaker.namu.infra.storage.common.BaseTimeJpaEntity;

@Entity
@Table(name = "post")
public class PostJpaEntity extends BaseTimeJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(nullable = false)
	private Long memberNo;

	@Column(nullable = false)
	private String memberNickname;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Column(length = 500)
	private String imagePath;

	@Column(nullable = false)
	private Long missionNo;

	protected PostJpaEntity() {
	}

	public PostJpaEntity(
		Long memberNo,
		String memberNickname,
		String title,
		String content,
		String imagePath,
		Long missionNo
	) {
		this.memberNo = memberNo;
		this.memberNickname = memberNickname;
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.missionNo = missionNo;
	}

	Post toPost() {
		return Post.of(no, memberNo, memberNickname, title, content, imagePath, missionNo);
	}

	static @NotNull PostJpaEntity from(@NotNull PostCreateDbCommand command) {
		return new PostJpaEntity(
			command.memberNo(),
			command.nickname(),
			command.title(),
			command.content(),
			command.imagePathKey(),
			command.missionNo()
		);
	}
}
