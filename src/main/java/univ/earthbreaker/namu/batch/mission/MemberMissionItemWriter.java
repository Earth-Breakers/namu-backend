package univ.earthbreaker.namu.batch.mission;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import univ.earthbreaker.namu.database.core.mission.MemberMissionBatchRepository;

public class MemberMissionItemWriter implements ItemWriter<MemberBatchEntity> {

	private final MemberMissionBatchRepository memberMissionBatchRepository;
	private final ItemWriterStepExecutionListener itemWriterStepExecutionListener;

	public MemberMissionItemWriter(
		MemberMissionBatchRepository memberMissionBatchRepository,
		ItemWriterStepExecutionListener itemWriterStepExecutionListener
	) {
		this.memberMissionBatchRepository = memberMissionBatchRepository;
		this.itemWriterStepExecutionListener = itemWriterStepExecutionListener;
	}

	@Override
	public void write(@NotNull Chunk<? extends MemberBatchEntity> chunk) {
		List<Long> memberNos = extractMemberNosInItems(chunk.getItems());
		deletePreviousMemberMissions(memberNos);
		saveAllMemberMissions(memberNos);
	}

	private @NotNull List<Long> extractMemberNosInItems(@NotNull List<? extends MemberBatchEntity> items) {
		return items.stream()
			.map(MemberBatchEntity::memberNo)
			.toList();
	}

	private void deletePreviousMemberMissions(List<Long> memberNos) {
		memberMissionBatchRepository.deleteByMemberNos(memberNos);
	}

	private void saveAllMemberMissions(List<Long> memberNos) {
		memberMissionBatchRepository.saveAllInBatch(memberNos, itemWriterStepExecutionListener.getFixMissions());
	}
}
