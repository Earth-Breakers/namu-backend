// package univ.earthbreaker.namu.batch.mission;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.assertj.core.api.Assertions.assertThat;
//
// import java.time.LocalDate;
// import java.util.List;
// import java.util.Map;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.CsvSource;
// import org.junit.runner.RunWith;
// import org.springframework.batch.core.BatchStatus;
// import org.springframework.batch.core.JobExecution;
// import org.springframework.batch.core.JobParameter;
// import org.springframework.batch.core.JobParameters;
// import org.springframework.batch.test.JobLauncherTestUtils;
// import org.springframework.batch.test.context.SpringBatchTest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import univ.earthbreaker.namu.database.core.mission.FixMissionJpaEntity;
// import univ.earthbreaker.namu.database.core.mission.FixMissionJpaRepository;
// import univ.earthbreaker.namu.database.core.mission.MemberMissionJpaRepository;
//
// @RunWith(SpringRunner.class)
// @SpringBatchTest
// @SpringBootTest(classes = {
// 	TestBatchConfig.class, MissionBatchJpaConfig.class,
// 	MemberMissionBatchConfig.class, LoadMissionStepDecider.class})
// class MemberMissionBatchTest {
//
// 	private @Autowired JobLauncherTestUtils jobLauncherTestUtils;
// 	private @Autowired FixMissionJpaRepository fixMissionJpaRepository;
// 	private @Autowired MemberMissionJpaRepository memberMissionJpaRepository;
//
// 	@BeforeEach
// 	void setUp() {
// 		fixMissionJpaRepository.saveAll(List.of(
// 			new FixMissionJpaEntity(1L, "USE_PUBLIC_TRANSPORT", "DEFAULT"),
// 			new FixMissionJpaEntity(2L, "RECYCLE", "DEFAULT"),
// 			new FixMissionJpaEntity(3L, "USE_STAIRS", "DEFAULT"),
// 			new FixMissionJpaEntity(4L, "USE_DIGITAL_RECEIPTS", "DEFAULT"),
// 			new FixMissionJpaEntity(5L, "USE_TUMBLER", "DEFAULT"),
// 			new FixMissionJpaEntity(6L, "USE_REUSABLE_CONTAINERS", "TODAY"),
// 			new FixMissionJpaEntity(7L, "VISIT_VEGAN_CAFE", "TODAY"),
// 			new FixMissionJpaEntity(8L, "USE_SHOPPING_BAGS", "TODAY"),
// 			new FixMissionJpaEntity(9L, "PLANT_TREES", "SPECIAL"),
// 			new FixMissionJpaEntity(10L, "BEACH_COMBING", "SPECIAL")
// 		));
// 	}
//
// 	@DisplayName("")
// 	@ParameterizedTest
// 	@CsvSource({
// 		"2024, 4, 14, 8",
// 		"2024, 4, 5, 10, 10",
// 		"2024, 5, 31, 10"})
// 	void memberMissionBatch(int year, int month, int dayOfMonth, int expect) throws Exception {
// 	    // given
// 		JobParameters jobParameters = new JobParameters(Map.of(
// 			"batchDate", new JobParameter<>(LocalDate.of(year, month, dayOfMonth), LocalDate.class),
// 			"chunkSize", new JobParameter<>(10, Integer.class)
// 		));
//
// 	    // when
// 		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
//
// 		// then
// 		assertAll(
// 			() -> assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED),
// 			() -> assertThat(memberMissionJpaRepository.findAll()).hasSize(expect)
// 		);
// 	}
// }
