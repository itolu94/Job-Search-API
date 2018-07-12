package com.job.search.api.jobsearchapi;

import com.job.search.api.jobsearchapi.API.CyberCodersAPI;
import com.job.search.api.jobsearchapi.API.DiceAPI;
import com.job.search.api.jobsearchapi.API.ZipRecruiterAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

@SpringBootTest
@WebMvcTest({DiceAPI.class, ZipRecruiterAPI.class, CyberCodersAPI.class})
public class JobSearchApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
