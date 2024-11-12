package ru.ylab.testcontainers.repositories;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.ylab.models.RefreshToken;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.testcontainers.config.AbstractDbTest;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "classpath:db/insert_tokens.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class RefreshTokenRepositoryTest extends AbstractDbTest {

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @DisplayName("Test()")
    @Test
    public void testFindByToken() {
        Optional<RefreshToken> token = tokenRepository.findByToken("token");
        assertThat(token).isPresent();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(token.get().getToken()).isEqualTo("token");
            softly.assertThat(token.get().getUserId()).isEqualTo(-1L);
        });
    }

    @Test
    public void testFindByTokenFail() {
        Optional<RefreshToken> token = tokenRepository.findByToken("some-token");
        assertThat(token).isNotPresent();
    }

    @Test
    public void testSave() {
        assertThat(tokenRepository.save(RefreshToken.builder()
                                                    .token("token")
                                                    .userId(-2L)
                                                    .created(Instant.now())
                                                    .expires(Instant.now().plus(14, ChronoUnit.DAYS))
                                                    .build()))
                .isTrue();
    }
}
