package com.walking.techie.xmltomysql.listener;

import com.walking.techie.xmltomysql.model.Report;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomJobCompletionListener extends JobExecutionListenerSupport {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      List<Report> results = jdbcTemplate
          .query("SELECT id, date, impression, clicks, earning FROM report",
              new RowMapper<Report>() {
                @Override
                public Report mapRow(ResultSet rs, int row) throws SQLException {
                  return new Report(rs.getInt(1), rs.getDate(2), rs.getLong(3), rs.getInt(4),
                      rs.getBigDecimal(5));
                }
              });

      for (Report report : results) {
        log.info("Found <" + report + "> in the database.");
      }

    }
  }
}
